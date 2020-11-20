package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.notes

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.notes.CreateNotesActivity
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.notes.NoteItemsActivity
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters.NotesAdapter
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment(), NotesAdapter.OnItemClickListener {

    private lateinit var notesViewModel: NotesViewModel
    //dummy
//    private val dummyList = generateDummyList(20)
//    private val adapter = NotesAdapter(dummyList, this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notes, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recycleview
        val adapter = NotesAdapter(this)

        notes.adapter = adapter
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty(){
                empty_view_notes.visibility = (if (adapter.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })

        notes.layoutManager = LinearLayoutManager(requireContext())
        notes.setHasFixedSize(true)

        //viewmodel
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })

        setHasOptionsMenu(true)
        fabCreateNotes.setOnClickListener { view ->
            openCreateNotesActivity()
        }
    }

    fun openCreateNotesActivity() {
        val intent = Intent(context, CreateNotesActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(note : Notes) {
        val intent = Intent(context, NoteItemsActivity::class.java)
        intent.putExtra("id", note.id)
        intent.putExtra("Title", note.title)
        intent.putExtra("Content", note.content)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteAllNotes(){
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setPositiveButton(R.string.yes){_,_ ->
            notesViewModel.deleteAllNotes()
            Toast.makeText(activity, R.string.note_delete_success, Toast.LENGTH_SHORT).show()

        }
        alertDialog.setNegativeButton(R.string.no){_,_ ->

        }
        alertDialog.setTitle(R.string.title_delete_note)
        alertDialog.setMessage(R.string.message_delete_all_notes)
        alertDialog.create().show()
    }


}