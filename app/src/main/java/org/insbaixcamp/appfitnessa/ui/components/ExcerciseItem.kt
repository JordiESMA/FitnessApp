package org.insbaixcamp.appfitnessa.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.insbaixcamp.appfitnessa.model.Exercise
import org.insbaixcamp.appfitnessa.ui.theme.AppFitnessATheme

@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = exercise.gifUrl,
                contentDescription = exercise.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = exercise.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = exercise.bodyParts.joinToString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseItemPreview() {
    val exercise = Exercise(
        exerciseId = "0001",
        name = "Press de banca amb barra",
        gifUrl = "",
        bodyParts = listOf("Pit", "Tríceps"),
        equipments = listOf("Barra"),
        targetMuscles = listOf("Pectoral major"),
        instructions = listOf(
            "Estira't al banc pla amb els peus ferms a terra.",
            "Baixa la barra fins al pit i empeny cap amunt."
        )
    )
    AppFitnessATheme {
        ExerciseItem(exercise = exercise)
    }
}