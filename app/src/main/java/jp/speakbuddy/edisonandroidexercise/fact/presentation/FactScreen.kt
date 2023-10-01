package jp.speakbuddy.edisonandroidexercise.fact.presentation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

@Composable
fun FactScreen(
    state: FactUiState,
    onEvent: (FactUiEvent) -> Unit
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

        if (state.showMultipleCats) {
            Text(
                text = "Multiple cats!!",
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Text(
            text = state.fact,
            style = MaterialTheme.typography.bodyLarge
        )

        state.length?.let {
            Text(
                text = "Length: $it",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        val onClick = {
            onEvent(FactUiEvent.UpdateFact { println("done") })
        }

        Button(onClick = onClick) {
            Text(text = "Update fact")
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(
            state = FactUiState(
                fact = "Cat families usually play best in even numbers. cats and kittens should be acquired in pairs whenever possible",
                length = "111",
                showMultipleCats = true
            ),
            onEvent = { }
        )
    }
}
