package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.images.ImagesFragment
import kotlinx.android.synthetic.main.gallery_item.view.*

class ImageAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var list = emptyList<Bitmap>()

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        val image = itemView.image_view
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val currentImage : Bitmap = list[position]
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(currentImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val image : View = LayoutInflater.from(parent.context).inflate(
            R.layout.gallery_item,
            parent, false)
        return ImageViewHolder(image)
    }

    override fun onBindViewHolder( holder: ImageViewHolder, position: Int) {
        val currentImage : Bitmap = list[position]
        holder.image.setImageBitmap(currentImage)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setData(images: List<Bitmap>){
        this.list = images
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun onItemClick(currentImage: Bitmap)
    }

}