package com.mobilebreakero.destigo

import com.mobilebreakero.ui.common.components.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TripManager(private val viewModel: com.mobilebreakero.ui.common.components.MainViewModel) {

    private var tripFinishedListener: (() -> Unit)? = null
    private var timerJob: Job? = null


    fun start() {
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            checkEndDatePeriodically()
        }
    }


    private suspend fun checkEndDatePeriodically() {
        while (isActive) {
            delay(CHECK_INTERVAL)
            val currentDate = getCurrentDate()
            val trips = viewModel.tripsResult

            for (trip in trips) {
                val endDate = trip.endDate?.let {
                    SimpleDateFormat(
                        "MM/dd/yyyy",
                        Locale.getDefault()
                    ).parse(it)
                }
                if (endDate != null && currentDate > endDate) {
                    tripFinishedListener?.invoke()
                    trip.id?.let {
                        viewModel.isTripFinished(it, true)
                    }
                }
            }
        }
    }

    private fun getCurrentDate(): Date {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val midnightDate = sdf.parse(currentDate)
        return midnightDate ?: Date()
    }

    fun cancelTimer() {
        timerJob?.cancel()
    }

    companion object {
        private const val CHECK_INTERVAL = 24 * 60 * 60 * 1000L
    }
}