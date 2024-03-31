package br.com.movieapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.core.data.local.entity.MovieEntity

@Database(//Anotação utilizada para identificar que essa classe é uma classe de banco de dados, que será utilizada pelo room para gerar o código necessário para criar e gerenciar o banco de dados
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}