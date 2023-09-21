package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.presentation.viewmodel.fact.FactContract
import jp.speakbuddy.edisonandroidexercise.Constants
import jp.speakbuddy.edisonandroidexercise.MainActivity
import jp.speakbuddy.edisonandroidexercise.R
import org.junit.Rule
import org.junit.Test


class FactScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkViewStateShowCats() {
        val mockFactCats = "cats."

        val testState = FactContract.FactViewState(fact = mockFactCats, showHintCats = true)
        composeTestRule.activity.setContent { FactView(testState) }

        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_TITLE_TAG).assertTextContains(
            composeTestRule.activity.getString(
                R.string.fact
            )
        ).assertExists()

        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_DESCRIPTION_TAG).assertTextContains(
            mockFactCats
        ).assertExists()

        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_MANY_CATS_TAG).assertTextContains(
            composeTestRule.activity.getString(R.string.multiple_cats)
        ).assertExists()
    }

    @Test
    fun checkViewStateShowCat() {
        val mockFact = "cat"
        val testState = FactContract.FactViewState(fact = mockFact, showHintCats = false)
        composeTestRule.activity.setContent { FactView(testState) }

        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_TITLE_TAG).assertTextContains(
            composeTestRule.activity.getString(
                R.string.fact
            )
        ).assertExists()

        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_DESCRIPTION_TAG).assertTextContains(
            mockFact
        ).assertExists()

        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_MANY_CATS_TAG).assertDoesNotExist()
    }

    @Test
    fun checkViewStateShowIndicator() {
        val testState = FactContract.FactViewState(loading = true)
        composeTestRule.activity.setContent {
            FactView(testState)
            LoadingIndicator(state = testState)
        }
        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_TITLE_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_MANY_CATS_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(Constants.Test.FACT_VIEW_DESCRIPTION_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(Constants.Test.LOADING_INDICATOR_VIEW).assertExists()
    }

    @Test
    fun checkViewStateShowLengthView() {
        val mockLength = "30"
        val testState = FactContract.FactViewState(showHintCats = true, length = mockLength)
        composeTestRule.activity.setContent { LengthView(testState) }
        composeTestRule.onNodeWithTag(Constants.Test.LENGTH_VIEW_TAG).assertTextContains(
            composeTestRule.activity.getString(R.string.length, mockLength)
        ).assertExists()
    }

    @Test
    fun checkViewStateHiddenLengthView() {
        val mockLength = "30"
        val testState = FactContract.FactViewState(showHintCats = false, length = mockLength)
        composeTestRule.activity.setContent { LengthView(testState) }
        composeTestRule.onNodeWithTag(Constants.Test.LENGTH_VIEW_TAG).assertDoesNotExist()
    }

    @Test
    fun checkViewStateShowUpdateFactButton() {
        composeTestRule.activity.setContent {
            UpdateFactButton(
                state = FactContract.FactViewState(),
                onClick = { })
        }
        composeTestRule.onNodeWithTag(Constants.Test.UPDATE_FACT_BUTTON_TAG).assertExists()
        composeTestRule.onNodeWithTag(Constants.Test.UPDATE_FACT_LABEL_TAG, useUnmergedTree = true)
            .assertTextContains(composeTestRule.activity.getString(R.string.update_fact))
            .assertExists()
    }

    @Test
    fun checkRefreshButtonIsWorkingProperly() {
        var isClicked = false
        composeTestRule.activity.setContent {
            UpdateFactButton(
                state = FactContract.FactViewState(),
                onClick = { isClicked = true })
        }
        composeTestRule.onNodeWithTag(Constants.Test.UPDATE_FACT_BUTTON_TAG).performClick()
        assert(isClicked)
    }
}