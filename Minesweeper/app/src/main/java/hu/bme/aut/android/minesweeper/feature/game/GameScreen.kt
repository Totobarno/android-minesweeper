package hu.bme.aut.android.minesweeper.feature.game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.minesweeper.R
import hu.bme.aut.android.minesweeper.ui.common.EndGameDialog
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom


@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun GameScreen(
    onNavigateBack: () -> Unit,
    viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
){
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val zoomState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }
    var showDialog by remember{ mutableStateOf(true) }
    var changed by remember{ mutableStateOf(false) }
    var remainingText by remember{ mutableIntStateOf(viewModel.playTable.remaining) }
    val context = LocalContext.current

    MinesweeperTheme(custom = custom) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Remaining: $remainingText") },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.saveLevel(context)
                            onNavigateBack()
                        }) {
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
        ){
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .transformable(state = zoomState)
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                when(state){
                    is GameState.Loading -> CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                    is GameState.Game -> {
                        if(state.load) {
                            viewModel.loadLevel(context)
                            remainingText = viewModel.playTable.remaining
                        }
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(viewModel.playTable.column),
                                modifier = Modifier.padding(it)
                            ) {
                                items(viewModel.playTable.Size) { i ->
                                    var row = i / viewModel.playTable.column
                                    var column = i % viewModel.playTable.column

                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .aspectRatio(1f)
                                            .background(MaterialTheme.colorScheme.primary)
                                            .combinedClickable(
                                                onClick = {
                                                    if (!viewModel.playTable.Table[row][column].flagged) {
                                                        viewModel.openCell(row, column)
                                                        changed = !changed
                                                    }
                                                },
                                                onLongClick = {
                                                    if (!viewModel.playTable.Table[row][column].open) {
                                                        viewModel.changeFlagState(row, column)
                                                        remainingText = viewModel.playTable.remaining
                                                        changed = !changed
                                                    }
                                                },
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (changed) {
                                            if (viewModel.playTable.Table[row][column].open) {
                                                Text(
                                                    text = "${viewModel.playTable.Table[row][column].value}",
                                                    color = MaterialTheme.colorScheme.tertiary
                                                )
                                            } else if (viewModel.playTable.Table[row][column].flagged) {
                                                Text(
                                                    text = "F",
                                                    color = MaterialTheme.colorScheme.tertiary
                                                )
                                            }
                                        } else {
                                            if (viewModel.playTable.Table[row][column].open) {
                                                Text(
                                                    text = "${viewModel.playTable.Table[row][column].value}",
                                                    color = MaterialTheme.colorScheme.tertiary
                                                )
                                            } else if (viewModel.playTable.Table[row][column].flagged) {
                                                Text(
                                                    text = "F",
                                                    color = MaterialTheme.colorScheme.tertiary
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is GameState.End -> {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Button(onClick = { onNavigateBack() }) {
                                Text(text = stringResource(id = R.string.return_main_menu), color = MaterialTheme.colorScheme.tertiary)
                            }
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(viewModel.playTable.column),
                                modifier = Modifier.padding(it)
                            ) {
                                items(viewModel.playTable.Size) { i ->
                                    var row = i / viewModel.playTable.column
                                    var column = i % viewModel.playTable.column
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .aspectRatio(1f)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (viewModel.playTable.Table[row][column].value == 9) {
                                            Text(
                                                text = "${viewModel.playTable.Table[row][column].value}",
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                        } else if (viewModel.playTable.Table[row][column].open) {
                                            Text(
                                                text = "${viewModel.playTable.Table[row][column].value}",
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                        } else if (viewModel.playTable.Table[row][column].flagged) {
                                            Text(
                                                text = "F",
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if(showDialog) {
                            EndGameDialog(
                                gameStatus = state.win,
                                score = viewModel.gameScore,
                                onConfirm = { nameText ->
                                    viewModel.saveScore(nameText)
                                    showDialog = false
                                },
                                onShare = { nameText ->
                                    viewModel.shareScore(nameText, context)
                                },
                                onDismiss = { showDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }
}