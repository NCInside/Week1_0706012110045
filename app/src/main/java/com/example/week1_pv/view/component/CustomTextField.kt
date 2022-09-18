package com.example.week1_pv.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(lbl: String, plc: String, isNum: Boolean): TextFieldValue {

    var text by remember { mutableStateOf(TextFieldValue(plc)) }
    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        label = { Text(text = lbl, color = Color.White) },
        placeholder = { Text(text = plc, color = Color.White) },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        keyboardOptions = if (isNum) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.DarkGray),
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
    return text
}