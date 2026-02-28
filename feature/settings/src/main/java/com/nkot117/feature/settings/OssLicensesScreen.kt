package com.nkot117.feature.settings

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.android.produceLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun OssLicensesScreen(@RawRes aboutLibrariesRes: Int, contentPadding: PaddingValues) {
    val libs by produceLibraries(aboutLibrariesRes)

    LibrariesContainer(
        libraries = libs,
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    )
}
