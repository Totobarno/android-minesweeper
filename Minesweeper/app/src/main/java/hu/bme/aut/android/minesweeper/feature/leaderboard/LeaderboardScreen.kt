package hu.bme.aut.android.minesweeper.feature.leaderboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.minesweeper.R
import hu.bme.aut.android.minesweeper.ui.model.toUiText
import androidx.compose.foundation.lazy.items
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun LeaderboardScreen(
    onNavigateBack: () -> Unit,
    viewModel: LeaderboardViewModel = viewModel(factory = LeaderboardViewModel.Factory)
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    MinesweeperTheme(custom = custom) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        if(state.difficulty == 1){
                            Text(text = stringResource(id = R.string.easy_leaderboard))
                        }
                        else if(state.difficulty == 2){
                            Text(text = stringResource(id = R.string.medium_leaderboard))
                        }
                        else{
                            Text(text = stringResource(id = R.string.hard_leaderboard))
                        }
                    },
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
            },
            bottomBar = {
                BottomAppBar (
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.tertiary
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { viewModel.sortByName() }) {
                                Icon(Icons.Filled.Person, contentDescription = "sort by name")
                            }
                            Text(text = stringResource(id = R.string.name))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { viewModel.sortByScore() }) {
                                Icon(Icons.Filled.AddCircle, contentDescription = "sort by score")
                            }
                            Text(text = stringResource(id = R.string.score))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { viewModel.sortByDate() }) {
                                Icon(Icons.Filled.CalendarMonth, contentDescription = "sort by date")
                            }
                            Text(text = stringResource(id = R.string.date))
                        }
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ){
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }else if (state.isError) {
                    Text(
                        text = state.error?.toUiText()?.asString(context)
                            ?: stringResource(id = R.string.error)
                    )
                } else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(5.dp))
                    ){
                        items(state.items, key = {item -> item.id}){item ->
                            ListItem(
                                headlineContent = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primary),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ){
                                        Text(text = item.name, color = MaterialTheme.colorScheme.tertiary)
                                        Text(text = item.score.toString(), color = MaterialTheme.colorScheme.tertiary)
                                        Text(text = item.date.toString(), color = MaterialTheme.colorScheme.tertiary)
                                    }
                                },
                                modifier = Modifier.animateItemPlacement(),
                            )
                            if (state.items.last() != item) {
                                Divider(
                                    thickness = 5.dp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}