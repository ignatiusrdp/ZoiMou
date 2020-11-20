package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Todo

class TodoRepository(private val todoDao: TodoDao) {

    val getAllTodos: LiveData<List<Todo>> = todoDao.getAllTodos()

    suspend fun addTodo(todo: Todo){
        todoDao.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

    suspend fun deleteAllTodos(){
        todoDao.deleteAllTodos()
    }

}