package model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Hits

@Dao
interface ImgDao {
    @get:Query("SELECT * FROM hits")
    val all: List<Hits>

    @Insert
    fun insertAll(vararg hits: Hits)
}