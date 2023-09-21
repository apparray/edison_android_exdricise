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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.viewmodel.fact.FactContract
import com.example.presentation.viewmodel.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_DESCRIPTION_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_MANY_CATS_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_TITLE_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.LENGTH_VIEW_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.UPDATE_FACT_BUTTON_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.UPDATE_FACT_LABEL_TAG
import jp.speakbuddy.edisonandroidexercise.R


@Composable
fun FactScreen(
    padding: PaddingValues,
    viewModel: FactViewModel = hiltViewModel(),
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        if (viewState.loading) {
            LoadingIndicator()
        } else {
            FactView(state = viewState)
            LengthView(state = viewState)
            UpdateFactButton(onClick = {
                viewModel.setEvent(FactContract.FactEvent.RefreshData)
            })
        }
    }
}

@Composable
fun FactView(state: FactContract.FactViewState) {
    Text(
        text = stringResource(id = R.string.fact),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.testTag(FACT_VIEW_TITLE_TAG)
    )

    if (state.showHintCats) {
        Text(
            text = stringResource(id = R.string.multiple_cats),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.testTag(FACT_VIEW_MANY_CATS_TAG)
        )
    }

    Text(
        text = if (state.retry) stringResource(state.errorMsg) else state.fact,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .testTag(FACT_VIEW_DESCRIPTION_TAG),
    )
}

@Composable
fun LengthView(state: FactContract.FactViewState) {
    if (state.showHintCats && !state.retry) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.length, state.length.toString()),
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


@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

