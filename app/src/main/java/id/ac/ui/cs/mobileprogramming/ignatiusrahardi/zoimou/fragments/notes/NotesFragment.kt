package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.CreateNotesActivity
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters.NotesAdapter
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notes, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // dummy
        val dummyList = generateDummyList(20)
        notes.adapter = NotesAdapter(dummyList)
        notes.layoutManager = LinearLayoutManager(context)
        notes.setHasFixedSize(true)
        fabCreate.setOnClickListener { view ->
            openCreateNotesActivity()
        }
    }

    fun openCreateNotesActivity() {
        val intent = Intent(context, CreateNotesActivity::class.java)
        startActivity(intent)
    }
    private fun generateDummyList(size: Int): List<Notes> {
        val list = ArrayList<Notes>()
        val drawable = R.drawable.ic_notes
        for (i in 0 until size) {
            val item = Notes(drawable, "Title $i", "Tap to see your note")
            list += item
        }
        return list
    }

}