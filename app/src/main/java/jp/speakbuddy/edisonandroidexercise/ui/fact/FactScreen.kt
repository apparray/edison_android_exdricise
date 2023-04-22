package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.repository.network.Fact
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.ui.fact.usecases.FactUseCases
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

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
        var fact: Fact? by remember { mutableStateOf(null) }
        Text(
            text = "Fact",
            style = MaterialTheme.typography.titleLarge
        )
        FactView(fact = fact)

        val onClick = {
            fact = viewModel.updateFact { print("done") }
        }

        Button(onClick = onClick) {
            Text(text = "Update fact")
        }
    }


}
@Composable
private fun FactView(fact: Fact?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        MultipleCatsText(isVisible = doestContainCats(fact?.fact))
        Text(
            text = fact?.fact ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
        FactLengthText(fact?.length)
    }
}




@Composable
private fun MultipleCatsText(isVisible: Boolean) {
    if (isVisible) {
        Text(
            text = "Multiple cats!",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun FactLengthText(length: Int?) {
    if (length != null && length > 100) {
        Text(
            text = "Length: $length",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun doestContainCats(st: String?): Boolean = st?.contains("cats") ?: false

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(viewModel = FactViewModel(PreviewUseCases()))
    }
}

// Sorry I could figure out a better solution right on the spot
private class PreviewUseCases: FactUseCases {
    override suspend fun getFact(): Fact {
        return Fact("stub", 1)
    }
}
