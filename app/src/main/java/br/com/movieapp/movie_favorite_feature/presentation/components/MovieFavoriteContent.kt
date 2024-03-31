package br.com.movieapp.movie_favorite_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.ui.theme.black

@Composable
fun MovieFavoriteContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    movies: List<Movie>,
    onClick: (id: Int) -> Unit
) {
    Box(
        modifier = modifier.background(black)
    ) {
        LazyColumn( //Substitui a recycler view no compose
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues,
            content = {
                items(
                    items = movies,
                    key = { item: Movie -> //key é usado para otimizar renderização do lazy column, quando a lista de filmes é atualizada esta chave é usada para identificar cada item da lista e permite que o lazy column identifique quando item foi adicionado, removido ou atualizado e evita renderização desnecessária de todos os itens da lista
                        item.id
                    }
                ) { movie ->
                    MovieFavoriteItem(
                        movie = movie,
                        onClick = onClick
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun MovieFavoriteContentPreview() {
    MovieFavoriteContent(
        modifier = Modifier,
        paddingValues = PaddingValues(),
        movies = listOf(
            Movie(
                id = 1,
                title = "Homem Aranha",
                voteAverage = 7.89,
                imageUrl = ""
            ),
            Movie(
                id = 2,
                title = "Homem de Ferro",
                voteAverage = 7.89,
                imageUrl = ""
            ),
        ),
        onClick = {

        }
    )
}