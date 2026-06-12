package com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.screens.DetailScreen
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.ui.screens.SearchScreen
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.viewmodel.MainViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel


class Routes {
    @Serializable
    data object SearchRoute

    @Serializable
    data class DetailRoute(val id: Int)
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navHostController: NavHostController = rememberNavController()
    val mainViewModel : MainViewModel = koinViewModel<MainViewModel>()

    NavHost(navController = navHostController,  startDestination = Routes.SearchRoute, modifier = modifier) {

        //Route 1 vers notre SearchScreen
        composable<Routes.SearchRoute> {

            //Si créé ici, il sera propre à cet instance de l'écran
            //val mainViewModel : MainViewModel = viewModel()

            //on peut passer le navHostController à un écran s'il déclenche des navigations
            SearchScreen(mainViewModel = mainViewModel ) {
                navHostController.navigate(Routes.DetailRoute(it))
            }
        }

        //Route 2 vers un écran de détail
        composable<Routes.DetailRoute> {
            val detailRoute = it.toRoute<Routes.DetailRoute>()
            val weatherEntity = mainViewModel.dataList.collectAsStateWithLifecycle().value.first { it.id == detailRoute.id }

            DetailScreen(data = weatherEntity,
                onBtBackClick = {navHostController.popBackStack()}
            )
        }
    }
}
