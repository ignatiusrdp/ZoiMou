package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val task: String,
    val duetime: String
)