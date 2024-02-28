package br.com.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp //Anotação informa para o Hilt que essa classe da aplicação irá gerar o código de injeção de dependência para tod o aplicativo
class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}