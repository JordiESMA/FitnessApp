package org.insbaixcamp.appfitnessa.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.insbaixcamp.appfitnessa.FitnessApp
import org.insbaixcamp.appfitnessa.model.WeightTracking
import org.insbaixcamp.appfitnessa.ui.theme.AppFitnessATheme

@Composable
fun WeightTrackingItem(weightTracking: WeightTracking) {
    Row(Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = formatDate(weightTracking.data),
            modifier = Modifier.weight(1f))
        Text(text = "${weightTracking.pes} Kg",
            modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun WeightTrackingItemPreview() {
    var weightTracking = WeightTracking(System.currentTimeMillis(),
                                        75.0)
    AppFitnessATheme {
        WeightTrackingItem(weightTracking)
    }
}
