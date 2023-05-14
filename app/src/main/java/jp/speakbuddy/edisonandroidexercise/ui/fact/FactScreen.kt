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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun FactScreen() {
    val viewModel: FactViewModel = getViewModel()
    val fact = viewModel.fact.observeAsState()
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

        Text(
            text = fact.value ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

        val onClick = {
            viewModel.updateFact()
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
        FactScreen()
    }
}
