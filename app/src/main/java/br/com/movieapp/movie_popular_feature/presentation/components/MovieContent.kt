package br.com.movieapp.movie_popular_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import br.com.movieapp.core.domain.model.Movie

@Composable
fun MovieContent(
    modifier: Modifier = Modifier,
    pagingMovies: LazyPagingItems<Movie>,
    paddingValues: PaddingValues,
    onclick: (movie: Int) -> Unit
) {
    Box(modifier = modifier.background(Color.Black)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), //Coluna terá 3 filmes
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center, //Alinhar conteúdo ao centro
            modifier = Modifier.fillMaxSize() //Preencher toda a tela
        ) {
            items(pagingMovies.itemCount) { index ->  //Para cada item da lista
                val movie = pagingMovies[index]
                movie?.let { 
                    MovieItem(
                        voteAverage = it.voteAverage,
                        imageUrl = it.imageUrl,
                        id = it.id,
                        onClick = { movieId ->
                            onclick(movieId)
                        }
                    )
                }
            }
        }
    }
}