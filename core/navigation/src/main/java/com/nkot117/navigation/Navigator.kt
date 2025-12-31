package com.nkot117.navigation

import androidx.compose.runtime.mutableStateListOf

class Navigator(
    start: AppNavKey = AppNavKey.Home,
) {
    val backStack = mutableStateListOf(start)

    val current: AppNavKey
        get() = backStack.last()

    /**
     * 1画面進む
     */
    fun push(navKey: AppNavKey) {
        backStack.add(navKey)
    }

    /**
     * 1画面戻る
     */
    fun pop() {
        backStack.removeLastOrNull()
    }

    /**
     * 任意地点まで戻る
     */
    fun popTo(navKey: AppNavKey) {
        backStack.dropLastWhile { it != navKey }
    }
}