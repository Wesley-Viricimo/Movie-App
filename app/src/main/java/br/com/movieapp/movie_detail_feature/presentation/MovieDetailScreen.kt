package br.com.movieapp.movie_detail_feature.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.movie_detail_feature.presentation.components.MovieDetailsContent
import br.com.movieapp.movie_detail_feature.presentation.state.MovieDetailState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    id: Int?,
    uiState: MovieDetailState,
    onAddFavorite: (Movie) -> Unit,
    checkedFavorite: (MovieDetailsEvent.CheckedFavorite) -> Unit,
    getMovieDetail: (MovieDetailsEvent.GetMovieDetail) -> Unit
) {
    val pagingMoviesSimilar = uiState.results.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {//Cria um fluxo assíncrono(paralelo) para buscar o detalhe do filme, a chave key1 quando recebe o valor true garante que o bloco de código executado abaixo será executado quando quando a função movie detail screen for chamada
        if (id != null) {
            getMovieDetail(MovieDetailsEvent.GetMovieDetail(id))
            checkedFavorite(MovieDetailsEvent.CheckedFavorite(id))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.detail_movie),
                        color = white
                    )
                },
                backgroundColor = black
            )
        },
        content = {
            MovieDetailsContent(
                movieDetails = uiState.movieDetails,
                pagingMoviesSimilar = pagingMoviesSimilar,
                isLoading = uiState.isLoading,
                isError = uiState.error,
                iconColor = uiState.iconColor,
                onAddFavorite = {movie ->
                    onAddFavorite(movie)
                }
            )
        }
    )
}