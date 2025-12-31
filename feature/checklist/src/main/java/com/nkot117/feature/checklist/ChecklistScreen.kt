package com.nkot117.feature.checklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nkot117.navigation.ChecklistScreenTransitionParams

@Composable
fun ChecklistScreenRoute(
    contentPadding: PaddingValues,
    params: ChecklistScreenTransitionParams,
    viewModel: ChecklistViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier.padding(contentPadding)
    ) {
        Column {
            Text(params.dayType.name)
            Text(params.weatherType.name)
            Text(params.date)
        }
    }

}