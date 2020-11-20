package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.images

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.ImagesViewModel
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : Fragment() {

    private lateinit var imagesViewModel: ImagesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        imagesViewModel =
                ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_images, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTest.setOnClickListener {view ->
            getPictures()
        }
    }

    fun getPictures(){
        val uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val cursor = activity?.contentResolver?.query(
            uri,null,null,null,orderBy
        )
        if (cursor != null && cursor.moveToFirst()){
            do {
                val name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                Log.d("getImages", "name: $name")
            } while(cursor.moveToNext())
            cursor.close()
        } else {
            Log.d("fail", "null")
            cursor?.close()
        }
    }
}