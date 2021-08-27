package superbyte.software.Challenge75.Week
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import superbyte.software.Challenge75.Day.Day
import superbyte.software.Challenge75.Day.DayRepository
import java.util.*

class WeekViewModel(private var ctx: Context): ViewModel() {
    private var weeks: MutableList<Week>
    private var days: MutableList<Day>


    private val repository: WeekRepository
    private val dayRepository: DayRepository

    init {
        dayRepository = DayRepository(ctx)
        repository = WeekRepository(ctx)
        weeks = repository.fetchWeeks()
        days = dayRepository.fetchDays()
        if(weeks.isEmpty()){
            populateWeeksDays()
            for(week in weeks){
                repository.saveWeek(week)
            }
            for(day in days){
                dayRepository.saveDay(day)
            }
        }
        sortWeeks()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("WeekViewModel", "WeekViewModel destroyed!")
    }

    private fun sortWeeks() {
        weeks.sortBy { week: Week -> week.number }
    }
    fun getWeeks(): MutableList<Week>{
        return weeks
    }
    fun getDayList(weekId: String): MutableList<Boolean>{
        val currentDays = dayRepository.fetchDays(weekId)
        val list: MutableList<Boolean> = mutableListOf()
        for(day in currentDays){
            list.add(day.drink && day.food && day.gallon && day.pray && day.read && day.workout)
        }
        return list
    }
    fun populateWeeksDays(){
        for(i in 1..11) {
            val uuid = UUID.randomUUID().toString()
            weeks.add(
                Week(
                    uuid, "Week ${i}", i
                )
            )
            for(j in 1..7){
                var name: String = "Monday"
                when (j) {
                    1 -> {
                        name = "Monday"
                    }
                    2 -> {
                        name = "Tuesday"
                    }
                    3 -> {
                        name = "Wednesday"
                    }
                    4 -> {
                        name = "Thursday"
                    }
                    5 -> {
                        name = "Friday"
                    }
                    6 -> {
                        name = "Saturday"
                    }
                    7 -> {
                        name = "Sunday"
                    }
                }
                days.add(Day(UUID.randomUUID().toString(), uuid, name,false,false,false,false,false,false,j))
            }
        }
    }

    fun checkAll(checked: Boolean, weekId: String, dayNumber: Int) {
        dayRepository.checkAll(checked,weekId,dayNumber)
    }

    fun getCurrentWeekPosition(): Int {
        var position = -1;
        for(week in weeks){
            val weekDays = days.filter { day: Day -> day.weekId == week.weekId }
            var flag = false
            for(day in weekDays){
                if(day.drink && day.gallon && day.food && day.workout && day.pray && day.read){
                    continue
                }
                else{
                   flag = true
                }
            }
            if(!flag){
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
}