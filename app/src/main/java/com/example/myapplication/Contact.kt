import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val name: String,
    val phone: String,
    val category: String,
)
