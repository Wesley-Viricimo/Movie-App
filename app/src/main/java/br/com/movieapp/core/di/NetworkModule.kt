package br.com.movieapp.core.di

import br.com.movieapp.BuildConfig
import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.ParamsInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module //Anotação indica que essa classe fornecerá os objetos para serem injetados em outras partes do aplicativo
@InstallIn(SingletonComponent::class) //Anotação indica em qual componente do hilt o módulo será instalado - Neste caso indicará que este SingletonComponent será responsável por gerenciar as instâncias de objetos com ciclo de vida do tipo singleton, ou seja existirá apenas uma instância da aplicação que irá sobreviver enquanto o aplicativo estiver sendo executado
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides //Indica que o hilt pode fornecer esta instância para outras classes que precisarão dela
    fun provideParamsInterceptor() : ParamsInterceptor {
        return ParamsInterceptor()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor { //Método que irá exibir os logs caso esteja executando a aplicação em ambiente de debug
        return HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }
    }

    @Provides
    fun provideOkHttpClient( //Método será responsável por realizar as requisições HTTP
        paramsInterceptor: ParamsInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(paramsInterceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory() : GsonConverterFactory { //Método responsável por converter o Json para o kotlin
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideMovieService(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) : MovieService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(MovieService::class.java)
    }
}