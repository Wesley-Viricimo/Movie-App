package br.com.movieapp.core.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.yellow

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
   val items = listOf(
       BottomNavItem.MoviePopular,
       BottomNavItem.MovieSearch,
       BottomNavItem.MovieFavorite,
   )

    BottomNavigation(
        contentColor = yellow,
        backgroundColor = black)
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState() //Contem informações da tela atual que será exibida
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { destination ->
            BottomNavigationItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true //Opção que permite que a nova tela seja colocada no topo da pilha de navegação e evita duplicação na mesma
                    }
                },
                icon = {
                    Icon(imageVector = destination.icon, contentDescription = null)
                },
                label = {
                    Text(text = destination.title)
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState() //Propriedade que representa a entrada atual da pilha de navegação, contém as informações da rota atual que o usuário está acessando
    return navBackStackEntry?.destination?.route //Retorna a rota de navegação atual
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController())
}
