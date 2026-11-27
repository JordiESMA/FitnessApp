package org.insbaixcamp.appfitnessa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.insbaixcamp.appfitnessa.ui.screen.EditProfileScreen
import org.insbaixcamp.appfitnessa.ui.screen.ProfileScreen
import org.insbaixcamp.appfitnessa.ui.screen.ExerciseScreen
import org.insbaixcamp.appfitnessa.ui.screen.WeightTrackingScreen
import org.insbaixcamp.appfitnessa.ui.theme.AppFitnessATheme
import org.insbaixcamp.appfitnessa.viewmodel.ExerciseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFitnessATheme {
                FitnessApp()
            }
        }
    }
}

enum class Screen(val label: String, val icon: ImageVector) {
    PROFILE("Perfil", Icons.Default.Person),
    WEIGHT_TRACKING("Pes", Icons.Default.MonitorWeight),
    EXERCISE("Exercicis", Icons.Default.FitnessCenter)
}

@Composable
fun FitnessApp() {
    var currentScreen by remember { mutableStateOf(Screen.PROFILE) }

    var nom: String by remember { mutableStateOf("Laia Soler") }
    var email: String by remember { mutableStateOf("laia.soler@exemple.com") }
    var dataNaixement: Long by remember { mutableStateOf(915148800000L) }
    var genere: String by remember { mutableStateOf("Dona") }
    var alçada: Int by remember { mutableStateOf(170) }
    var pes: Double by remember { mutableStateOf(60.0) }
    var pesObjectiu: Double by remember { mutableStateOf(60.0) }
    var objectiu: String by remember { mutableStateOf("Guanyar massa muscular") }
    var nivellActivitat: String by remember { mutableStateOf("Moderat") }
    var editant: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                Screen.entries.forEach { screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Screen.PROFILE -> {
                    if (editant) {
                        EditProfileScreen(
                            nom,
                            email,
                            dataNaixement,
                            genere,
                            alçada,
                            pes,
                            pesObjectiu,
                            objectiu,
                            nivellActivitat,
                            { nomN, emailN, dataNaixementN, genereN, alçadaN, pesN, pesObjectiuN, objectiuN, nivellActivitatN ->
                                nom = nomN
                                email = emailN
                                dataNaixement = dataNaixementN
                                genere = genereN
                                alçada = alçadaN
                                pes = pesN
                                pesObjectiu = pesObjectiuN
                                objectiu = objectiuN
                                nivellActivitat = nivellActivitatN
                                editant = false
                            }
                        )
                    } else {
                        ProfileScreen(
                            nom, email, dataNaixement, genere, alçada,
                            pes, objectiu, nivellActivitat,
                            { editant = true }
                        )
                    }
                }
                Screen.WEIGHT_TRACKING -> {
                    WeightTrackingScreen(
                        pesActual = pes,
                        pesObjectiu = pesObjectiu,
                        alçada = alçada
                    )
                }
                Screen.EXERCISE -> {
                    val exerciseViewModel: ExerciseViewModel = viewModel()
                    ExerciseScreen(viewModel = exerciseViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFitnessATheme {
        FitnessApp()
    }
}