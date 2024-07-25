package hu.bme.aut.android.minesweeper.feature.menu.main_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.minesweeper.R
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom

@ExperimentalMaterial3Api
@Composable
fun MainMenuScreen(
    onNewGameClick: () -> Unit,
    onSignOut: () -> Unit,
    onLoadGame: (Int, Int, Int, Int, Boolean) -> Unit,
    onLeaderboardClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: MainMenuViewModel = viewModel(factory = MainMenuViewModel.Factory)
){

    MinesweeperTheme(custom = custom) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.tertiary,
                        actionIconContentColor = MaterialTheme.colorScheme.tertiary,
                        navigationIconContentColor = MaterialTheme.colorScheme.tertiary
                    ),
                    actions = {
                        IconButton(onClick = {
                            viewModel.signOut()
                            onSignOut()
                        }) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                        }
                    }
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { onNewGameClick() }) {
                        Text(text = stringResource(id = R.string.new_game), color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = { onLoadGame(0, 0, 0, 0, true) }) {
                        Text(text = stringResource(id = R.string.load_game), color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = { onLeaderboardClick() }) {
                        Text(text = stringResource(id = R.string.leaderboards), color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = { onSettingsClick() }) {
                        Text(text = stringResource(id = R.string.settings), color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}
