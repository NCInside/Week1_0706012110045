package com.example.week1_pv.model

import android.net.Uri

data class Hewan(
    val id: String,
    var nama: String,
    var jenis: String,
    var usia: Int,
    var uri: Uri?
)