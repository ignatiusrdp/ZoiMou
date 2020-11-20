package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels

import android.app.Application
import androidx.lifecycle.*
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.AppDatabase
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Weather
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : WeatherRepository
    val getWeatherData : LiveData<Weather>

    init {
        val weatherDao = AppDatabase.getDatabase(application).weatherDao()
        repository = WeatherRepository(weatherDao)
        getWeatherData = repository.getWeatherData
    }
    fun deleteWeather(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWeather()
        }
    }



}