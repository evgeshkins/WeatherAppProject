package com.example.weatherappproject

import android.app.Application
import com.example.weatherappproject.data.MainDb

// инициализация бд при запуске приложения
class App: Application() {
    // by lazy - если бд не создана, то будет создаваться; если создана, то просто выдаст созданную бд
    val database by lazy { MainDb.createDataBase(this) }
}