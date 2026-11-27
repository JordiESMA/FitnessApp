package org.insbaixcamp.appfitnessa.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.insbaixcamp.appfitnessa.ui.components.ExerciseItem
import org.insbaixcamp.appfitnessa.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(viewModel: ExerciseViewModel = viewModel()) {
    var cercaTmp by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    val exercises = viewModel.exercises
    val filteredExercises = exercises.filter {
        it.name.contains(cercaTmp, ignoreCase = true)
    }
    val isLoading = viewModel.isLoading
    val error = viewModel.error
    val hasNextPage = viewModel.hasNextPage

    // Els índexs corresponen a la llista filtrada que es mostra a la pantalla.
    val nearEnd by remember(listState, filteredExercises.size) {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            filteredExercises.isNotEmpty() && lastVisible >= 0 &&
                lastVisible >= filteredExercises.size - 6
        }
    }

    LaunchedEffect(nearEnd, exercises.size, isLoading, error, hasNextPage) {
        if (nearEnd && !isLoading && error == null && hasNextPage) {
            viewModel.loadMore()
        }
    }

    LaunchedEffect(cercaTmp) {
        listState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Exercicis",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = cercaTmp,
            onValueChange = { cercaTmp = it },
            label = { Text("Cerca l'exercici que vols") },
            supportingText = { Text("Cerca entre els exercicis carregats") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().testTag("exercise_list"),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = filteredExercises,
                key = { it.exerciseId }
            ) { exercise ->
                ExerciseItem(exercise = exercise)
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (error != null) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (exercises.isEmpty()) {
                                    viewModel.loadExercises()
                                } else {
                                    viewModel.loadMore()
                                }
                            }
                        ) {
                            Text("Tornar a provar")
                        }
                    }
                }
            }

            if (filteredExercises.isEmpty() && !isLoading && error == null) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(if (cercaTmp.isBlank()) "No hi ha exercicis disponibles."
                            else "No hi ha coincidències entre els exercicis carregats.")
                        if (hasNextPage) {
                            Button(onClick = { viewModel.loadMore() }) {
                                Text("Cercar en més exercicis")
                            }
                        }
                    }
                }
            }

            if (exercises.isNotEmpty() && !hasNextPage && !isLoading && error == null) {
                item {
                    Text(
                        text = "Has arribat al final dels exercicis.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
