package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather")
data class Weather (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val weather: String,
    val weatherDescription : String,
    val temp: String,
    val feelsLike: String,
    val time: String,
    val place:String,
    val iconUrl:String

)