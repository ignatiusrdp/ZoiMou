package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImagesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is images Fragment"
    }
    val text: LiveData<String> = _text
}