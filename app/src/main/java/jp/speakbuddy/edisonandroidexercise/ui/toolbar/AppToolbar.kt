package jp.speakbuddy.edisonandroidexercise.ui.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R

@Composable
fun AppToolBar() {
    Surface(
        shadowElevation = 8.dp
    ) {
        Row(
            Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(
                text = stringResource(id = R.string.cats_facts),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewAppToolBar() {
    AppToolBar()
}