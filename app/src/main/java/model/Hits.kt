package model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Hits (
    @field:PrimaryKey
    val id : Int,
    val pageURL : String,
    val type : String,
    val tags : String,
    val previewURL : String,
    val previewWidth : Int,
    val previewHeight : Int,
    val webformatURL : String,
    val webformatWidth : Int,
    val webformatHeight : Int,
    val largeImageURL : String,
    val imageWidth : Int,
    val imageHeight : Int,
    val imageSize : Int,
    val views : Int,
    val downloads : Int,
    val favorites : String,
    val likes : String,
    val comments : String,
    val user_id : Int,
    val user : String,
    val userImageURL : String
): Parcelable