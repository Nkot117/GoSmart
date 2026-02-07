package com.nkot117.feature.settings

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.components.SecondaryButton
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.Primary500
import com.nkot117.core.ui.theme.TextMain
import com.nkot117.core.ui.theme.TextSub


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingScreenRoute(
    contentPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchReminderSettings()
    }

    SettingsScreen(
        contentPadding, state,
        viewModel::setReminderEnabled,
        viewModel::setReminderTime,
        viewModel::saveSettings,
        onBack
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    contentPadding: PaddingValues,
    state: SettingsUiState,
    setEnabled: (Boolean) -> Unit,
    setReminderTime: (Int, Int) -> Unit,
    saveSettings: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val topColor = BgWorkdayTop
    val bottomColor = BgWorkdayBottom
    val timePickerState = rememberTimePickerState(
        initialHour = state.reminder.hour,
        initialMinute = state.reminder.minute
    )
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

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
                reminderSettings = state.reminder,
                onToggle = { isEnabled ->
                    setEnabled(isEnabled)
                },
                onTimeClick = {
                    showTimePicker = true
                },
                onSave = {
                    saveSettings()
                    onBack()
                },
                onShowPermissionDialogChange = { show ->
                    showPermissionDialog = show
                }
            )
        }
    }

    if (showTimePicker) {
        NotificationTimePickerDialog(
            timePickerState = timePickerState,
            onConfirm = {
                setReminderTime(timePickerState.hour, timePickerState.minute)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = {
                Text("通知の許可が必要です")
            },
            text = {
                Text("リマインダー通知を受け取るには、設定画面で通知を許可してください。")
            },
            confirmButton = {
                PrimaryButton(
                    onClick = {
                        openNotificationSettings(context)
                        showPermissionDialog = false
                    },
                    text = "設定を開く"
                )
            },
            dismissButton = {
                SecondaryButton(
                    onClick = { showPermissionDialog = false },
                    text = "キャンセル"
                )

            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun ReminderSettingsCard(
    reminderSettings: Reminder,
    onToggle: (Boolean) -> Unit,
    onTimeClick: () -> Unit,
    onSave: () -> Unit,
    onShowPermissionDialogChange: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onSave()
        } else {
            onShowPermissionDialogChange(true)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ReminderToggleRow(reminderSettings.enabled, onToggle)
            if (reminderSettings.enabled) {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(Modifier.height(16.dp))
                NotificationTimeRow(onTimeClick, reminderSettings.hour, reminderSettings.minute)
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                onClick = {
                    if (reminderSettings.enabled) {
                        val hasPermission =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED

                        if (hasPermission) {
                            onSave()
                        } else {
                            requestPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    } else {
                        onSave()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "保存する"
            )
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

@SuppressLint("DefaultLocale")
@Composable
private fun NotificationTimeRow(
    onTimeClick: () -> Unit,
    settingHour: Int,
    settingMinute: Int,
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
                "${String.format("%02d", settingHour)}:${String.format("%02d", settingMinute)}",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMain
            )
        }

        Spacer(Modifier.height(5.dp))

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

fun openNotificationSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Surface {
        SettingsScreen(
            contentPadding = PaddingValues(0.dp),
            state = SettingsUiState(),
            setEnabled = {},
            setReminderTime = { _, _ -> },
            saveSettings = {},
            onBack = {},
        )
    }
}