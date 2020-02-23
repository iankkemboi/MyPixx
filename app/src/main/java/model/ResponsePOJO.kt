package model

import androidx.room.Entity


data class ResponsePOJO(val total : Int,
                           val totalHits : Int,
                           val hits : List<Hits>)