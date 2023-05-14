package jp.speakbuddy.edisonandroidexercise.ui.compose_test

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_DESCRIPTION_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_MANY_CATS_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.FACT_VIEW_TITLE_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.LENGTH_VIEW_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.UPDATE_FACT_BUTTON_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.UPDATE_FACT_LABEL_TAG
import jp.speakbuddy.edisonandroidexercise.MainActivity
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.Mock.catsFactMock
import jp.speakbuddy.edisonandroidexercise.ui.Mock.factMock
import jp.speakbuddy.edisonandroidexercise.ui.Mock.longLength
import jp.speakbuddy.edisonandroidexercise.ui.Mock.shortLength
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactView
import jp.speakbuddy.edisonandroidexercise.ui.fact.LengthView
import jp.speakbuddy.edisonandroidexercise.ui.fact.UpdateFactButton
import org.junit.Rule
import org.junit.Test

class FactScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifyIf_catsFactIsDisplayed() {
        composeTestRule.activity.setContent { FactView(catsFactMock) }

        // Check fact title visibility and content
        composeTestRule.onNodeWithTag(FACT_VIEW_TITLE_TAG).assertTextContains(
            composeTestRule.activity.getString(
                R.string.fact
            )
        ).assertExists()

        // Check multiple cats label visibility and content
        composeTestRule.onNodeWithTag(FACT_VIEW_DESCRIPTION_TAG).assertTextContains(
            catsFactMock
        ).assertExists()
    }

    @Test
    fun verifyIf_multipleCatsLabelIsVisible() {
        composeTestRule.activity.setContent { FactView(catsFactMock) }

        composeTestRule.onNodeWithTag(FACT_VIEW_MANY_CATS_TAG).assertTextContains(
            composeTestRule.activity.getString(R.string.multiple_cats)
        ).assertExists()
    }

    @Test
    fun verifyIf_multipleCatsLabelIsHidden() {
        composeTestRule.activity.setContent { FactView(factMock) }

        composeTestRule.onNodeWithTag(FACT_VIEW_MANY_CATS_TAG).assertDoesNotExist()
    }

    @Test
    fun verifyIf_lengthLabelIsVisible() {
        composeTestRule.activity.setContent { LengthView(length = longLength) }

        composeTestRule.onNodeWithTag(LENGTH_VIEW_TAG).assertTextContains(
            composeTestRule.activity.getString(
                R.string.lbl_length,
                150.toString()
            )
        ).assertExists()
    }

    @Test
    fun verifyIf_lengthLabelIsHidden() {
        composeTestRule.activity.setContent { LengthView(length = shortLength) }

        composeTestRule.onNodeWithTag(LENGTH_VIEW_TAG).assertDoesNotExist()
    }

    @Test
    fun verifyIf_getFactButtonIsDisplayedCorrectly() {
        composeTestRule.activity.setContent { UpdateFactButton(onClick = { }) }
        composeTestRule.onNodeWithTag(UPDATE_FACT_BUTTON_TAG).assertExists()
        composeTestRule.onNodeWithTag(UPDATE_FACT_LABEL_TAG, useUnmergedTree = true)
            .assertTextContains(composeTestRule.activity.getString(R.string.update_fact))
            .assertExists()
    }

    @Test
    fun verifyIf_getFactButtonWorksCorrectly() {
        var clicked = false
        composeTestRule.activity.setContent {
            UpdateFactButton(onClick = { clicked = true })
        }
        composeTestRule.onNodeWithTag(UPDATE_FACT_BUTTON_TAG).performClick()
        assert(clicked)
    }
}