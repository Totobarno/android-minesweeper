package hu.bme.aut.android.minesweeper.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import hu.bme.aut.android.minesweeper.data.auth.FirebaseAuthService
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardItem
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirebaseLeaderboardService(
    private val firestore: FirebaseFirestore,
    private val authService: FirebaseAuthService
): LeaderboardService {

    override val items: Flow<List<LeaderboardItem>> = authService.currentUser.flatMapLatest { user ->
        if (user == null) flow { emit(emptyList()) }
        else currentCollection(user.id)
            .snapshots()
            .map { snapshot ->
                snapshot
                    .toObjects<FirebaseLeaderboardItem>()
                    .map {
                        it.asLeaderboardItem()
                    }
            }
    }

    override suspend fun saveItem(item: LeaderboardItem) {
        authService.currentUserId?.let {
            currentCollection(it).add(item.asFirebaseLeaderboardItem()).await()
        }
    }

    private fun currentCollection(userId: String) =
        firestore.collection(USER_COLLECTION).document(userId).collection(LEADERBOARD)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val LEADERBOARD = "leaderboard"
    }
}