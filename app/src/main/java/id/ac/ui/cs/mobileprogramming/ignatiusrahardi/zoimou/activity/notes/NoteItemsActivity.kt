package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.MainActivity
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_note_items.*

class NoteItemsActivity : AppCompatActivity() {

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_items)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteContent.movementMethod = ScrollingMovementMethod()

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("Title")
        val content = intent.getStringExtra("Content")
        noteTitle.text = title
        noteContent.text = content

        editCreateButton.setOnClickListener { v ->
            val editIntent = Intent(this, EditNotesActivity::class.java)
            editIntent.putExtra("Title", noteTitle.text.toString())
            editIntent.putExtra("Content",noteContent.text.toString())
            editIntent.putExtra("id", id)
            startActivity(editIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(){
        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("Title").toString()
        val content = intent.getStringExtra("Content").toString()

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setPositiveButton(R.string.yes){_,_ ->
            val deleteNote = Notes(id,title,content)
            mNotesViewModel.deleteNotes(deleteNote)
            Toast.makeText(this, R.string.note_delete_success, Toast.LENGTH_SHORT).show()
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)

        }
        alertDialog.setNegativeButton(R.string.no){_,_ ->

        }
        alertDialog.setTitle(R.string.title_delete_note)
        alertDialog.setMessage(R.string.message_delete_note)
        alertDialog.create().show()
    }

}