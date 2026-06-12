package com.amonteiro.a06_kmp_ambientit_kmp

import a06_kmp_ambientit_kmp.shared.generated.resources.Res
import a06_kmp_ambientit_kmp.shared.generated.resources.compose_multiplatform
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.AppNavigation
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.theme.A06_kmp_ambientit2Theme
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun App() {
    A06_kmp_ambientit2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}