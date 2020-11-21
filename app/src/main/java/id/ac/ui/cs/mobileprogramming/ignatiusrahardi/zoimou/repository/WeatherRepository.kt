package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao.WeatherDao
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Weather

class WeatherRepository(private val weatherDao: WeatherDao) {

    val getWeatherData : LiveData<Weather> = weatherDao.getWeatherData(0)

    suspend fun deleteWeather() {
        weatherDao.deleteWeather()
    }

}