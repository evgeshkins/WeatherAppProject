package com.example.weatherappproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    onClick: (String) -> Unit,
    baseViewModel: BaseViewModel = viewModel(factory = BaseViewModel.factory)
) {
    val citiesList = baseViewModel.citiesList.collectAsState(initial = emptyList())
    val citiesListAsList = citiesList.value.toList()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "Погода.now",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Выберите город:",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = baseViewModel.cityInput.value,
                        onValueChange = { baseViewModel.cityInput.value = it },
                        label = { Text(text = "Добавить город...") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White
                        )
                    )
                    IconButton(onClick = { baseViewModel.insertCity() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add city to db"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        val navController = rememberNavController()
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(citiesListAsList) { item ->
                ListItem(
                    item,
                    {
                        baseViewModel.cityEntity = it
                        baseViewModel.cityInput.value = it.cityName
                    },
                    {
                        baseViewModel.deleteCity(it)
                    },
                    onClick
                )
            }
        }
    }
}