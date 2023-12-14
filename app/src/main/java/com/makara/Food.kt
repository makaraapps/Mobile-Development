package com.makara

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String,
    val from: String,
    val description: String,
    val photo: Int,
): Parcelable