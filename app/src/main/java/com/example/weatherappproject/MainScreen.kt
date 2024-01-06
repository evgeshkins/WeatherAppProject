package com.example.weatherappproject

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Surface(
        color = colorResource(id = R.color.color_primary),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.logo),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = colorResource(id = R.color.text_color),
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            )

            // сохранение состояния поля для ввода
            var textValue by remember { mutableStateOf("") }

            OutlinedTextField(
                value = textValue,
                onValueChange = { newTextValue ->
                    textValue = newTextValue  // обновление переменной с текстовым значением при вводе
                },
                label = { Text(text = stringResource(id = R.string.hint_user_name)) },
                modifier = Modifier
                    .padding(top = 100.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.text_color),
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            // сохранение состояния текста результата
            var resultTextState by remember { mutableStateOf("test") }
            var mutableResult = remember { mutableStateOf(resultTextState) }

            Button(
                onClick = {
                    // если пользователь не ввел данные о городе
                    if (textValue.isEmpty()) {
                        mutableResult.value = "Введите город!"
                    } else {
                        val key: String = "67c959ae5a4cb9cbc3fad3c8fe6f5d37"
                        val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$textValue&appid=$key&lang=ru"

                        val executor = GetURLData(mutableResult)
                        executor.execute(url)
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.main_btn),
                    fontWeight = FontWeight.Bold
                )
            }


            Text(
                text = mutableResult.value,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                color = colorResource(id = R.color.text_color),
                fontSize = 25.sp
            )
        }
    }
}