package com.example.week1_pv.view.component

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.week1_pv.model.Hewan
import com.example.week1_pv.R
import com.example.week1_pv.navigation.Screen
import com.example.week1_pv.db.HewanList


@Composable
fun CustomCard(
    hewan: Hewan,
    navController: NavController
) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf(hewan.uri)
    }
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val mContext = LocalContext.current

    androidx.compose.material.Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.DarkGray,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(2.dp,Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {

            if (imageUri == null) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Image Hewan",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(112.dp)
                        .height(144.dp)
                        .clip((RoundedCornerShape(corner = CornerSize(8.dp))))
                )
            }
            else {
                imageUri?.let {

                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(mContext.contentResolver,it)

                    } else {
                        val source = ImageDecoder
                            .createSource(mContext.contentResolver,it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let {  btm ->
                        Image(bitmap = btm.asImageBitmap(),
                            contentDescription = "Image Hewan",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(112.dp)
                                .height(144.dp)
                                .clip((RoundedCornerShape(corner = CornerSize(8.dp))))
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                Text(
                    text = hewan.nama,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = hewan.jenis,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                )
                Text(
                    text = "Usia: " + hewan.usia + " tahun",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            navController.navigate(route = Screen.FormScreen.passTitlenId("Edit", hewan.id))
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1f5557)),
                        shape = CutCornerShape(10),
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier.width(70.dp)
                    ) {
                        Text(
                            text = "EDIT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { showDeleteDialog = !showDeleteDialog },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFb43f3f)),
                        shape = CutCornerShape(10),
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier.width(90.dp)
                    ) {
                        Text(
                            text = "DELETE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        DeleteAlertDialog(
            onDismiss = { showDeleteDialog = !showDeleteDialog },
            onExit = {
                HewanList.hList.remove(hewan)
                showDeleteDialog = !showDeleteDialog
            }
        )
    }
}