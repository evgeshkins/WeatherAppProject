package com.example.weatherappproject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.weatherappproject.data.CityEntity

@Composable
fun ListItem(item: CityEntity, onClickInsert: (CityEntity) -> Unit, onClickDelete: (CityEntity) -> Unit, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onClick(item.cityName)
            }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = item.cityName, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp), fontWeight = FontWeight.Bold)
            IconButton(onClick = { onClickInsert(item) }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Delete city from db")
            }
            IconButton(onClick = { onClickDelete(item) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete city from db")
            }
        }
    }
}