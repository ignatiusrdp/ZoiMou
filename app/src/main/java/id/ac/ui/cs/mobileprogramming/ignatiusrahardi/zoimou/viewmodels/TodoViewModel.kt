package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.AppDatabase
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Todo
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    val getAllTodos : LiveData<List<Todo>>
    private val repository : TodoRepository

    init {
        val todoDao = AppDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        getAllTodos = repository.getAllTodos
    }

    fun addTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }
    fun deleteTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }
    fun deleteAllTodos(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodos()
        }
    }

}