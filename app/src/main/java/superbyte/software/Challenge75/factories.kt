package superbyte.software.Challenge75

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import superbyte.software.Challenge75.Day.DayViewModel
import superbyte.software.Challenge75.Week.WeekViewModel

class WeekViewModelFactory(private val ctx: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeekViewModel(ctx) as T
    }

}

class DayViewModelFactory(private val ctx: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DayViewModel(ctx) as T
    }

}