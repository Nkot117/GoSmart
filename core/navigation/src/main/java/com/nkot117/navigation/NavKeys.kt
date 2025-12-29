package com.nkot117.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppNavKey : NavKey {
    @Serializable
    data object Home : AppNavKey

    @Serializable
    data object Checklist : AppNavKey
}