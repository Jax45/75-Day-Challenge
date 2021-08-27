package superbyte.software.Challenge75.Day

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel

class DayViewModel(private var ctx: Context): ViewModel() {
    private val repository: DayRepository
    private lateinit var days: MutableList<Day>

    init {

        repository = DayRepository(ctx)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("WeekViewModel", "WeekViewModel destroyed!")
    }


    fun getDays(): MutableList<Day>{
        return days;
    }

    fun updateDay(day: Day): Boolean {
        repository.updateDay(day)
        return day.drink && day.gallon && day.food && day.workout && day.pray && day.read
    }

    fun getCurrentDayPosition(): Int {
        var position = -1;
        for(day in days){
            if(day.drink && day.gallon && day.food && day.workout && day.pray && day.read){
                position += 1
            }
            else
                break
        }
        return if(position < 0)
            0
        else
            position
    }

    fun populateDays(weekId: String) {
        days = repository.fetchDays(weekId)
    }
}