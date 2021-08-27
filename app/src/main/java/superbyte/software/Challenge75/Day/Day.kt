package superbyte.software.Challenge75.Day

import androidx.room.*
import superbyte.software.Challenge75.Week.Week

@Entity(tableName = "Day",
    foreignKeys = [ForeignKey(entity = Week::class,parentColumns = ["weekId"],
        childColumns = ["weekId"],onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["dayId"], unique = true)])

data class Day(
    @PrimaryKey val dayId: String,
    @ColumnInfo(name = "weekId") var weekId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "drink") var drink: Boolean,
    @ColumnInfo(name = "gallon") var gallon: Boolean,
    @ColumnInfo(name = "food") var food: Boolean,
    @ColumnInfo(name = "workout") var workout: Boolean,
    @ColumnInfo(name = "pray") var pray: Boolean,
    @ColumnInfo(name = "read") var read: Boolean,
    @ColumnInfo(name = "number") var number: Int
//    var finished: Boolean = drink && gallon && food && workout && pray && read
)
