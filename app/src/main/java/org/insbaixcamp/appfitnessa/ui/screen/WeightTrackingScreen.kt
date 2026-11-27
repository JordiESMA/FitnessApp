package org.insbaixcamp.appfitnessa.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import org.insbaixcamp.appfitnessa.model.WeightTracking
import org.insbaixcamp.appfitnessa.tools.CalcularIMC
import org.insbaixcamp.appfitnessa.ui.components.WeightTrackingItem
import org.insbaixcamp.appfitnessa.ui.theme.AppFitnessATheme

@Composable
fun WeightTrackingScreen(
    pesActual: Double,
    pesObjectiu: Double,
    alçada: Int
) {
    val TAG = "WeightTrackingScreen"
    val weightTrackingList = remember { mutableStateListOf<WeightTracking>() }
    var pesNouText by remember { mutableStateOf(pesActual.toString()) }
    var pesActual by remember { mutableStateOf(pesActual) }
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(top = 16.dp)) {
            Card(
                Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "pes actual",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$pesActual Kg",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Card(
                Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "pes objectiu",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$pesObjectiu Kg",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Card(
                Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "IMC",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                val imc = CalcularIMC.calcularIMC(pesActual, alçada)
                Text(
                    text = imc.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text("Registra un nou pes",                     fontWeight = FontWeight.Bold
                ,modifier = Modifier.padding(start = 16.dp, top = 16.dp))
        OutlinedTextField(
            value = pesNouText,
            onValueChange = { pesNouText = it },
            label = { Text("Pes en Kg") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                val pesDouble = pesNouText.toDoubleOrNull()
                if (pesDouble != null && pesDouble > 0.0) {
                    val data = System.currentTimeMillis()
                    val weightTracking = WeightTracking(data, pesDouble)
                    weightTrackingList.add( weightTracking)
                    //actualitzo el pes
                    pesActual = pesDouble
                    Log.i(TAG, "Object weightTracking: $weightTracking")
                    Log.i(TAG, "List weightTracking: $weightTrackingList")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Guardar pes")
        }
        Text(
            "Evolució del pes",
            Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        Text(
            text = "Últims registres",
            Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = weightTrackingList,
                key = { weightTracking -> weightTracking.data }
            ) { weightTracking ->
                WeightTrackingItem(weightTracking)
            }
        }
    }
}

@Preview(showBackground = true)
//@PreviewScreenSizes()
@Composable
fun WeightTrackingScreenPreview() {
    AppFitnessATheme {
        WeightTrackingScreen(
            pesActual = 75.0,
            pesObjectiu = 70.0,
            alçada = 175
        )
    }
}