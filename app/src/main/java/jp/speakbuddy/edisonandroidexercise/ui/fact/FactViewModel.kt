package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.domain.GetFactStateUseCase
import jp.speakbuddy.edisonandroidexercise.model.FactState
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(private val useCase: GetFactStateUseCase) : ViewModel() {

    private var _factState = mutableStateOf(FactState())
    val factState: State<FactState>
        get() = _factState

    fun updateFact() {
        runBlocking {
            _factState.value = useCase.getFactState()
        }
    }
}
