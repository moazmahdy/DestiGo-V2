package com.mobilebreakero.core_ui.extensions

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.toSize

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.math.abs

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastAny(predicate: (T) -> Boolean): Boolean {
    contract { callsInPlace(predicate) }
    fastForEach { if (predicate(it)) return true }
    return false
}
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(item)
    }
}

private suspend fun PointerInputScope.detectTransformGestures(
    onGesture: (centroid: Offset, pan: Offset, zoom: Float, timeMillis: Long) -> Boolean,
    onGestureStart: () -> Unit = {},
    onGestureEnd: () -> Unit = {},
    onTap: (position: Offset) -> Unit = {},
    onDoubleTap: (position: Offset) -> Unit = {},
    enableOneFingerZoom: Boolean = true,
) = awaitEachGesture {
    val firstDown = awaitFirstDown(requireUnconsumed = false)
    onGestureStart()

    var firstUp: PointerInputChange = firstDown
    var isTap = true
    val touchSlop = TouchSlop(viewConfiguration.touchSlop)
    forEachPointerEventUntilReleased { event ->
        if (touchSlop.isPast(event)) {
            val zoomChange = event.calculateZoom()
            val panChange = event.calculatePan()
            if (zoomChange != 1f || panChange != Offset.Zero) {
                val centroid = event.calculateCentroid(useCurrent = true)
                val timeMillis = event.changes[0].uptimeMillis
                val canConsume = onGesture(centroid, panChange, zoomChange, timeMillis)
                if (canConsume) {
                    event.consumePositionChanges()
                }
            }
            isTap = false
        }
        if (event.changes.size > 1) {
            isTap = false
        }
        firstUp = event.changes[0]
    }

    if (firstUp.uptimeMillis - firstDown.uptimeMillis > viewConfiguration.longPressTimeoutMillis) {
        isTap = false
    }

    if (isTap) {
        val secondDown = awaitSecondDown(firstUp)
        if (secondDown == null) {
            onTap(firstUp.position)
        } else {
            var isDoubleTap = true
            var secondUp: PointerInputChange = secondDown
            val secondTouchSlop = TouchSlop(viewConfiguration.touchSlop)
            forEachPointerEventUntilReleased { event ->
                if (secondTouchSlop.isPast(event)) {
                    if (enableOneFingerZoom) {
                        val panChange = event.calculatePan()
                        val zoomChange = 1f + panChange.y * 0.004f
                        if (zoomChange != 1f) {
                            val centroid = event.calculateCentroid(useCurrent = true)
                            val timeMillis = event.changes[0].uptimeMillis
                            val canConsume = onGesture(centroid, Offset.Zero, zoomChange, timeMillis)
                            if (canConsume) {
                                event.consumePositionChanges()
                            }
                        }
                    }
                    isDoubleTap = false
                }
                if (event.changes.size > 1) {
                    isDoubleTap = false
                }
                secondUp = event.changes[0]
            }

            if (secondUp.uptimeMillis - secondDown.uptimeMillis > viewConfiguration.longPressTimeoutMillis) {
                isDoubleTap = false
            }

            if (isDoubleTap) {
                onDoubleTap(secondUp.position)
            }
        }
    }
    onGestureEnd()
}

private suspend fun AwaitPointerEventScope.forEachPointerEventUntilReleased(
    action: (PointerEvent) -> Unit,
) {
    do {
        val event = awaitPointerEvent()
        if (event.changes.fastAny { it.isConsumed }) {
            break
        }
        action(event)
    } while (event.changes.fastAny { it.pressed })
}


private suspend fun AwaitPointerEventScope.awaitSecondDown(
    firstUp: PointerInputChange
): PointerInputChange? = withTimeoutOrNull(viewConfiguration.doubleTapTimeoutMillis) {
    val minUptime = firstUp.uptimeMillis + viewConfiguration.doubleTapMinTimeMillis
    var change: PointerInputChange
    do {
        change = awaitFirstDown()
    } while (change.uptimeMillis < minUptime)
    change
}


private fun PointerEvent.consumePositionChanges() {
    changes.fastForEach {
        if (it.positionChanged()) {
            it.consume()
        }
    }
}


private class TouchSlop(private val threshold: Float) {
    private var zoom = 1f
    private var pan = Offset.Zero
    private var _isPast = false

    fun isPast(event: PointerEvent): Boolean {
        if (_isPast) {
            return true
        }

        zoom *= event.calculateZoom()
        pan += event.calculatePan()
        val zoomMotion = abs(1 - zoom) * event.calculateCentroidSize(useCurrent = true)
        val panMotion = pan.getDistance()
        _isPast = zoomMotion > threshold || panMotion > threshold

        return _isPast
    }
}

fun Modifier.zoom(
    zoomState: ZoomState,
    enableOneFingerZoom: Boolean = true,
    onTap: (position: Offset) -> Unit = {},
    onDoubleTap: suspend (position: Offset) -> Unit = { position -> zoomState.toggleScale(2.5f, position) },
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "zoomable"
        properties["zoomState"] = zoomState
    }
) {
    val scope = rememberCoroutineScope()
    Modifier
        .onSizeChanged { size ->
            zoomState.setLayoutSize(size.toSize())
        }
        .pointerInput(zoomState) {
            detectTransformGestures(
                onGestureStart = { zoomState.startGesture() },
                onGesture = { centroid, pan, zoom, timeMillis ->
                    val canConsume = zoomState.canConsumeGesture(pan = pan, zoom = zoom)
                    if (canConsume) {
                        scope.launch {
                            zoomState.applyGesture(
                                pan = pan,
                                zoom = zoom,
                                position = centroid,
                                timeMillis = timeMillis,
                            )
                        }
                    }
                    canConsume
                },
                onGestureEnd = {
                    scope.launch {
                        zoomState.endGesture()
                    }
                },
                onTap = onTap,
                onDoubleTap = { position ->
                    scope.launch {
                        onDoubleTap(position)
                    }
                },
                enableOneFingerZoom = enableOneFingerZoom,
            )
        }
        .graphicsLayer {
            scaleX = zoomState.scale
            scaleY = zoomState.scale
            translationX = zoomState.offsetX
            translationY = zoomState.offsetY
        }
}

suspend fun ZoomState.toggleScale(
    targetScale: Float,
    position: Offset,
    animationSpec: AnimationSpec<Float> = spring(),
) {
    val newScale = if (scale == 1f) targetScale else 1f
    changeScale(newScale, position, animationSpec)
}


@Stable
class ZoomState(
    @FloatRange(from = 1.0) private val maxScale: Float = 5f,
    private var contentSize: Size = Size.Zero,
    private val velocityDecay: DecayAnimationSpec<Float> = exponentialDecay(),
) {
    init {
        require(maxScale >= 1.0f) { "maxScale must be at least 1.0." }
    }

    private var _scale = Animatable(1f).apply {
        updateBounds(0.9f, maxScale)
    }

    val scale: Float
        get() = _scale.value

    private var _offsetX = Animatable(0f)

    val offsetX: Float
        get() = _offsetX.value

    private var _offsetY = Animatable(0f)

    val offsetY: Float
        get() = _offsetY.value

    private var layoutSize = Size.Zero

    fun setLayoutSize(size: Size) {
        layoutSize = size
        updateFitContentSize()
    }


    fun setContentSize(size: Size) {
        contentSize = size
        updateFitContentSize()
    }

    private var fitContentSize = Size.Zero
    private fun updateFitContentSize() {
        if (layoutSize == Size.Zero) {
            fitContentSize = Size.Zero
            return
        }

        if (contentSize == Size.Zero) {
            fitContentSize = layoutSize
            return
        }

        val contentAspectRatio = contentSize.width / contentSize.height
        val layoutAspectRatio = layoutSize.width / layoutSize.height

        fitContentSize = if (contentAspectRatio > layoutAspectRatio) {
            contentSize * (layoutSize.width / contentSize.width)
        } else {
            contentSize * (layoutSize.height / contentSize.height)
        }
    }

    suspend fun reset() = coroutineScope {
        launch { _scale.snapTo(1f) }
        _offsetX.updateBounds(0f, 0f)
        launch { _offsetX.snapTo(0f) }
        _offsetY.updateBounds(0f, 0f)
        launch { _offsetY.snapTo(0f) }
    }

    private var shouldConsumeEvent: Boolean? = null
    private val velocityTracker = VelocityTracker()

    internal fun startGesture() {
        shouldConsumeEvent = null
        velocityTracker.resetTracking()
    }

    internal fun canConsumeGesture(pan: Offset, zoom: Float): Boolean {
        return shouldConsumeEvent ?: run {
            var consume = true
            if (zoom == 1f) { // One finger gesture
                if (scale == 1f) {  // Not zoomed
                    consume = false
                } else {
                    val ratio = (abs(pan.x) / abs(pan.y))
                    if (ratio > 3) {   // Horizontal drag
                        if ((pan.x < 0) && (_offsetX.value == _offsetX.lowerBound)) {
                            // Drag R to L when right edge of the content is shown.
                            consume = false
                        }
                        if ((pan.x > 0) && (_offsetX.value == _offsetX.upperBound)) {
                            // Drag L to R when left edge of the content is shown.
                            consume = false
                        }
                    } else if (ratio < 0.33) { // Vertical drag
                        if ((pan.y < 0) && (_offsetY.value == _offsetY.lowerBound)) {
                            // Drag bottom to top when bottom edge of the content is shown.
                            consume = false
                        }
                        if ((pan.y > 0) && (_offsetY.value == _offsetY.upperBound)) {
                            // Drag top to bottom when top edge of the content is shown.
                            consume = false
                        }
                    }
                }
            }
            shouldConsumeEvent = consume
            consume
        }
    }

    internal suspend fun applyGesture(
        pan: Offset,
        zoom: Float,
        position: Offset,
        timeMillis: Long
    ) = coroutineScope {
        val newScale = (scale * zoom).coerceIn(0.9f, maxScale)
        val newOffset = calculateNewOffset(newScale, position, pan)
        val newBounds = calculateNewBounds(newScale)

        _offsetX.updateBounds(newBounds.left, newBounds.right)
        launch {
            _offsetX.snapTo(newOffset.x)
        }

        _offsetY.updateBounds(newBounds.top, newBounds.bottom)
        launch {
            _offsetY.snapTo(newOffset.y)
        }

        launch {
            _scale.snapTo(newScale)
        }

        if (zoom == 1f) {
            velocityTracker.addPosition(timeMillis, position)
        } else {
            velocityTracker.resetTracking()
        }
    }

    suspend fun changeScale(
        targetScale: Float,
        position: Offset,
        animationSpec: AnimationSpec<Float> = spring(),
    ) = coroutineScope {
        val newScale = targetScale.coerceIn(1f, maxScale)
        val newOffset = calculateNewOffset(newScale, position, Offset.Zero)
        val newBounds = calculateNewBounds(newScale)

        val x = newOffset.x.coerceIn(newBounds.left, newBounds.right)
        launch {
            _offsetX.updateBounds(null, null)
            _offsetX.animateTo(x, animationSpec)
            _offsetX.updateBounds(newBounds.left, newBounds.right)
        }

        val y = newOffset.y.coerceIn(newBounds.top, newBounds.bottom)
        launch {
            _offsetY.updateBounds(null, null)
            _offsetY.animateTo(y, animationSpec)
            _offsetY.updateBounds(newBounds.top, newBounds.bottom)
        }

        launch {
            _scale.animateTo(newScale, animationSpec)
        }
    }

    private fun calculateNewOffset(
        newScale: Float,
        position: Offset,
        pan: Offset,
    ): Offset {
        val size = fitContentSize * scale
        val newSize = fitContentSize * newScale
        val deltaWidth = newSize.width - size.width
        val deltaHeight = newSize.height - size.height

        val xInContent = position.x - offsetX + (size.width - layoutSize.width) * 0.5f
        val yInContent = position.y - offsetY + (size.height - layoutSize.height) * 0.5f

        val deltaX = (deltaWidth * 0.5f) - (deltaWidth * xInContent / size.width)
        val deltaY = (deltaHeight * 0.5f) - (deltaHeight * yInContent / size.height)

        val x = offsetX + pan.x + deltaX
        val y = offsetY + pan.y + deltaY

        return Offset(x, y)
    }

    private fun calculateNewBounds(
        newScale: Float,
    ): Rect {
        val newSize = fitContentSize * newScale
        val boundX = java.lang.Float.max((newSize.width - layoutSize.width), 0f) * 0.5f
        val boundY = java.lang.Float.max((newSize.height - layoutSize.height), 0f) * 0.5f
        return Rect(-boundX, -boundY, boundX, boundY)
    }

    internal suspend fun endGesture() = coroutineScope {
        val velocity = velocityTracker.calculateVelocity()
        if (velocity.x != 0f) {
            launch {
                _offsetX.animateDecay(velocity.x, velocityDecay)
            }
        }
        if (velocity.y != 0f) {
            launch {
                _offsetY.animateDecay(velocity.y, velocityDecay)
            }
        }

        if (_scale.value < 1f) {
            launch {
                _scale.animateTo(1f)
            }
        }
    }

    suspend fun centerByContentCoordinate(
        offset: Offset,
        scale: Float = 3f,
        animationSpec: AnimationSpec<Float> = tween(700),
    ) = coroutineScope {
        val fitContentSizeFactor = fitContentSize.width / contentSize.width

        val boundX = java.lang.Float.max((fitContentSize.width * scale - layoutSize.width), 0f) / 2f
        val boundY = java.lang.Float.max((fitContentSize.height * scale - layoutSize.height), 0f) / 2f

        suspend fun executeZoomWithAnimation() {
            listOf(
                async {
                    val fixedTargetOffsetX =
                        ((fitContentSize.width / 2 - offset.x * fitContentSizeFactor) * scale)
                            .coerceIn(
                                minimumValue = -boundX,
                                maximumValue = boundX,
                            ) // Adjust zoom target position to prevent execute zoom animation to out of content boundaries
                    _offsetX.animateTo(fixedTargetOffsetX, animationSpec)
                },
                async {
                    val fixedTargetOffsetY = ((fitContentSize.height / 2 - offset.y * fitContentSizeFactor) * scale)
                        .coerceIn(minimumValue = -boundY, maximumValue = boundY)
                    _offsetY.animateTo(fixedTargetOffsetY, animationSpec)
                },
                async {
                    _scale.animateTo(scale, animationSpec)
                },
            ).awaitAll()
        }

        if (scale > _scale.value) {
            _offsetX.updateBounds(-boundX, boundX)
            _offsetY.updateBounds(-boundY, boundY)
            executeZoomWithAnimation()
        } else {
            executeZoomWithAnimation()
            _offsetX.updateBounds(-boundX, boundX)
            _offsetY.updateBounds(-boundY, boundY)
        }
    }

    suspend fun centerByLayoutCoordinate(
        offset: Offset,
        scale: Float = 3f,
        animationSpec: AnimationSpec<Float> = tween(700),
    ) = coroutineScope {

        val boundX = java.lang.Float.max((fitContentSize.width * scale - layoutSize.width), 0f) / 2f
        val boundY = java.lang.Float.max((fitContentSize.height * scale - layoutSize.height), 0f) / 2f

        suspend fun executeZoomWithAnimation() {
            listOf(
                async {
                    val fixedTargetOffsetX =
                        ((layoutSize.width / 2 - offset.x) * scale)
                            .coerceIn(
                                minimumValue = -boundX,
                                maximumValue = boundX,
                            )
                    _offsetX.animateTo(fixedTargetOffsetX, animationSpec)
                },
                async {
                    val fixedTargetOffsetY = ((layoutSize.height / 2 - offset.y) * scale)
                        .coerceIn(minimumValue = -boundY, maximumValue = boundY)
                    _offsetY.animateTo(fixedTargetOffsetY, animationSpec)
                },
                async {
                    _scale.animateTo(scale, animationSpec)
                },
            ).awaitAll()
        }

        if (scale > _scale.value) {
            _offsetX.updateBounds(-boundX, boundX)
            _offsetY.updateBounds(-boundY, boundY)
            executeZoomWithAnimation()
        } else {
            executeZoomWithAnimation()
            _offsetX.updateBounds(-boundX, boundX)
            _offsetY.updateBounds(-boundY, boundY)
        }
    }
}
