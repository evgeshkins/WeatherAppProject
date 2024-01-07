package com.example.weatherappproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1)
abstract class MainDb: RoomDatabase() {
   abstract val dao: DataAccessObject // переменная интерфейса, использующегося для заполнения, редактирования, удаления данных в бд
    companion object{
        // создание бд
        fun createDataBase(context: Context): MainDb{
            return Room.databaseBuilder(context, MainDb::class.java, "city.db").build()
        }
    }

}