package model

import androidx.room.Entity


data class ResponsePOJO<T>(val total : Int,
                           val totalHits : Int,
                           val hits : List<Hits>)