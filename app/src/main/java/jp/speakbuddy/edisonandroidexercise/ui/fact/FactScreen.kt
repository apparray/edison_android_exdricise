package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_DESCRIPTION_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_MANY_CATS_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_TITLE_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.LENGTH_VIEW_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.UPDATE_FACT_BUTTON_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.UPDATE_FACT_LABEL_TAG
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun FactScreen(paddingValues: PaddingValues) {
    val viewModel: FactViewModel = getViewModel()
    val fact = viewModel.fact.observeAsState()
    val length = viewModel.length.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        FactView(fact = fact.value ?: "")
        LengthView(length = length.value ?: 0)
        UpdateFactButton(onClick = {
            viewModel.updateFact()
        })
    }
}

@Composable
fun FactView(fact: String) {
    Text(
        text = stringResource(id = R.string.fact),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.testTag(FACT_VIEW_TITLE_TAG)
    )

    if (fact.contains(stringResource(id = R.string.cats), ignoreCase = true)) {
        Text(
            text = stringResource(id = R.string.multiple_cats),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.testTag(FACT_VIEW_MANY_CATS_TAG)
        )
    }

    Text(
        text = fact,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .testTag(FACT_VIEW_DESCRIPTION_TAG),
    )
}

@Composable
fun LengthView(length: Int) {
    if (length >= 100) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.lbl_length, length.toString()),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .testTag(LENGTH_VIEW_TAG)
            )
        }
    }
}

@Composable
fun UpdateFactButton(onClick: (Unit) -> Unit) {
    Button(onClick = { onClick(Unit) }, modifier = Modifier.testTag(UPDATE_FACT_BUTTON_TAG)) {
        Text(
            text = stringResource(id = R.string.update_fact),
            modifier = Modifier.testTag(UPDATE_FACT_LABEL_TAG)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FactView(fact = "Lorem ipsum dolor sit cats, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
            LengthView(length = 120)
            UpdateFactButton(onClick = {})
        }
    }
}
