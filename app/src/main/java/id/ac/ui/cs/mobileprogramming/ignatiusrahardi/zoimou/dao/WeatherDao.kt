package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Weather

@Dao
interface WeatherDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weather: Weather)

    @Update
    suspend fun updateWeather(weather: Weather)

    @Query("DELETE FROM weather")
    suspend fun deleteWeather()

    @Query("SELECT EXISTS(SELECT * FROM weather WHERE id = :id)")
    suspend fun checkIfExist(id: Int) : Boolean

    @Query("SELECT * FROM weather WHERE id = :id")
    fun getWeatherData(id: Int): LiveData<Weather>

}