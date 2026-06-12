package com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WeatherEntity
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.screens.PictureRowItem

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    urlList: List<WeatherEntity>,
    onPictureClick: (Int) -> Unit
) {
    LazyColumn  (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(urlList.size) {
            PictureRowItem(
                data = urlList[it],
                onPictureClick = onPictureClick
            )
        }
    }
}