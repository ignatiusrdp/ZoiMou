package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.MainActivity
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_create_notes.*

class CreateNotesActivity : AppCompatActivity() {

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_add_notes)

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        submitCreateButton.setOnClickListener{
            insertData()
        }
    }
    private fun insertData(){
        val title = createTitle.text.toString()
        val content = createContent.text.toString()

        if(inputCheck(title,content)){
            val note = Notes(0, title,content)
            mNotesViewModel.addNotes(note)
            Toast.makeText(this, R.string.note_add_success, Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this, R.string.note_warn, Toast.LENGTH_LONG).show()
        }

    }
    private fun inputCheck(title: String, content : String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === android.R.id.home){

            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
        return true
    }
}