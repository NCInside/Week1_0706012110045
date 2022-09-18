package com.example.week1_pv.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DeleteAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(300.dp, 260.dp),
            elevation = 8.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {

                Text(
                    text = "Deletion Confirmation",
                    modifier = Modifier.padding(16.dp), fontSize = 28.sp, fontWeight = FontWeight.Bold
                )

                Text(
                    text = "All deleted data cannot be recovered. Do you wish to proceed?",
                    modifier = Modifier.padding(16.dp), fontSize = 18.sp
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1f5557)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "CANCEL", color = Color.White)
                    }

                    Button(
                        onClick = { onExit() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFb43f3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "DELETE", color = Color.White)
                    }
                }

            }
        }
    }
}
