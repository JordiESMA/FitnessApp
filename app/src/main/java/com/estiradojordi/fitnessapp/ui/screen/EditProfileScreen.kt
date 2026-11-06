package com.estiradojordi.fitnessapp.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.estiradojordi.fitnessapp.R
import com.estiradojordi.fitnessapp.components.DataSelector
import com.estiradojordi.fitnessapp.components.MenuDesplegable


@Composable
fun EditProfileScreen(
    nom: String,
    gmail: String,
    dataNaixement: String,
    genere: String,
    alcada: Int,
    pes: Double,
    pesObjectiu: Double,
    objectiu: String,
    nivellActivitat: String,
    guardar: (String, String, String, String, Int, Double, Double, String, String) -> Unit
) {
    var nomNou by remember { mutableStateOf(nom) }
    var emailNou by remember { mutableStateOf(gmail) }
    var dataNaixementNova by remember { mutableStateOf(dataNaixement) }
    var genereNou by remember { mutableStateOf(genere) }
    var alcadaNova by remember { mutableStateOf(alcada) }
    var pesNou by remember { mutableStateOf(pes) }
    var pesObjectiuNou by remember { mutableStateOf(pesObjectiu) }
    var objectiuNou by remember { mutableStateOf(objectiu) }
    var nivellActivitatNou by remember { mutableStateOf(nivellActivitat) }
    var generes by remember { mutableStateOf(listOf("Masculí", "Femení", "Altres", "No dire")) }

    Column() {
        Row() {

            //IMAGEN CON SUS BOTONES DE CAMBIAR O NO

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
            )
            Column() {
                Button(onClick = {}) { Text(text = "Canviar foto") }
                Button(onClick = {}) { Text(text = "Eliminar foto") }
            }
        }

        //DATOS PERSONALES

        Text(text = "Dades Personals")

        //NOMBRE COMPLETO

        OutlinedTextField(
            label = { Text("Nom complet") },
            value = nomNou,
            onValueChange = { nomNou = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        //CORREO


        OutlinedTextField(
            label = { Text("Email") },
            value = emailNou,
            onValueChange = { emailNou = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        //DATA DE NAIXEMENT
        DataSelector(
            label = "Data de naixement",
            selectedDateMillis = dataNaixementNova.toLongOrNull(),
            onDateSelected = { dataNaixementNova = it.toString() },
            modifier = Modifier
                .padding(8.dp)
        )

        //GENERE

        MenuDesplegable(
            label = "Genere",
            opcions = generes,
            valorSeleccionat = genereNou,
            onValorSeleccionat = { genereNou = it },
            modifier = Modifier
                .padding(8.dp)
        )

        //DADES FISIQUES
        Text(text = "Dades físiques")
        Row() {

            //ALÇADA

            OutlinedTextField(
                label = { Text("Alçada") },
                value = alcadaNova.toString(),
                onValueChange = { alcadaNova = it.toIntOrNull() ?: alcadaNova },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

            //PES

            OutlinedTextField(
                label = { Text("Pes") },
                value = pesNou.toString(),
                onValueChange = { pesNou = it.toDoubleOrNull() ?: pesNou },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

            //PES OBJECTIU

            OutlinedTextField(
                label = { Text("Pes objetciu") },
                value = pesObjectiuNou.toString(),
                onValueChange = { pesObjectiuNou = it.toDoubleOrNull() ?: pesObjectiuNou },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)

            )


        }
        Button(onClick = { guardar(nomNou,emailNou,dataNaixementNova,genereNou,alcadaNova,pesNou,pesObjectiuNou,objectiuNou,nivellActivitatNou) }) {
            Text("Guardar dades")
        }


        }
    }

