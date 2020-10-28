package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import kotlinx.android.synthetic.main.notes_cardview.view.*

// still dummy data
class NotesAdapter(private val list: List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val card = LayoutInflater.from(parent.context).inflate(
            R.layout.notes_cardview,
            parent, false)

        return NotesViewHolder(card)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = list[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.title.text = currentItem.title
        holder.desc.text = currentItem.desc


    }

    override fun getItemCount() = list.size


    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.note_ico
        val title: TextView = itemView.note_title
        val desc: TextView = itemView.note_click
    }
}