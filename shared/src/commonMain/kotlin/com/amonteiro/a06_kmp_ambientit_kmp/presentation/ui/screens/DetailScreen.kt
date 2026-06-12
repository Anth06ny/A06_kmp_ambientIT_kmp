package com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.screens

import a06_kmp_ambientit_kmp.shared.generated.resources.Res
import a06_kmp_ambientit_kmp.shared.generated.resources.error
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.DescriptionEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.TempEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WeatherEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WindEntity
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.theme.A06_kmp_ambientit2Theme
import org.jetbrains.compose.resources.painterResource


@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
            or UI_MODE_TYPE_NORMAL
)
@Composable
fun DetailScreenPreview() {
    A06_kmp_ambientit2Theme() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetailScreen(
                modifier = Modifier.padding(innerPadding),
                //jeu de donnée pour la Preview
                data = WeatherEntity(
                    id = 2,
                    name = "Toulouse",
                    main = TempEntity(temp = 22.3),
                    weather = listOf(
                        DescriptionEntity(description = "partiellement nuageux", icon = "https://picsum.photos/201")
                    ),
                    wind = WindEntity(speed = 3.2)
                )
            )
        }
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    data: WeatherEntity,
    onBtBackClick: () -> Unit = {}
) {

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = data.name,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )

        AsyncImage(
            model = data.weather.firstOrNull()?.icon ?: "",
            contentDescription = data.weather.firstOrNull()?.description ?: "",
            contentScale = ContentScale.FillWidth,
            //Pour toto.png. Si besoin de choisir l'import pour la classe R, c'est celle de votre package
            //Image d'échec de chargement qui sera utilisé par la preview
            error = painterResource(Res.drawable.error),
            //Image d'attente.
            //placeholder = painterResource(R.drawable.toto),
            onError = { println(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Text(
            text = data.getResume(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))

        Button(
            onClick = onBtBackClick,
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            modifier = Modifier
                .padding(8.dp)
                .align(CenterHorizontally)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Retour")
        }
    }
}
