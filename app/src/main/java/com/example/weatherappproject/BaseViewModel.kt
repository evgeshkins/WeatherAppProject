package com.example.weatherappproject

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.weatherappproject.data.CityEntity
import com.example.weatherappproject.data.MainDb
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class BaseViewModel(val database: MainDb): ViewModel() {

    val citiesList = database.dao.getAllCities()
    val cityInput = mutableStateOf("")
    // переменная дата-класса для проверки того, выбрали мы уже существующую сущность или создаем (null в начале, соответственно)
    var cityEntity: CityEntity? = null

    // функцмя для реализации добавления и изменения данных в бд
    fun insertCity() = viewModelScope.launch {
        // проверка: существует или нет?
        val cityItem = cityEntity?.copy(cityName = cityInput.value) ?: CityEntity(cityName = cityInput.value)
        database.dao.insertCity(cityItem)
        // очишаем cityEntity для новых проверок в дальнейшем
        cityEntity = null
        // очищаем поле для ввода
        cityInput.value = ""
    }
    fun deleteCity(item: CityEntity) = viewModelScope.launch {
        database.dao.deleteCity(item)
    }

    companion object{
        // вместо конструкторов в ViewModel используются фабрики
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create
                        (modelClass: Class<T>, extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return BaseViewModel(database) as T
            }
        }
    }
}