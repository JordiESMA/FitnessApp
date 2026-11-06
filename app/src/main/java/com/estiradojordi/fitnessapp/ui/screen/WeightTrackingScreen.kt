package com.estiradojordi.fitnessapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estiradojordi.fitnessapp.ui.theme.FitnessAppTheme
import com.estiradojordi.fitnessapp.tools.calculateIMC

@Composable
fun WeightTrackingScren(
    pesActual: Double,
    pesObjectiu: Double,
    alcada: Int
) {
    Column( Modifier.fillMaxWidth()) {
        Row( Modifier.padding(all = 8.dp)) {
            Card(Modifier.weight(1f).padding(6.dp)) {
                Text(" pes actual")
                Text("${pesActual} Kg")
            }
            Card(Modifier.weight(1f).padding(6.dp)) {
                Text("Pes objectiu")
                Text("${pesActual} Kg")
            }
            Card(Modifier.weight(1f).padding(6.dp)) {

                var imc = calculateIMC(pesActual , alcada)
                Text("IMC")
                Text("%.2f".format(imc))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun WeightTrackingScrenPreview() {
    FitnessAppTheme {
        WeightTrackingScren(
            pesActual = 75.0,
            pesObjectiu = 70.0,
            alcada = 175
        )
    }
}