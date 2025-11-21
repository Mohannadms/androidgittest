import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact: Contact)

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<Contact>

    @Query("SELECT * FROM contacts WHERE category = :categoryInput")
    suspend fun getContactsByCategory(categoryInput: String): List<Contact>

    @Query("SELECT DISTINCT category FROM contacts")
    suspend fun getAllCategories(): List<String>
}
