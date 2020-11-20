package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import kotlinx.android.synthetic.main.notes_cardview.view.*

// still dummy data
class NotesAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    private var list = emptyList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val card = LayoutInflater.from(parent.context).inflate(
            R.layout.notes_cardview,
            parent, false)

        return NotesViewHolder(card)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = list[position]
        holder.title.text = currentNote.title



    }

    override fun getItemCount() = list.size


    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.note_title

        init {
           itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val currentNote:Notes = list[position]
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(currentNote)
            }
        }
    }

    fun setData(notes: List<Notes>){
        this.list = notes
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        //dummy need param
        fun onItemClick(currentNote: Notes)
    }
}