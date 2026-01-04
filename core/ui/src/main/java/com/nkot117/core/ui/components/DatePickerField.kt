package com.nkot117.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.common.toDisplayYmdSlash
import com.nkot117.core.common.toLocalDate
import com.nkot117.core.ui.theme.SmartGoTheme
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    selectedDateMillis: Long?,
    onDateChange: (Long?) -> Unit,
    confirmButtonLabel: String,
    cancelButtonLabel: String,
    modifier: Modifier = Modifier,
    formLabel: String? = null,
    placeholder: String? = null,
) {
    var visibleDialog by rememberSaveable { mutableStateOf(false) }

    val dateText = selectedDateMillis?.toLocalDate()?.toDisplayYmdSlash().orEmpty()
    val shape = RoundedCornerShape(12.dp)

    // テキストフィールド
    DatePickerTextField(
        value = dateText,
        onOpenDialog = { visibleDialog = true },
        modifier = modifier,
        shape = shape,
        formLabel = formLabel,
        placeholder = placeholder,
    )

    // ピッカーモーダル
    DatePickerModal(
        visible = visibleDialog,
        initialSelectedDateMillis = selectedDateMillis,
        confirmButtonLabel = confirmButtonLabel,
        cancelButtonLabel = cancelButtonLabel,
        onConfirm = { pickedMillis ->
            onDateChange(pickedMillis)
            visibleDialog = false
        },
        onDismiss = { visibleDialog = false }
    )
}

@Composable
private fun DatePickerTextField(
    value: String,
    onOpenDialog: () -> Unit,
    modifier: Modifier,
    shape: RoundedCornerShape,
    formLabel: String?,
    placeholder: String?,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        label = formLabel?.let { labelText ->
            { Text(text = labelText, style = MaterialTheme.typography.labelSmall) }
        },
        placeholder = placeholder?.let { placeholderText ->
            { Text(text = placeholderText, style = MaterialTheme.typography.labelSmall) }
        },
        trailingIcon = {
            IconButton(onClick = onOpenDialog) {
                Icon(Icons.Default.DateRange, contentDescription = null)
            }
        },
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape)
            .clickable { onOpenDialog() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerModal(
    visible: Boolean,
    initialSelectedDateMillis: Long?,
    confirmButtonLabel: String,
    cancelButtonLabel: String,
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    if (!visible) return

    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(state.selectedDateMillis) }) {
                Text(text = confirmButtonLabel, style = MaterialTheme.typography.labelSmall)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = cancelButtonLabel, style = MaterialTheme.typography.labelSmall)
            }
        }
    ) {
        DatePicker(state = state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DatePickerFieldPreview_Empty() {
    SmartGoTheme {
        var selected by remember { mutableStateOf<Long?>(null) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DatePickerField(
                selectedDateMillis = selected,
                onDateChange = { selected = it },
                confirmButtonLabel = "OK",
                cancelButtonLabel = "キャンセル",
                formLabel = "日付",
                placeholder = "追加する日付",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DatePickerFieldPreview_Selected() {
    SmartGoTheme {
        var selected: Long? by remember {
            mutableStateOf(
                LocalDate.of(2026, 1, 4)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DatePickerField(
                selectedDateMillis = selected,
                onDateChange = { selected = it },
                confirmButtonLabel = "OK",
                cancelButtonLabel = "キャンセル",
                formLabel = "日付",
                placeholder = "追加する日付",
            )
        }
    }
}