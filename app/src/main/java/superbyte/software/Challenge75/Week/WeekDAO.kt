package superbyte.software.Challenge75.Week
import androidx.room.*

@Dao
interface WeekDAO {
    @Query("SELECT * FROM week")
    fun getAll(): MutableList<Week>

    @Query("DELETE FROM week")
    fun deleteAll()

    @Insert
    fun insert(vararg week: Week)

    @Update
    fun update(week: Week)

    @Delete
    fun delete(week: Week)
}