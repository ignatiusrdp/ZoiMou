package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_edit_notes.*

class EditNotesActivity : AppCompatActivity() {

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_edit_notes)

        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("Title")
        val content = intent.getStringExtra("Content")

        editTitle.setText(title)
        editContent.setText(content)

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        submitEditButton.setOnClickListener {
            updateData(id)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === android.R.id.home){
            val intent = intent
            val title = intent.getStringExtra("Title")
            val content = intent.getStringExtra("Content")

            val backActionBarIntent = Intent(this, NoteItemsActivity::class.java)
            backActionBarIntent.putExtra("Title", title)
            backActionBarIntent.putExtra("Content", content)
            startActivity(backActionBarIntent)
        }
        return true
    }

    private fun updateData(id : Int){
        val title = editTitle.text.toString()
        val content = editContent.text.toString()

        if(inputCheck(title,content)){
            val updateNote = Notes(id,title,content)

            mNotesViewModel.updateNotes(updateNote)
            Toast.makeText(this, R.string.note_edit_success, Toast.LENGTH_LONG).show()
            val backToNoteIntent = Intent(this, NoteItemsActivity::class.java)
            backToNoteIntent.putExtra("Title", title)
            backToNoteIntent.putExtra("Content", content)
            startActivity(backToNoteIntent)

        } else {
            Toast.makeText(this, R.string.note_warn, Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(title: String, content : String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }
}