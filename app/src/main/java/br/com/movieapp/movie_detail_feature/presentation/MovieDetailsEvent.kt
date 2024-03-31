package br.com.movieapp.movie_detail_feature.presentation

sealed class MovieDetailsEvent {
    data class GetMovieDetail(val movieId: Int): MovieDetailsEvent()
}