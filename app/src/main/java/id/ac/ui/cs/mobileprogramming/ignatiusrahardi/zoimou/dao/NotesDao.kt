package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes

@Dao
interface NotesDao {

    @Insert
    suspend fun addNotes(notes : Notes)

    @Update
    suspend fun updateNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Notes>>

}