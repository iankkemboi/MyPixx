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


class DetailsViewModel: BaseViewModel() {
    private val fullimage = MutableLiveData<String>()
    private val username = MutableLiveData<String>()
    private val userurl = MutableLiveData<String>()
    private val tag = MutableLiveData<String>()

    private val likes = MutableLiveData<String>()
    private val favorites = MutableLiveData<String>()
    private val comments = MutableLiveData<String>()

    fun bind(image: Hits){
        fullimage.value = image.largeImageURL
        username.value = "@"+image.user
        userurl.value = image.userImageURL
        tag.value = image.tags

        likes.value = image.likes+" Likes"
        favorites.value = image.favorites+" Favorites"
        comments.value = image.comments+" Comments"
    }
    companion object {


        @BindingAdapter("fullimageUrl")
        @JvmStatic
        fun loadfullimage(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView.context).load(imageUrl)
                .apply(RequestOptions.centerCropTransform())

                .into(imageView)
            //Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
        @BindingAdapter("detailsitems")
        @JvmStatic
        fun setDetailItems(view: ChipGroup, tags: String?) {
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
                chip.setTextColor(context.resources.getColor(R.color.white))
                chip.setChipBackgroundColorResource(R.color.holo_blue_light)

                chip.height = 100
                view.addView(chip)
            }
        }
    }
    fun getFullImageURl():MutableLiveData<String>{
        return fullimage
    }

    fun getUsername():MutableLiveData<String>{
        return username
    }
    fun getTags():MutableLiveData<String>{
        return tag
    }
    fun getUserurl():MutableLiveData<String>{
        return userurl
    }


    fun getComments():MutableLiveData<String>{
        return comments
    }
    fun getLikes():MutableLiveData<String>{
        return likes
    }
    fun getFav():MutableLiveData<String>{
        return favorites
    }
}