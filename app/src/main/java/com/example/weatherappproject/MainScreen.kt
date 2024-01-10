package com.example.weatherappproject

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import coil.compose.rememberImagePainter
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onClick: (String) -> Unit, cityName: String) {
    var cityNameValue by remember { mutableStateOf(cityName) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Surface(
        color = colorResource(id = R.color.color_primary),
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLandscape) {
            // сохранение состояния текста результата
            var realTemp = remember { mutableStateOf("") }
            var cloudState = remember { mutableStateOf("") }
            var likeTemp = remember { mutableStateOf("") }
            var imageCode = remember { mutableStateOf("") }
            var windSpeed = remember { mutableStateOf("") }

            val key: String = "67c959ae5a4cb9cbc3fad3c8fe6f5d37"
            val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$cityNameValue&appid=$key&lang=ru&units=metric"
            LaunchedEffect(Unit) {
                val executor = GetURLData(realTemp, cloudState, likeTemp, imageCode, windSpeed)
                executor.execute(url)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = cityNameValue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = colorResource(id = R.color.text_color),
                        )

                        Text(
                            text = cloudState.value,
                            color = colorResource(id = R.color.text_color),
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    val iconsMap = mapOf<String, Int>(
                        // код иконки в openweathermap api to наше название иконки с высоким разрешением
                        "01d" to R.drawable.one_d, "01n" to R.drawable.one_n,
                        "02d" to R.drawable.two_d, "02n" to R.drawable.two_n,
                        "03d" to R.drawable.three_d, "03n" to R.drawable.three_n,
                        "04d" to R.drawable.four_d, "04n" to R.drawable.four_n,
                        "09d" to R.drawable.nine_d, "09n" to R.drawable.nine_n,
                        "10d" to R.drawable.ten_d, "10n" to R.drawable.ten_n,
                        "11d" to R.drawable.eleven_d, "11n" to R.drawable.eleven_n,
                        "13d" to R.drawable.thirteen_d, "13n" to R.drawable.thirteen_n,
                        "50d" to R.drawable.fifty_d, "50n" to R.drawable.fifty_n
                    )
                    val resourceId = iconsMap[imageCode.value]
                    if (resourceId != null) {
                        Image(
                            painter = painterResource(resourceId),
                            contentDescription = "Weather Icon",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(top = 10.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = realTemp.value,
                        color = colorResource(id = R.color.text_color),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .padding(top = 20.dp)
                    )
                    {
                        Text(
                            text = likeTemp.value,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            color = Color.Black,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = windSpeed.value,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(bottom = 10.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            color = Color.Black,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = cityNameValue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )

                // сохранение состояния текста результата
                var realTemp = remember { mutableStateOf("") }
                var cloudState = remember { mutableStateOf("") }
                var likeTemp = remember { mutableStateOf("") }
                var imageCode = remember { mutableStateOf("") }
                var windSpeed = remember { mutableStateOf("") }

                val key: String = "67c959ae5a4cb9cbc3fad3c8fe6f5d37"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$cityNameValue&appid=$key&lang=ru&units=metric"
                LaunchedEffect(Unit) {
                    val executor = GetURLData(realTemp, cloudState, likeTemp, imageCode, windSpeed)
                    executor.execute(url)
                }

                Text(
                    text = realTemp.value,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.text_color),
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center
                )

                // словарь сопоставления иконок погодных явлений
                val iconsMap = mapOf<String, Int>(
                    // код иконки в openweathermap api to наше название иконки с высоким разрешением
                    "01d" to R.drawable.one_d, "01n" to R.drawable.one_n,
                    "02d" to R.drawable.two_d, "02n" to R.drawable.two_n,
                    "03d" to R.drawable.three_d, "03n" to R.drawable.three_n,
                    "04d" to R.drawable.four_d, "04n" to R.drawable.four_n,
                    "09d" to R.drawable.nine_d, "09n" to R.drawable.nine_n,
                    "10d" to R.drawable.ten_d, "10n" to R.drawable.ten_n,
                    "11d" to R.drawable.eleven_d, "11n" to R.drawable.eleven_n,
                    "13d" to R.drawable.thirteen_d, "13n" to R.drawable.thirteen_n,
                    "50d" to R.drawable.fifty_d, "50n" to R.drawable.fifty_n
                )
                val resourceId = iconsMap[imageCode.value]
                if (resourceId != null)
                {
                    Image(
                        painter = painterResource(resourceId),
                        //painter = rememberImagePainter("https://openweathermap.org/img/wn/10d@2x.png"),
                        contentDescription = "Weather Icon",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(top = 10.dp)
                    )
                }

                Text(
                    text = cloudState.value,
                    modifier = Modifier
                        .padding(start = 1.dp)
                        .padding(top = 10.dp),
                    color = colorResource(id = R.color.text_color),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )

                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .padding(top = 40.dp)
                )
                {
                    Text(
                        text = likeTemp.value,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = windSpeed.value,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

    }
}
