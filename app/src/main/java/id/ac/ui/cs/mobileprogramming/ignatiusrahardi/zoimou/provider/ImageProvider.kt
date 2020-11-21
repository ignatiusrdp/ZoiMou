package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.provider

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity

class ImageProvider {
    companion object{
        fun getPictures(activity: FragmentActivity?): List<Uri> {
            val path = "Pictures"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
                var images: MutableList<Uri> = mutableListOf<Uri>()

                if (cursor != null && cursor.moveToFirst()){
                    val columnIdxData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                    do {
                        val imageId:String = cursor.getString(columnIdxData)
                        val uriImage = Uri.withAppendedPath(uri, "" + imageId)

                        images.add(uriImage)
                    } while(cursor.moveToNext())
                    cursor.close()
                } else {
                    cursor?.close()
                    return images
                }
                return images
            } else {
                val selection = MediaStore.MediaColumns.DATA + " like ? "

                val selectionargs = arrayOf("%$path%")
                val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val orderBy:String = MediaStore.Images.Media.DATE_TAKEN
                val projection : Array<String> = arrayOf(
                    MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                )
                val cursor: Cursor? = activity?.contentResolver?.query(
                    uri, projection, selection, selectionargs, orderBy
                )
                var images: MutableList<Uri> = mutableListOf<Uri>()

                if (cursor != null && cursor.moveToFirst()){
                    val columnIdxData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                    do {
                        val imageId:String = cursor.getString(columnIdxData)
                        images.add(Uri.parse(imageId))
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

}