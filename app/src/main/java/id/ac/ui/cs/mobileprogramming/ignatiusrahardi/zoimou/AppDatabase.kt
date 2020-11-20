package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao.NotesDao
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.dao.WeatherDao
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Notes
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Todo
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Weather

@Database(entities = [Notes::class, Weather::class, Todo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {


    abstract fun notesDao(): NotesDao

    abstract fun weatherDao(): WeatherDao

    abstract fun todoDao(): TodoDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val DB_NAME: String = "zoimou_db"

        fun getDatabase(context:Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}