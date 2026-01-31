package com.nkot117.smartgo

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.nkot117.core.notification.ReminderScheduler
import com.nkot117.core.ui.theme.SmartGoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var scheduler: ReminderScheduler
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            // granted が true なら通知出せる（Android 13+）
            // false ならユーザーが拒否
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPostNotificationsIfNeeded()

        setContent {
            AppContent()

            // TODO: 検証が終わり次第削除
            Button(
                onClick = { scheduler.scheduleNextReminder(0, 1) }
            ) {
                Text("1分後に通知")
            }

            // TODO: ここまで
        }
    }

    private fun requestPostNotificationsIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val permission = android.Manifest.permission.POST_NOTIFICATIONS
        val granted = ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED

        if (!granted) {
            requestPermissionLauncher.launch(permission)
        }
    }
}


@Composable
fun AppContent() {
    SmartGoTheme {
        AppScaffold()
    }
}

