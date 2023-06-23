package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.model.Fact
import com.example.domain.usecase.GetFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFactUseCase: GetFactUseCase
) : ViewModel() {
    /*  private val _factState = mutableStateOf<Resource<Fact>>(Resource.Loading())
      val factState: State<Resource<Fact>> = _factState
      fun getFacts() {
          viewModelScope.launch {
              _factState.value = getFactUseCase()
          }
      }*/

    fun updateFact(completion: () -> Unit): Fact? =
        runBlocking {
            try {
                Log.d("data 1","111==$getFactUseCase()")
                Log.d("data 2","222==${getFactUseCase().data}")
                getFactUseCase().data
            } catch (e: Throwable) {
                Log.e("exception", e.message.toString())
                null
            }.also { completion() }
        }
}
