package superbyte.software.Challenge75.Day

import androidx.room.*
import superbyte.software.Challenge75.Week.Week

@Dao
interface DayDAO {
    @Query("SELECT * FROM day")
    fun getAll(): MutableList<Day>

    @Query("SELECT * FROM day WHERE weekId = (:weekId)")
    fun getAll(weekId: String): MutableList<Day>

    @Query("DELETE FROM day")
    fun deleteAll()

    @Update
    fun update(day: Day)

    @Insert
    fun insert(vararg day: Day)

    @Delete
    fun delete(day: Day)

    @Query("UPDATE DAY SET workout = (:checked), food = (:checked)," +
            "gallon = (:checked), drink = (:checked),read = (:checked), pray = (:checked) " +
            "WHERE weekId = (:weekId) AND number = (:dayNumber)")
    fun checkAll(checked: Boolean, weekId: String, dayNumber: Int)

}