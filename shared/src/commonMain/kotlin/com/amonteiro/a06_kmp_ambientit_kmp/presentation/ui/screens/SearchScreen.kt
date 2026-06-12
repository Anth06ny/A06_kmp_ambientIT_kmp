package com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WeatherEntity
import com.amonteiro.a06_kmp_ambientit_kmp.di.apiModule
import com.amonteiro.a06_kmp_ambientit_kmp.di.viewModelModule
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.MyError
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.WeatherGallery
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.theme.A06_kmp_ambientit2Theme
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.viewmodel.MainViewModel
import org.koin.compose.KoinApplicationPreview
import org.koin.compose.viewmodel.koinViewModel

//Code affiché dans la Preview, thème claire, thème sombre
@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Composable
fun SearchScreenPreview() {
    KoinApplicationPreview(application = {
        //androidContext(context) uniquement si coté Android avec Context
        modules(viewModelModule, apiModule)
    }) {
        A06_kmp_ambientit2Theme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
                mainViewModel.loadFakeData(true, "Une erreur")
                SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel,)
            }
        }
    }
}
@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel, onPictureClick: (Int) -> Unit = {}) {
    LaunchedEffect("") {
        mainViewModel.loadWeathers("Toulouse")
    }
    val list = mainViewModel.dataList.collectAsStateWithLifecycle().value
    //.filter { it.name.contains(searchText, true) }
    val runInProgress by mainViewModel.runInProgress.collectAsStateWithLifecycle()
    val errorMessage by mainViewModel.errorMessage.collectAsStateWithLifecycle()


    var searchText by rememberSaveable{ mutableStateOf("Toulouse") }

    Column(modifier = modifier.fillMaxSize()) {
        //On partage le searchText
        SearchBar(searchText = searchText) { searchText = it }

        AnimatedVisibility(visible = runInProgress){
            CircularProgressIndicator()
        }

        //On relie la donnée ErrorMessage au composant graphique MyError
        MyError(errorMessage =  errorMessage)

        WeatherGallery(modifier = Modifier.weight(1f), list, onPictureClick)


        Row(modifier = Modifier.align(CenterHorizontally)) {
            Button(
                onClick = { searchText = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear filter")
            }

            Button(
                onClick = {  mainViewModel.loadWeathers(searchText) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load data")
            }
        }

    }

}

@Composable
fun PictureRowItem(modifier: Modifier = Modifier, data: WeatherEntity, onPictureClick:(Int)->Unit) {
    //Persiste à la recomposition
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        AsyncImage(
            model = data.weather.firstOrNull()?.icon ?: "",
            contentDescription = data.weather.firstOrNull()?.description ?: "",
            contentScale = ContentScale.FillWidth,
            //Pour toto.png. Si besoin de choisir l'import pour la classe R, c'est celle de votre package
            //Image d'échec de chargement qui sera utilisé par la preview
            //error = painterResource(R.drawable.toto),
            //Image d'attente.
            //placeholder = painterResource(R.drawable.toto),
            onError = { println(it) },
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
                .clickable { onPictureClick(data.id) }
        )

        Column(
            Modifier
                .weight(1f)
                .clickable { isExpanded = !isExpanded }) {
            Text(
                text = data.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = if (isExpanded) data.getResume() else (data.getResume().take(20) + "..."),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .animateContentSize()
            )
        }
    }
}


@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: String, onValueChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onValueChange, //Action
        leadingIcon = { //Image d'icône
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        placeholder = { //Texte d'aide
            Text("Votre recherche ici")
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)//Hauteur minimum
            .padding(8.dp)
    )
}