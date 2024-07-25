package hu.bme.aut.android.minesweeper.feature.menu.difficulty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.minesweeper.R
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom

@ExperimentalMaterial3Api
@Composable
fun DifficultyMenuScreen(
    onGameButtonClick: (Int, Int, Int, Int, Boolean) -> Unit,
    onLeaderboardButtonClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: DifficultyMenuViewModel = viewModel(factory = DifficultyMenuViewModel.Factory)
){
    val state = viewModel.state.collectAsStateWithLifecycle().value

    MinesweeperTheme(custom = custom) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.choose_difficulty)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.tertiary,
                        actionIconContentColor = MaterialTheme.colorScheme.tertiary,
                        navigationIconContentColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                when(state) {
                    is DifficultyMenuState.Loading-> CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                    is DifficultyMenuState.Menu -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = {
                                if(state.isGame)
                                    onGameButtonClick(9, 9, 10, 1, false)
                                else
                                    onLeaderboardButtonClick(1)
                            }) {
                                Text(text = stringResource(id = R.string.easy), color = MaterialTheme.colorScheme.tertiary)
                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            Button(onClick = {
                                if(state.isGame)
                                    onGameButtonClick(16, 16, 40, 2, false)
                                else
                                    onLeaderboardButtonClick(2)
                            }) {
                                Text(text = stringResource(id = R.string.medium), color = MaterialTheme.colorScheme.tertiary)
                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            Button(onClick = {
                                if(state.isGame)
                                    onGameButtonClick(30, 16, 99, 3, false)
                                else
                                    onLeaderboardButtonClick(3)
                            }) {
                                Text(text = stringResource(id = R.string.hard), color = MaterialTheme.colorScheme.tertiary)
                            }
                        }
                    }
                }
            }
        }
    }
}