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
    private val tag = MutableLiveData<String>()

    fun bind(image: Hits){
        thumbnail.value = image.previewURL
        username.value = "@"+image.user
        tag.value = image.tags
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

        @BindingAdapter("items")
        @JvmStatic
        fun setItems(view: ChipGroup, tags: String?) {
            if (tags == null // Since we are using liveData to observe data, any changes in that table(favorites)
// will trigger the observer and hence rebinding data, which can lead to duplicates.
                || view.childCount > 0
            ) return
            // dynamically create & add genre chips
            val lsttags = tags.split(",").toTypedArray()
            val context: Context = view.context
            for (lsttag in lsttags) {
                val chip = Chip(context)
                chip.text = lsttag

                chip.chipStrokeColor = ColorStateList.valueOf(
                    context.resources.getColor(R.color.holo_green_dark)
                )
                chip.setChipBackgroundColorResource(R.color.holo_blue_light)
                view.addView(chip)
            }
        }
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