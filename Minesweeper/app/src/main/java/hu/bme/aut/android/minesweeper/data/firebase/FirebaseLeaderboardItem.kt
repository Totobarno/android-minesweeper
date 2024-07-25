package hu.bme.aut.android.minesweeper.data.firebase

import com.google.firebase.firestore.DocumentId
import com.google.firebase.Timestamp
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardItem
import kotlinx.datetime.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

data class FirebaseLeaderboardItem(
    @DocumentId val id: String = "",
    val score: Int = 0,
    val name: String = "",
    val date: Timestamp = Timestamp.now(),
    val difficulty: Int = 0
)
fun FirebaseLeaderboardItem.asLeaderboardItem() = LeaderboardItem(
    id = id,
    score = score,
    name = name,
    date = LocalDateTime
        .ofInstant(Instant.ofEpochSecond(date.seconds), ZoneId.systemDefault())
        .toKotlinLocalDateTime()
        .date,
    difficulty = difficulty,
)

fun LeaderboardItem.asFirebaseLeaderboardItem() = FirebaseLeaderboardItem(
    id = id,
    score = score,
    name = name,
    date = Timestamp(Date.from(date.atStartOfDayIn(TimeZone.currentSystemDefault()).toJavaInstant())),
    difficulty = difficulty,
)