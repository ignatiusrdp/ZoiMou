package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Todo
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.todo.TodoFragment
import kotlinx.android.synthetic.main.todos_cardview.view.*

class TodoAdapter(
    private val listener: TodoFragment, private val context : Context?
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var list = emptyList<Todo>()

    inner class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.checkboxTodo
        val dueTime: TextView = itemView.todo_time
        val delete: ImageView = itemView.delete_todo

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val currentTodo: Todo = list[position]
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(currentTodo)
                }
            }
            delete.setOnClickListener {
                val position: Int = adapterPosition
                val currentTodo: Todo = list[position]
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(currentTodo)
                }
            }
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val position: Int = adapterPosition
                    val currentTodo: Todo = list[position]
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onChecked(currentTodo)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val card : View = LayoutInflater.from(parent.context).inflate(
            R.layout.todos_cardview,
            parent, false)
        return TodoViewHolder(card)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo : Todo = list[position]
        holder.checkBox.text = currentTodo.task
        holder.dueTime.text = currentTodo.duetime
    }

    override fun getItemCount(): Int = list.size

    fun setData(todo: List<Todo>){
        this.list = todo
        notifyDataSetChanged()
    }

    interface OnItemClickListener{

        fun onItemClick(currentTodo: Todo)
        fun onDeleteClick(currentTodo: Todo)
        fun onChecked(currentTodo: Todo)
    }


}