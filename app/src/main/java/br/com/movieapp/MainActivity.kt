package br.com.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import br.com.movieapp.core.presentation.MainScreen
import br.com.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint //Anotação diz pro hilt que será necessário gerar o código para injeção de dependência nesta classe
class MainActivity : ComponentActivity() {

    val apiKey = BuildConfig.JWT_TOKEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MovieAppTheme {
                MainScreen(navController = rememberNavController())
            }
        }
    }
}