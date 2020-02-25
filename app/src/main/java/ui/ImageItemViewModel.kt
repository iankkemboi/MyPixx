package ui

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import model.Hits


class ImageItemViewModel: BaseViewModel() {
    private val thumbnail = MutableLiveData<String>()
    private val username = MutableLiveData<String>()
    private val tags = MutableLiveData<String>()

    fun bind(image: Hits){
        thumbnail.value = image.previewURL
        username.value = "@"+image.user
        tags.value = image.tags
    }
    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadimage(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView.context).load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
            //Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
        }



    }
    fun getThumbnail():MutableLiveData<String>{
        return thumbnail
    }

    fun getUsername():MutableLiveData<String>{
        return username
    }
    fun getTags():MutableLiveData<String>{
        return tags
    }
}