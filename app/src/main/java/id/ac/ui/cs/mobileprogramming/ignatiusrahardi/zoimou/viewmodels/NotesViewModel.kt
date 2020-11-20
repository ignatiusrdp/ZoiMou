package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels

import android.app.Application
import androidx.lifecycle.*
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.AppDatabase
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val getAllNotes : LiveData<List<Notes>>
    private val repository : NotesRepository

    init {
        val notesDao = AppDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
        getAllNotes = repository.getAllNotes
    }

    fun addNotes(notes: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNotes(notes)
        }
    }

    fun updateNotes(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)
        }
    }
    fun deleteNotes(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(notes)
        }
    }
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletelAllNotes()
        }
    }

}