package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Todo

@Dao
interface TodoDao {
    @Insert
    suspend fun addTodo(todo : Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo")
    suspend fun deleteAllTodos()

    @Query("SELECT * FROM todo ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<Todo>>
}