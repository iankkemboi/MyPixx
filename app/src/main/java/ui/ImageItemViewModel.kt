package ui

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import model.Hits

class ImageItemViewModel: BaseViewModel() {
    private val thumbnail = MutableLiveData<String>()
    private val username = MutableLiveData<String>()
    private val tag = MutableLiveData<String>()

    fun bind(image: Hits){
        thumbnail.value = image.previewURL
        username.value = image.user
        tag.value = image.tags
    }

    fun getThumbnail():MutableLiveData<String>{
        return thumbnail
    }

    fun getUsername():MutableLiveData<String>{
        return username
    }
    fun getTags():MutableLiveData<String>{
        return tag
    }
}