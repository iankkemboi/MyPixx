package ui

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
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
    private val listtags = MutableLiveData<List<String>>()

    fun bind(image: Hits){
        thumbnail.value = image.previewURL
        username.value = "@"+image.user
        tags.value = image.tags
        listtags.value = image.tags.split(",")
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
        fun setItems(view: ChipGroup,lsttags: List<String> ) {

            if (lsttags == null
                // Since we are using liveData to observe data, any changes in that table(favorites)
                // will trigger the observer and hence rebinding data, which can lead to duplicates.
                || view.getChildCount() > 0)
                return;

            val context: Context = view.context
            for (lsttag in lsttags) {
                val chip = Chip(context)
                chip.text = lsttag

                chip.chipStrokeColor = ColorStateList.valueOf(
                    context.resources.getColor(R.color.holo_green_dark)
                )
                chip.setTextColor(context.resources.getColor(R.color.white))
                chip.setChipBackgroundColorResource(R.color.holo_blue_light)


                view.addView(chip)
            }
            Log.v("size",lsttags.size.toString())
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
    fun getListTags():MutableLiveData<List<String>>{
        return listtags
    }
}