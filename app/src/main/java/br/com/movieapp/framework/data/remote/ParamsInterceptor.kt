package br.com.movieapp.framework.data.remote

import br.com.movieapp.BuildConfig
import br.com.movieapp.framework.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ParamsInterceptor : Interceptor {

    //Método responsável por interceptar as requisições antes que elas sejam enviadas para o servidor (API)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request() //Obtendo requisição original
        val url = request.url.newBuilder()
            .addQueryParameter(Constants.LANGUAGE_PARAM, Constants.LANGUAGE_VALUE)
            .addQueryParameter(Constants.API_TOKEN_PARAM, BuildConfig.JWT_TOKEN)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}