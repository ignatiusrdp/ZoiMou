package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import kotlinx.android.synthetic.main.gallery_item.view.*

class ImageAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var list = emptyList<Uri>()

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        val image = itemView.image_view
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val currentImage  = list[position]
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
        val currentImage = list[position]
        holder.image.setImageURI(currentImage)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setData(images: List<Uri>){
        this.list = images
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun onItemClick(currentImage: Uri)
    }

}