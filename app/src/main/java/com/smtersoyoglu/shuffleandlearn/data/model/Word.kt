package com.smtersoyoglu.shuffleandlearn.data.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    val id: Int,
    val translation: String,
    val english: String,
    val imageUrl: String,
    //val imageResId: Int,
    var isLearned: Boolean = false,
    val sentence: String
) : Parcelable
