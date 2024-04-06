package br.com.movieapp.movie_favorite_feature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.movie_favorite_feature.presentation.components.MovieFavoriteContent
import br.com.movieapp.movie_favorite_feature.presentation.state.MovieFavoriteState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@Composable
fun MovieFavoriteScreen(
    uiState: MovieFavoriteState,
    navigateToDetailMovie: (Int) -> Unit
    ) {

    val movies = uiState.movies

    Scaffold(
        topBar = {
            MovieAppBar(title = R.string.favorite_movies)
        },
        content = { paddingValues ->
            MovieFavoriteContent(
                paddingValues = paddingValues,
                movies = movies,
                onClick = {movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}

@Preview
@Composable
private fun MovieFavoriteScreenPreview() {
    MovieFavoriteScreen(
        uiState = MovieFavoriteState(),
        navigateToDetailMovie = {}
    )
}