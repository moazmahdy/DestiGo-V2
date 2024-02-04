package com.mobilebreakero.core_ui.components

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun ShowDatePickerDialog(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                    .format(Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time)
                onDateSelected(formattedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            if (datePickerDialog.isShowing) {
                datePickerDialog.dismiss()
            }
        }
    }

    LaunchedEffect(selectedDate) {
        if (!datePickerDialog.isShowing) {
            datePickerDialog.updateDate(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
}
