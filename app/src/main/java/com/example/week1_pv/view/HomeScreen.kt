package com.example.week1_pv.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.week1_pv.navigation.Screen
import com.example.week1_pv.db.HewanList
import com.example.week1_pv.ui.theme.Week1_pvTheme
import com.example.week1_pv.view.component.CustomCard

@Composable
fun HomeScreen(
    navController: NavController
) {
    Week1_pvTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(route = Screen.FormScreen.passTitlenId("Tambah"))
                        },
                        backgroundColor = Color(0xFF1f5557),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(Icons.Filled.Add,"", tint = Color.White)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(1.dp)
                ) {
                    Text(
                        text = "Data Hewan",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val hewans = remember {HewanList.hList}
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items (hewans) {
                            CustomCard(
                                hewan = it,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}