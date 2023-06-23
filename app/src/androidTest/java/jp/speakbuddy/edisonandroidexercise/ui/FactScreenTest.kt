package jp.speakbuddy.edisonandroidexercise.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.repository.FactRepository
import com.example.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class FactScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var getFactUseCase: GetFactUseCase

    private var factRepository: FactRepository = mock()

    @Test
    fun testFactScreen() {

        getFactUseCase = GetFactUseCase(factRepository)
        val viewModel = FactViewModel(getFactUseCase) // Replace with your actual view model

        composeTestRule.setContent {
            FactScreen(viewModel = viewModel)
        }

        // Wait for the initial content to be displayed
        composeTestRule.waitForIdle()

        // Verify that the initial text is displayed
        composeTestRule.onNodeWithText("Fact", useUnmergedTree = true).assertIsDisplayed()

        // Perform a click on the button
        composeTestRule.onNodeWithText("Update fact").performClick()

        // Wait for the update to be applied
        runBlocking {
            composeTestRule.awaitIdle()
        }
    }
}
