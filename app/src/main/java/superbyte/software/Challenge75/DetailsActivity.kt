package superbyte.software.Challenge75

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_home.*
import superbyte.software.Challenge75.Day.Day
import superbyte.software.Challenge75.Day.DayViewModel


class DetailsActivity : AppCompatActivity() {
    private var nightNotify: Boolean = false
    private var viewModel: DayViewModel? = null
    private var weekId: String = ""
    private lateinit var notify: myNotificationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        weekId = intent.getStringExtra("weekId")!!
        supportActionBar?.hide()

        nightNotify = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                "nightNotify",
                true
        )
        //notification
        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
        val noonNotify = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                "noonNotify",
                true
        )
        notify = myNotificationManager(this,alarm,getSharedPreferences("Settings",Context.MODE_PRIVATE),nightNotify,noonNotify)


        viewModel = ViewModelProvider(this, DayViewModelFactory(this)).get(
            DayViewModel::class.java
        )
        viewModel!!.populateDays(weekId);
        val recycler: RecyclerView = findViewById(R.id.daysRecyclerView);
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = DayAdapter()

        //Scroll item
        recycler.scrollToPosition(viewModel!!.getCurrentDayPosition());
    }
    inner class DayAdapter: RecyclerView.Adapter<DayHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
            val inflater = LayoutInflater.from(this@DetailsActivity)
            val itemView = inflater.inflate(R.layout.day_item_view, parent, false)
            return DayHolder(itemView)
        }


        override fun getItemCount(): Int = viewModel!!.getDays().size

        override fun onBindViewHolder(holder: DayHolder, position: Int) {
            var day = viewModel!!.getDays()[position]
            val drink: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.DrinkCheckBox)
            drink.setOnCheckedChangeListener { buttonView, isChecked ->
                day.drink = isChecked
                if(viewModel!!.updateDay(day))
                    notify.dayFinished()
            }
            val food: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.FoodCheckBox)
            food.setOnCheckedChangeListener { buttonView, isChecked ->
                day.food = isChecked
                if(viewModel!!.updateDay(day))
                    notify.dayFinished()
            }
            val gallon: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.GallonCheckBox)
            gallon.setOnCheckedChangeListener { buttonView, isChecked ->
                day.gallon = isChecked
                if(viewModel!!.updateDay(day))
                    notify.dayFinished()
            }
            val pray: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.PrayCheckBox)
            pray.setOnCheckedChangeListener { buttonView, isChecked ->
                day.pray = isChecked
                if(viewModel!!.updateDay(day))
                    notify.dayFinished()
            }
            val workout: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.WorkoutCheckBox)
            workout.setOnCheckedChangeListener { buttonView, isChecked ->
                day.workout =isChecked
                if(viewModel!!.updateDay(day))
                    notify.dayFinished()
            }
            val read: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.ReadCheckBox)
            read.setOnCheckedChangeListener { buttonView, isChecked ->
                day.read = isChecked
                if(viewModel!!.updateDay(day))
                    notify.dayFinished()
            }
            holder.bindDay(day)

        }

    }
    class DayHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {


        private val DayTextView: TextView = itemView.findViewById(R.id.DayLabel)
        private val DrinkCheck: CheckBox = itemView.findViewById(R.id.DrinkCheckBox)
        private val GallonCheck: CheckBox = itemView.findViewById(R.id.GallonCheckBox)
        private val FoodCheck: CheckBox = itemView.findViewById(R.id.FoodCheckBox)
        private val WorkoutCheck: CheckBox = itemView.findViewById(R.id.WorkoutCheckBox)
        private val PrayCheck: CheckBox = itemView.findViewById(R.id.PrayCheckBox)
        private val ReadCheck: CheckBox = itemView.findViewById(R.id.ReadCheckBox)


        fun bindDay(day: Day) {
            DayTextView.text = day.name
            DrinkCheck.isChecked = day.drink
            GallonCheck.isChecked = day.gallon
            FoodCheck.isChecked = day.food
            WorkoutCheck.isChecked = day.workout
            PrayCheck.isChecked = day.pray
            ReadCheck.isChecked = day.read
        }
    }
}