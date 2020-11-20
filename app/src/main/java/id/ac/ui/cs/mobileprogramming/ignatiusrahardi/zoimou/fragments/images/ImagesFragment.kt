package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.images

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.adapters.ImageAdapter
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.provider.ImageProvider
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.ImagesViewModel
import kotlinx.android.synthetic.main.fragment_images.*
import java.io.File

class ImagesFragment : Fragment() , ImageAdapter.OnItemClickListener{

    private lateinit var image_uri: Uri
    private lateinit var imagesViewModel: ImagesViewModel
    private var images = emptyList<Bitmap>()
    private val REQUEST_CODE_READ_EXT = 99
    private val REQUEST_CODE_CAMERA = 100

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
        if (checkPermissionGallery()){
           setGallery()
        } else {
            buttonCamera.isEnabled = false
        }

        buttonCamera.setOnClickListener { view ->

            if(activity?.let { checkCameraHardware(it) }!!){
                openCamera()
            } else {
                Toast.makeText(activity, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun openCamera(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "from zoimou app")
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        image_uri =
            activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
    }


    fun setGallery(){
        val adapter = ImageAdapter(this)
        images = ImageProvider.getPictures(activity)

        adapter.setData(images)
        image_list.setHasFixedSize(true)
        image_list.layoutManager = GridLayoutManager(requireContext(), 4)

        image_list.adapter = adapter
    }
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
    private fun checkPermissionGallery():Boolean{
        if(checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED||
            checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }


    override fun onItemClick(currentImage: Bitmap) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK){
            setGallery()
            Toast.makeText(activity, R.string.camera_captured, Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(activity,  R.string.camera_cancelled, Toast.LENGTH_LONG).show()

        }
        super.onActivityResult(requestCode, resultCode, data)

    }

}