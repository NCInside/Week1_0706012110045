package com.example.week1_pv.view

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.week1_pv.db.HewanList
import com.example.week1_pv.model.Hewan
import com.example.week1_pv.ui.theme.Week1_pvTheme
import com.example.week1_pv.view.component.CustomTextField
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FormScreen(
    navController: NavController,
    title: String,
    id: String
) {

    val mContext = LocalContext.current
    var hewan = Hewan("", "", "", -1, null)
    var isEmpty = (title == "Tambah")
    if (!isEmpty) hewan = HewanList.hList.find { it.id == id }!!
    val galleryPermissionState = rememberPermissionState(permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)
    var imageUri by remember {
        mutableStateOf(hewan.uri)
    }
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Week1_pvTheme{
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                                  navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    ) {
                        Icon(Icons.Filled.ArrowBack,"", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    Text(
                        text = "$title Hewan",
                        fontSize = 32.sp,
                        color = Color.White
                    )
                }
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .statusBarsPadding()
                            .navigationBarsWithImePadding()
                            .verticalScroll(rememberScrollState())
                    ) {

                        when (galleryPermissionState.status) {
                            PermissionStatus.Granted -> {
                                Button(
                                    onClick = { launcher.launch("image/*") },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1f5557))
                                ) {
                                    Text(text = "Pick image", color = Color.White)
                                }
                            }
                            is PermissionStatus.Denied -> {
                                Column {
                                    Button(
                                        onClick = { galleryPermissionState.launchPermissionRequest() },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1f5557))
                                    ) {
                                        Text(text = "Permission", color = Color.White)
                                    }
                                }
                            }
                        }

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
                                    contentDescription =null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .padding(top = 36.dp)
                                )
                            }
                        }

                        var nama = CustomTextField("Nama Hewan", hewan.nama,false)
                        var jenis = CustomTextField("Jenis Hewan", hewan.jenis,false)
                        var usia = CustomTextField("Usia Hewan", if (hewan.usia < 0) "" else hewan.usia.toString(),true)

                        Button(
                            onClick = {
                                if (nama.text.isEmpty()) Toast.makeText(mContext, "Please input a value for Nama Hewan", Toast.LENGTH_SHORT).show()
                                else if (jenis.text.isEmpty()) Toast.makeText(mContext, "Please input a value for Jenis Hewan", Toast.LENGTH_SHORT).show()
                                else if (usia.text.isEmpty()) Toast.makeText(mContext, "Please input a value for Usia Hewan", Toast.LENGTH_SHORT).show()
                                else if (usia.text.toDoubleOrNull() == null) Toast.makeText(mContext, "Please input a valid value for Usia Hewan", Toast.LENGTH_SHORT).show()
                                else {
                                    if (isEmpty) HewanList.hList.add(Hewan(UUID.randomUUID().toString(), nama.text, jenis.text, usia.text.toInt(), imageUri))
                                    else {
                                        hewan.usia = usia.text.toInt()
                                        hewan.jenis = jenis.text
                                        hewan.nama = nama.text
                                        hewan.uri = imageUri
                                    }
                                    navController.popBackStack()
                                }
                            },
                            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1f5557)),
                            modifier = Modifier
                                .padding(top = 48.dp)
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text(
                                text = "SIMPAN",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
