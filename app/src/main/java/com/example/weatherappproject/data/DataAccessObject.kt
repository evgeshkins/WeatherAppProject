package com.example.weatherappproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/*
Suspension Functions (Функции приостановки): Ключевое понятие корутин — это приостановка выполнения функции без блокирования потока.
Функции могут приостанавливать свое выполнение с помощью ключевого слова suspend, чтобы позволить другим операциям продолжиться,
а затем продолжить свою работу.
*/


// интерфейс для заполнения бд, изменения и удаления данных в бд
@Dao
interface DataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // в случае добавления элемента с существующим id, элемент будет перезаписан
    // используются корутины, т.к. операция записи - трудоемкая
    suspend fun insertCity(cityEntity: CityEntity)
    @Delete
    suspend fun deleteCity(cityEntity: CityEntity)
    @Query("SELECT * FROM city")
    fun getAllCities(): Flow<List<CityEntity>> // Здесь тоже используется корутина - класс flow.
                                              // В случае с suspend, после каждого изменения в бд, необходимо было бы вновь вызывать функцию
                                              // Здесь же этого делать не нужно. При изменении по запросу SELECT, flow будет выдавать новое

}