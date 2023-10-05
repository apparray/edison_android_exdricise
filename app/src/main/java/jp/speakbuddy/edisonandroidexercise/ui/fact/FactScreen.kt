package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FactScreen(
    viewModel: FactViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Fact",
            style = MaterialTheme.typography.titleLarge
        )

        if (viewModel.factState.value.isMultipleCats) {
            Text(text = "Multiple cats!!", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }

        Text(
            text = viewModel.factState.value.fact,
            style = MaterialTheme.typography.bodyLarge
        )

        if (viewModel.factState.value.isShowingLength) {
            val length = viewModel.factState.value.length.toString()
            Text(
                text = "Length: $length",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        val onClick = {
            viewModel.updateFact()
        }

        Button(onClick = onClick) {
            Text(text = "Update fact")
        }
    }
}
