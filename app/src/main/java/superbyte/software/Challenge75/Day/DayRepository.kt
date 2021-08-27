package superbyte.software.Challenge75.Day

import android.app.Activity
import android.content.Context
import androidx.room.Room
import superbyte.software.Challenge75.AppDatabase
import superbyte.software.Challenge75.Week.Week

class DayRepository(private var ctx: Context) {
    private val db: AppDatabase

    init {
        if (ctx is Activity) {
            ctx = ctx.applicationContext
        }
        db = Room.databaseBuilder(ctx, AppDatabase::class.java, "app.db")
            .allowMainThreadQueries()
            .build()
    }

    fun updateDay(day: Day){
        db.dayDao().update(day)
    }

    fun saveDay(day: Day){
        db.dayDao().insert(day)
    }
//    8b29b769-90c8-4a9c-b672-730b8f2d8f87
    //8b29b769-90c8-4a9c-b672-730b8f2d8f87
    fun fetchDays(): MutableList<Day> {

        return db.dayDao().getAll()
    }
    fun fetchDays(weekId: String): MutableList<Day> {

        return db.dayDao().getAll(weekId)
    }

    fun checkAll(checked: Boolean, weekId: String, dayNumber: Int) {
        db.dayDao().checkAll(checked,weekId,dayNumber)
    }
}