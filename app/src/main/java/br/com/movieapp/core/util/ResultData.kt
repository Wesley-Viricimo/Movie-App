package br.com.movieapp.core.util

sealed class ResultData<out T> { //Classe irá representar os diferentes estados das operações que serão realizadas

    object Loading : ResultData<Nothing>()
    data class Success<out T>(val data: T?) : ResultData<T>()
    data class Failure(val e : Exception?) : ResultData<Nothing>()
}