package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.provider

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity

class ImageProvider {
    companion object{
        fun getPictures(activity: FragmentActivity?): List<Bitmap> {
            val path = "Pictures"

            val selection = MediaStore.Files.FileColumns.RELATIVE_PATH + " like ? "

            val selectionargs = arrayOf("%$path%")
            val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val orderBy:String = MediaStore.Images.Media.DATE_TAKEN
            val projection : Array<String> = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )
            val cursor: Cursor? = activity?.contentResolver?.query(
                uri, projection, selection, selectionargs, orderBy
            )
            var images: MutableList<Bitmap> = mutableListOf<Bitmap>()

            if (cursor != null && cursor.moveToFirst()){
                val columnIdxData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                do {
                    val imageId:String = cursor.getString(columnIdxData)
                    val uriImage = Uri.withAppendedPath(uri, "" + imageId)

                    var image: Bitmap
                    activity?.contentResolver.openFileDescriptor(uriImage, "r").use { pfd ->
                        if( pfd != null ){
                            image = BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                            images.add(image)
                        }
                    }
                } while(cursor.moveToNext())
                cursor.close()
            } else {
                cursor?.close()
                return images
            }
            return images
        }

    }

}