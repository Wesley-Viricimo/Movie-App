package br.com.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GetMovieDetailUseCase {
    operator fun invoke(params: Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>>

    data class Params(
        val movieId: Int
    )
}

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieDetailsRepository
) : GetMovieDetailUseCase {

    override fun invoke(params: GetMovieDetailUseCase.Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val movieDetails = repository.getMovieDetails(params.movieId)
                val movieSimilar = repository.getMoviesSimilar(
                    movieId = params.movieId,
                    pagingConfing = PagingConfig(
                        pageSize = 20,
                        initialLoadSize = 20
                    )
                )
                emit(ResultData.Success(movieSimilar to movieDetails))
            } catch (exception: HttpException) {
                emit(ResultData.Failure(exception))
            } catch (exception: IOException) {
                emit(ResultData.Failure(exception))
            }
        }.flowOn(Dispatchers.IO)
    }

}