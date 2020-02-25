package daggerinjections.vmfactory

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import model.db.AppDatabase
import ui.DetailsViewModel

import ui.ImagesListViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImagesListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "hits").build()
            @Suppress("UNCHECKED_CAST")
            return ImagesListViewModel(db.imgdao()) as T
        }
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}