package superbyte.software.Challenge75.Week

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Week",indices = [Index(value = ["weekId"], unique = true)])

data class Week(
    @PrimaryKey val weekId: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "number") val number: Int?
)
