package br.com.movieapp.movie_detail_feature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailUseCase
import br.com.movieapp.movie_detail_feature.presentation.state.MovieDetailState
import br.com.movieapp.movie_favorite_feature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.IsMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addMovieFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase
): ViewModel() {

    var uiState by mutableStateOf(MovieDetailState())
        private set

    fun checkedFavorite(checkedFavorite: MovieDetailsEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    fun getMovieDetail(getMovieDetail: MovieDetailsEvent.GetMovieDetail) {
        event(getMovieDetail)
    }

    private fun event(event: MovieDetailsEvent) { //Recebendo evento como parâmetro
        when(event) { //Similar ao switch, testando o evento

            is MovieDetailsEvent.AddFavorite -> {
                viewModelScope.launch {
                    addMovieFavoriteUseCase.invoke(params = AddMovieFavoriteUseCase.Params(
                        movie = event.movie
                    )).collectLatest { result ->
                        when(result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(iconColor = Color.Red)
                            }
                            is ResultData.Failure -> {
                                UtilFunctions.logError("DETAIL", "Erro ao cadastrar filme")
                            }
                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailsEvent.CheckedFavorite -> {
                viewModelScope.launch {
                    isMovieFavoriteUseCase.invoke(params = IsMovieFavoriteUseCase.Params(
                            movieId = event.movieId
                        )).collectLatest { result ->
                        when(result) {
                            is ResultData.Success -> {
                                uiState = if(result.data == true) {
                                    uiState.copy(iconColor = Color.Red)
                                } else {
                                    uiState.copy(iconColor = Color.White)
                                }
                            }
                            is ResultData.Failure -> {
                                UtilFunctions.logError("DETAIL", "Um erro ocorreu!")
                            }
                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailsEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    deleteMovieFavoriteUseCase.invoke(
                        params = DeleteMovieFavoriteUseCase.Params(
                            movie = event.movie
                        )
                    ).collectLatest { result ->
                        when(result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(iconColor = Color.White)
                            }
                            is ResultData.Failure -> {
                                UtilFunctions.logError("DETAIL", "Um erro ocorreu!")
                            }
                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailsEvent.GetMovieDetail -> { //Se o evento for um GetMovieDetail
                viewModelScope.launch {
                    getMovieDetailUseCase.invoke(
                        params = GetMovieDetailUseCase.Params(
                            movieId = event.movieId
                        )
                    ).collect{ resultData ->
                        when(resultData) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    movieDetails = resultData.data?.second,
                                    results = resultData.data?.first ?: emptyFlow()
                                )
                            }

                            is ResultData.Failure -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    error = resultData.e?.message.toString()
                                )
                                UtilFunctions.logError(
                                    "DETAIL - ERROR",
                                    resultData.e?.message.toString()
                                )
                            }

                            is ResultData.Loading -> {
                                uiState = uiState.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}