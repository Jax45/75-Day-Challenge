package superbyte.software.Challenge75.Week

import android.app.Activity
import android.content.Context
import androidx.room.Room
import superbyte.software.Challenge75.AppDatabase

class WeekRepository(private var ctx: Context) {
    private val db: AppDatabase

    init {
        if (ctx is Activity) {
            ctx = ctx.applicationContext
        }
        db = Room.databaseBuilder(ctx, AppDatabase::class.java, "app.db")
            .allowMainThreadQueries()
            .build()
    }

    fun updateWeek(week: Week){
        db.weekDao().update(week)
    }

    fun saveWeek(week: Week) {
        db.weekDao().insert(week)
    }

    fun fetchWeeks(): MutableList<Week> {

        return db.weekDao().getAll()
    }

}