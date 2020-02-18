package model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import model.Hits

@Database(entities = [Hits::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imgdao(): ImgDao
}