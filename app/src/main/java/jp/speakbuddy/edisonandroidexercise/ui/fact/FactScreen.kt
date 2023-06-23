package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.Fact
import jp.speakbuddy.edisonandroidexercise.R

@Composable
fun FactScreen(viewModel: FactViewModel) {
    var fact: Fact? by remember { mutableStateOf(null) }

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
            text = stringResource(id = R.string.fact),
            style = MaterialTheme.typography.titleLarge
        )

        val pattern = Regex("\\bcat's\\b|\\bcats\\b", RegexOption.IGNORE_CASE)
        val containsCats = pattern.containsMatchIn(fact?.fact.toString())
        if (containsCats) {
            Text(text = stringResource(id = R.string.multiple_cats),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (fact != null) {
            Text(
                text = fact?.fact.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (fact != null && fact?.fact?.length as Int >= 100) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "Length: ${fact?.fact?.length}",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        val onClick = {
            fact =  viewModel.updateFact { "update done" }
        }

        Button(onClick = onClick) {
            Text(text = stringResource(R.string.update_fact))
        }
    }
}