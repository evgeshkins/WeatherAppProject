package com.example.weatherappproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1)
abstract class MainDb: RoomDatabase() {
    // создание бд
    companion object{
        fun createDataBase(context: Context): MainDb{
            return Room.databaseBuilder(context, MainDb::class.java, "city.db").build()
        }
    }

}