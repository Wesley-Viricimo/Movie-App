package br.com.movieapp.movie_popular_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.movieapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    voteAverage: Double,
    imageUrl: String,
    id: Int,
    onClick: (id: Int) -> Unit
) {
    Box(modifier = modifier) {
        MovieRate(
            rate = voteAverage,
            modifier = Modifier
                .align(Alignment.BottomStart) //Alinhamento
                .zIndex(2f) //Indica que o componente deve estar acima dos outros componentes
                .padding(start = 6.dp, bottom = 8.dp) //Espaço do inicio e de baixo
                .background(Color.Black) // Cor de fundo
        )
        Card(
            modifier = Modifier
                .fillMaxSize() //Deve preencher toda a tela
                .height(200.dp) //Altura
                .padding(4.dp) //Espaçamento da margem
                .clickable {
                    onClick(id)
                },
            shape = RoundedCornerShape(8.dp), //Borda arredondada
            elevation = 8.dp
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current) //Passando contexto em que imagem será carregada
                        .data(imageUrl) //Passando a imagem que será carregada
                        .crossfade(true) //Efeito ao carregar a imagem
                        .error(R.drawable.ic_error_image)
                        .placeholder(R.drawable.ic_placeholder)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight, //Para imagem preencher toda a altura do card
                    modifier = Modifier
                        .fillMaxWidth() //Dentro do box a imagem preencherá toda a sua largura
                        .align(Alignment.BottomCenter)
                        .background(Color.Black)
                        .clip(RoundedCornerShape(8.dp)) //Colocar borda dentro do box
                    )
            }
        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(modifier = Modifier ,voteAverage = 7.2, imageUrl = "", id = 1, onClick = {})
}