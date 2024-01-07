package com.example.weatherappproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(value = "",
                onValueChange = {},
                label = {Text(text = "Введите город...")},
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ))
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add city to db")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        // ленивая колонка - рендерит не все карточки сразу, а только те, которые видны пользователю на данный момент
        LazyColumn(modifier = Modifier.fillMaxWidth())
        {
            items(count = 15){
                ListItem()
            }
        }
    }
}