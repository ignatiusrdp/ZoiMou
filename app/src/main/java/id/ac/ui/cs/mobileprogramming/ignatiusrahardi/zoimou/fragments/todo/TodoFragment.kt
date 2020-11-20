package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.todo

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters.TodoAdapter
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Todo
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.todo_text_layout.view.*

class TodoFragment : Fragment(), TodoAdapter.OnItemClickListener {

    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_todo, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecycleView
        val adapter = TodoAdapter(this,context)

        todos.adapter = adapter
        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                checkEmpty()
            }
            fun checkEmpty(){
                empty_view_todo.visibility = (if(adapter.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
        todos.layoutManager = LinearLayoutManager(requireContext())
        todos.setHasFixedSize(true)

        //view model
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.getAllTodos.observe( viewLifecycleOwner, Observer { todo ->
            adapter.setData(todo)
        })

        setHasOptionsMenu(true)
        button_create_todo.setOnClickListener { view ->
            insertData()
        }
    }

    override fun onItemClick(currentTodo: Todo) {
        val id : Int = currentTodo.id
        val task : String = currentTodo.task
        val dueTime : String = currentTodo.duetime
        updateData(id, task, dueTime)
    }

    override fun onDeleteClick(currentTodo: Todo) {
        val id : Int = currentTodo.id
        val task : String = currentTodo.task
        val dueTime : String = currentTodo.duetime
        deleteData(id,task, dueTime)

    }

    override fun onChecked(currentTodo: Todo) {
        val id : Int = currentTodo.id
        val task : String = currentTodo.task
        val dueTime : String = currentTodo.duetime
        checkedData(id,task,dueTime)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllTodos()
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteAllTodos(){
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setPositiveButton(R.string.yes){_,_ ->
            todoViewModel.deleteAllTodos()
            Toast.makeText(activity, R.string.success_delete_todo, Toast.LENGTH_SHORT).show()

        }
        alertDialog.setNegativeButton(R.string.no){_,_ ->

        }
        alertDialog.setTitle(R.string.title_delete_todo)
        alertDialog.setMessage(R.string.message_delete_all_todo)
        alertDialog.create().show()
    }

    fun insertData(){
        val insertDialog = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout:View = inflater.inflate(R.layout.todo_text_layout, null)
        val taskInput : EditText = dialogLayout.create_task
        val dueTimeInput: EditText = dialogLayout.create_time
        insertDialog.setTitle(R.string.title_add_todo)
            .setPositiveButton(R.string.button_add_todo){dialog, which ->
                val task: String = taskInput.text.toString()
                val dueTime :String = dueTimeInput.text.toString()
                if(inputCheck(task,dueTime)){
                    val todo = Todo(0,task,dueTime)
                    todoViewModel.addTodo(todo)
                } else {
                    Toast.makeText(activity, R.string.warn_add_todo, Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton(R.string.cancel){_,_ ->

            }
            .setView(dialogLayout)
            .show()
    }

    fun updateData(id : Int, task : String, time : String){
        val insertDialog = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout:View = inflater.inflate(R.layout.todo_text_layout, null)
        val taskInput : EditText = dialogLayout.create_task
        val dueTimeInput: EditText = dialogLayout.create_time
        taskInput.setText(task)
        dueTimeInput.setText(time)
        insertDialog.setTitle(R.string.title_edit_todo)
            .setPositiveButton(R.string.button_edit_todo){dialog, which ->
                val task: String = taskInput.text.toString()
                val dueTime :String = dueTimeInput.text.toString()
                if(inputCheck(task,dueTime)){
                    val todo = Todo(id,task,dueTime)
                    todoViewModel.updateTodo(todo)
                } else {
                    Toast.makeText(activity, R.string.warn_edit_todo, Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton(R.string.cancel){_,_ ->

            }
            .setView(dialogLayout)
            .show()
    }
    fun deleteData(id: Int, task: String, dueTime : String){
        val deleteDialog = AlertDialog.Builder(activity)
        deleteDialog.setPositiveButton(R.string.yes){ _, _ ->
            val deleteTodo = Todo(id,task,dueTime)
            todoViewModel.deleteTodo(deleteTodo)
            Toast.makeText(activity, R.string.success_delete_todo, Toast.LENGTH_SHORT).show()

        }
        deleteDialog.setNegativeButton(R.string.no){ _, _ ->

        }
        deleteDialog.setTitle(R.string.title_delete_todo)
        deleteDialog.setMessage(R.string.message_delete_todo)
        deleteDialog.create().show()

    }
    fun checkedData(id: Int, task: String, dueTime: String){
        val checkedDialog = AlertDialog.Builder(activity)
        checkedDialog.setPositiveButton(R.string.yes){ _, _ ->
            val deleteTodo = Todo(id,task,dueTime)
            todoViewModel.deleteTodo(deleteTodo)
            Toast.makeText(activity, R.string.todo_completed, Toast.LENGTH_SHORT).show()

        }
        checkedDialog.setNegativeButton(R.string.no){ _, _ ->

        }
        checkedDialog.setTitle(R.string.title_todo_confirmation)
        checkedDialog.setMessage(R.string.message_done_todo)
        checkedDialog.create().show()
    }
    private fun inputCheck(title: String, content : String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }




}