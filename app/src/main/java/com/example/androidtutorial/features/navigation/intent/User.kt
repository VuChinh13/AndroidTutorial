package com.example.androidtutorial.features.navigation.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String
) : Parcelable
