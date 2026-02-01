package com.nkot117.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.Primary500
import com.nkot117.core.ui.theme.TextMain
import com.nkot117.core.ui.theme.TextSub
import java.time.LocalTime


@Composable
fun SettingScreenRoute(
    contentPadding: PaddingValues,
) {
    SettingsScreen(contentPadding)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    contentPadding: PaddingValues,
) {
    val topColor = BgWorkdayTop
    val bottomColor = BgWorkdayBottom

    var isPushNotificationEnabled by remember { mutableStateOf(true) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var showTimePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime.hour,
        initialMinute = selectedTime.minute
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                "外出前リマインダー設定",
                style = MaterialTheme.typography.titleSmall,
                color = TextMain
            )

            Spacer(Modifier.height(8.dp))

            ReminderSettingsCard(
                isPushNotificationEnabled = isPushNotificationEnabled,
                onToggle = { isEnabled ->
                    isPushNotificationEnabled = isEnabled
                },
                onTimeClick = {
                    showTimePicker = true
                },
                onSave = {
                    // 保存処理をここに追加
                }
            )
        }
    }

    if (showTimePicker) {
        NotificationTimePickerDialog(
            timePickerState = timePickerState,
            onConfirm = {
                selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showTimePicker = false },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Composable
private fun ReminderSettingsCard(
    isPushNotificationEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    onTimeClick: () -> Unit,
    onSave: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ReminderToggleRow(isPushNotificationEnabled, onToggle)

            if (isPushNotificationEnabled) {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(Modifier.height(16.dp))
                NotificationTimeRow(onTimeClick)
                Spacer(Modifier.height(8.dp))
                Spacer(Modifier.height(16.dp))
                PrimaryButton(
                    onClick = onSave,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "保存する"
                )
            }
        }
    }
}

@Composable
private fun ReminderToggleRow(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "外出前リマインダー",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelLarge,
            color = TextMain
        )
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Primary500
            )
        )
    }
}

@Composable
private fun NotificationTimeRow(
    onTimeClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTimeClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "通知時刻",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = TextMain
            )
            Text(
                "8:00",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMain
            )

            Spacer(Modifier.height(5.dp))


        }
        Text(
            "毎日この時刻に通知します",
            style = MaterialTheme.typography.bodySmall,
            color = TextSub,
            modifier = Modifier.padding(start = 0.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTimePickerDialog(
    timePickerState: TimePickerState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("通知時刻を選択") },
        text = { TimePicker(state = timePickerState) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("確定")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Surface {
        SettingsScreen(
            contentPadding = PaddingValues(0.dp)
        )
    }
}