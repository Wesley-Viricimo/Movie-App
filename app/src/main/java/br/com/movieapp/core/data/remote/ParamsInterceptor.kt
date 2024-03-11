package br.com.movieapp.core.data.remote

import br.com.movieapp.BuildConfig
import br.com.movieapp.core.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ParamsInterceptor : Interceptor {

    //Método responsável por interceptar as requisições antes que elas sejam enviadas para o servidor (API)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request() //Obtendo requisição original
        val url = request.url.newBuilder()
            .addQueryParameter(Constants.LANGUAGE_PARAM, Constants.LANGUAGE_VALUE)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .addHeader(Constants.API_TOKEN_PARAM, BuildConfig.JWT_TOKEN)
            .build()

        return chain.proceed(newRequest)
    }
}