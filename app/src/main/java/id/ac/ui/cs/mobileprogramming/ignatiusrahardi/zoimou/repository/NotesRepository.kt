package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao.NotesDao
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes: LiveData<List<Notes>> = notesDao.getAllNotes()

    suspend fun addNotes(notes: Notes){
        notesDao.addNotes(notes)
    }

    suspend fun updateNotes(notes: Notes){
        notesDao.updateNotes(notes)
    }

    suspend fun deleteNotes(notes: Notes){
        notesDao.deleteNotes(notes)
    }

    suspend fun deletelAllNotes(){
        notesDao.deleteAllNotes()
    }

}