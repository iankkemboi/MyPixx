package model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import model.Hits

@Dao
interface ImgDao {
    @get:Query("SELECT * FROM hits")
    val all: List<Hits>

    @get:Query("SELECT COUNT(*) from hits")
    val hitscount : Int?

    @Insert
    fun insertAll(vararg hits: Hits)
}