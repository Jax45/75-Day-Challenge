package superbyte.software.Challenge75

import androidx.room.Database
import androidx.room.RoomDatabase
import superbyte.software.Challenge75.Day.Day
import superbyte.software.Challenge75.Day.DayDAO
import superbyte.software.Challenge75.Week.Week
import superbyte.software.Challenge75.Week.WeekDAO

@Database(entities = [Week::class, Day::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weekDao(): WeekDAO
    abstract fun dayDao(): DayDAO

}