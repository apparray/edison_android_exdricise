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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.viewmodel.fact.FactContract
import com.example.presentation.viewmodel.fact.FactViewModel


@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel(),
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
        var fact by remember { mutableStateOf("") }

        Text(
            text = "Fact",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = fact,
            style = MaterialTheme.typography.bodyLarge
        )

        val onClick = {
            viewModel.setEvent(FactContract.FactEvent.RefreshData)
        }

        Button(onClick = onClick) {
            Text(text = "Update fact")
        }
    }
}
