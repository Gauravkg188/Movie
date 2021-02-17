package com.kg.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(

    val id:Int,
    val original_title: String,
    val original_language:String,
    val overview:String,
    val release_date:String,
    val vote_count:Int,
    val vote_average:Float,
    val adult:Boolean,
    val poster_path:String?,
    val backdrop_path:String,
    val title:String


): Parcelable