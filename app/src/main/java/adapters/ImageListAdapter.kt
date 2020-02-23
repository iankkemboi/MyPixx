package adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ian.mypixxx.R
import com.ian.mypixxx.databinding.ItemImageBinding
import model.Hits
import ui.ImageItemViewModel


class ImageListAdapter: RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {
    private lateinit var imgList:List<Hits>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.ViewHolder {
        val binding: ItemImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageListAdapter.ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    override fun getItemCount(): Int {
        return if(::imgList.isInitialized) imgList.size else 0
    }

    fun updatePostList(imggList:List<Hits>){
        this.imgList = imggList
        Log.v("imglist",imggList.get(0).user)
        notifyDataSetChanged()
    }

    fun checkroomdb(){
    }

    class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = ImageItemViewModel()
        fun bind(imgpojo:Hits){
            viewModel.bind(imgpojo)
            binding.viewModel = viewModel
        }
    }
}