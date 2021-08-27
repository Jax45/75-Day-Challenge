package superbyte.software.Challenge75

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import superbyte.software.Challenge75.Week.Week
import superbyte.software.Challenge75.Week.WeekViewModel
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var notify: myNotificationManager
    private var viewModel: WeekViewModel? = null
    private lateinit var recycler: RecyclerView
    private var checkAll: Boolean = false
    private var nightNotify: Boolean = false
    private var time: Long = 0



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, SettingsActivity::class.java)
        this.startActivity(intent)
        return true
    }

    override fun onResume() {
        super.onResume()
        checkAll = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("checkAll", true)
        nightNotify = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
            "nightNotify",
            true
        )
        time = getSharedPreferences("Settings",Context.MODE_PRIVATE).getLong(
            "time",
            Calendar.getInstance().timeInMillis
        )

        recycler.adapter?.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProvider(this, WeekViewModelFactory(this)).get(
            WeekViewModel::class.java
        )
        checkAll = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("checkAll", true)
        nightNotify = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
            "nightNotify",
            true
        )
        val noonNotify = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                "noonNotify",
                true
        )
        time = getSharedPreferences("Settings",Context.MODE_PRIVATE).getLong(
            "time",
            Calendar.getInstance().timeInMillis
        )

        //menu
        this.actionBar?.setDisplayHomeAsUpEnabled(true)


        //Notification
        val savedCalendar = Calendar.getInstance()
        savedCalendar.timeInMillis = time
        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
        notify = myNotificationManager(this,alarm,getSharedPreferences("Settings",Context.MODE_PRIVATE),nightNotify,noonNotify)
        notify.sendNightNotify(!savedCalendar.after(Calendar.getInstance()))
        notify.sendNoonNotify(!savedCalendar.after(Calendar.getInstance()))


        recycler = findViewById(R.id.HomeActivityWeekRecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = WeekAdapter()
        //Scroll item
        recycler.scrollToPosition(viewModel!!.getCurrentWeekPosition());
    }



    inner class WeekAdapter: RecyclerView.Adapter<WeekHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekHolder {
            val inflater = LayoutInflater.from(this@HomeActivity)
            val itemView = inflater.inflate(R.layout.week_item_view, parent, false)
            return WeekHolder(itemView)
        }


        override fun getItemCount(): Int = viewModel!!.getWeeks().size

        override fun onBindViewHolder(holder: WeekHolder, position: Int) {
            val week = viewModel!!.getWeeks()[position]
            val list = viewModel!!.getDayList(week.weekId)
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
                intent.putExtra("weekId", viewModel!!.getWeeks()[position].weekId)
                holder.itemView.context.startActivity(intent)
            }

            val monday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.MonCheckbox)
            monday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 1)
                if(isChecked)
                    notify.dayFinished()
            }
            val tuesday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.TueCheckbox)
            tuesday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 2)
                if(isChecked)
                    notify.dayFinished()
            }
            val wednesday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.WedCheckbox)
            wednesday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 3)
                if(isChecked)
                    notify.dayFinished()
            }
            val thursday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.ThurCheckbox)
            thursday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 4)
                if(isChecked)
                    notify.dayFinished()
            }
            val friday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.FriCheckbox)
            friday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 5)
                if(isChecked)
                    notify.dayFinished()
            }
            val saturday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.SatCheckbox)
            saturday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 6)
                if(isChecked)
                    notify.dayFinished()
            }
            val sunday: CheckBox = holder.itemView.findViewById<CheckBox>(R.id.SunCheckbox)
            sunday.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel!!.checkAll(isChecked, week.weekId, 7)
                if(isChecked)
                    notify.dayFinished()
            }

            //check all off at once shared pref
            monday.isEnabled = checkAll
            tuesday.isEnabled = checkAll
            wednesday.isEnabled = checkAll
            thursday.isEnabled = checkAll
            friday.isEnabled = checkAll
            saturday.isEnabled = checkAll
            sunday.isEnabled = checkAll


            holder.bindWeek(week, list)

        }

    }
    class WeekHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {


        private val WeekTextView: TextView = itemView.findViewById(R.id.WeekLabel)
        private val MondayCheck: CheckBox = itemView.findViewById(R.id.MonCheckbox)
        private val TuesdayCheck: CheckBox = itemView.findViewById(R.id.TueCheckbox)
        private val WednesdayCheck: CheckBox = itemView.findViewById(R.id.WedCheckbox)
        private val ThursdayCheck: CheckBox = itemView.findViewById(R.id.ThurCheckbox)
        private val FridayCheck: CheckBox = itemView.findViewById(R.id.FriCheckbox)
        private val SaturdayCheck: CheckBox = itemView.findViewById(R.id.SatCheckbox)
        private val SundayCheck: CheckBox = itemView.findViewById(R.id.SunCheckbox)


        fun bindWeek(week: Week, dayList: List<Boolean>) {
            WeekTextView.text = week.name
            MondayCheck.isChecked = dayList[0]
            TuesdayCheck.isChecked = dayList[1]
            WednesdayCheck.isChecked = dayList[2]
            ThursdayCheck.isChecked = dayList[3]
            FridayCheck.isChecked = dayList[4]
            SaturdayCheck.isChecked = dayList[5]
            SundayCheck.isChecked = dayList[6]

        }
    }
}