package com.smtersoyoglu.shuffleandlearn
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    val id: Int,
    val translation: String,
    val english: String,
    val imageResId: Int,
    var isLearned: Boolean = false,
    val sentence: String
) : Parcelable
