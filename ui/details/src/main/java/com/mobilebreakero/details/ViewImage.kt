package com.mobilebreakero.details

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.components.ElevatedButton
import com.mobilebreakero.common_ui.extensions.rememberZoomState
import com.mobilebreakero.common_ui.extensions.zoom
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


@Composable
fun ViewImage(
    image: String,
) {
    val context = LocalContext.current
    var isImageSaved by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(title = "Save", onClick = {
                saveImageIntoDevice(context, image).let {
                    isImageSaved = true
                }
            }, icon = R.drawable.save)
            Spacer(modifier = Modifier.width(10.dp))
            ElevatedButton(
                title = "Share",
                onClick = { shareImage(context, image) },
                icon = R.drawable.share
            )
        }

        if (isImageSaved) {
            Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
        }

        val zoomState = rememberZoomState()
        Spacer(modifier = Modifier.height(10.dp))
        SubcomposeAsyncImage(
            model = image,
            contentDescription = null,
            loading = {
                LoadingIndicator()
            },
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .zoom(
                    zoomState = zoomState,
                    onDoubleTap = { position ->
                        val targetScale = when {
                            zoomState.scale < 2f -> 2f
                            zoomState.scale < 4f -> 4f
                            else -> 1f
                        }
                        zoomState.changeScale(targetScale, position)
                    }
                )
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun saveImageIntoDevice(context: Context, image: String) {
    GlobalScope.launch(Dispatchers.Main) {
        val bitmap = loadBitmapFromUrl(context, image)
        bitmap?.let {
            val uri = saveBitmapToCache(context, bitmap)
            val openIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                setDataAndType(uri, "image/jpeg")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(openIntent)
        }
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val contentResolver = context.contentResolver
    val imageUri = contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )

    try {
        imageUri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                    return null
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    return imageUri
}


@OptIn(DelicateCoroutinesApi::class)
fun shareImage(context: Context, image: String) {
    GlobalScope.launch(Dispatchers.Main) {
        val bitmap = loadBitmapFromUrl(context, image)
        bitmap?.let {
            val uri = saveBitmapToCache(context, bitmap)
            uri?.let {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/jpeg"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share Image")
                context.startActivity(shareIntent)
            }
        }
    }
}

suspend fun loadBitmapFromUrl(context: Context, imageUrl: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .target { drawable ->
                    if (drawable is BitmapDrawable) {
                        return@target
                    }
                }
                .build()

            val imageLoader = ImageLoader(context)
            val result = imageLoader.execute(request)

            if (result is SuccessResult) {
                result.drawable.toBitmap()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}