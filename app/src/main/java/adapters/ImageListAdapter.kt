package adapters

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ian.mypixxx.R
import com.ian.mypixxx.databinding.ItemImageBinding
import model.Hits
import model.RecyclerViewCallback
import model.UpdateRecycleView
import ui.ImageItemViewModel


class ImageListAdapter( private  var imgList:List<Hits>,val ct: Context): RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    var itemClick: ((Hits) -> Unit)? = null
    var recyclerViewCallback: RecyclerViewCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.ViewHolder {
        val binding: ItemImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image, parent, false)

        return ViewHolder(binding).apply {
            itemView.setOnClickListener {

              this@ImageListAdapter.recyclerViewCallback?.onRecycleViewItemClick(imgList[adapterPosition], adapterPosition)
            }
        }

    }

    override fun onBindViewHolder(holder: ImageListAdapter.ViewHolder, position: Int) {
        holder.bind(imgList[position])




    }

    fun setOnCallbackListener(recyclerViewCallback: RecyclerViewCallback) {
        this.recyclerViewCallback = recyclerViewCallback
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    /* updatePostList(imggList:List<Hits>){
        this.imgList = imggList

        notifyDataSetChanged()
    }*/


    class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = ImageItemViewModel()


        fun bind(imgpojo:Hits){
            viewModel.bind(imgpojo)
            binding.viewModel = viewModel



        }


    }

}