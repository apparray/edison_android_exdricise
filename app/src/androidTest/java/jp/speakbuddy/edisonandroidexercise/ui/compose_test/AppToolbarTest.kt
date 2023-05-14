package jp.speakbuddy.edisonandroidexercise.ui.compose_test

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import jp.speakbuddy.edisonandroidexercise.Constants.Test.APP_TOOLBAR_BACKGROUND_TAG
import jp.speakbuddy.edisonandroidexercise.Constants.Test.APP_TOOLBAR_TITLE_TAG
import jp.speakbuddy.edisonandroidexercise.MainActivity
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.toolbar.AppToolBar
import org.junit.Rule
import org.junit.Test

class AppToolbarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifyIf_toolbarIsVisible() {
        composeTestRule.activity.setContent { AppToolBar() }
        composeTestRule.onNodeWithTag(APP_TOOLBAR_BACKGROUND_TAG).assertExists()
    }

    @Test
    fun verifyIf_titleIsDisplayedCorrectly() {
        composeTestRule.activity.setContent { AppToolBar() }
        composeTestRule.onNodeWithTag(APP_TOOLBAR_TITLE_TAG)
            .assertTextContains(composeTestRule.activity.getString(R.string.cats_facts))
            .assertExists()
    }
}