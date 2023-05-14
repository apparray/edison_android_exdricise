package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.speakbuddy.edisonandroidexercise.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FactViewModel(
    private val factRepository: FactRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _fact = MutableLiveData<String>()
    val fact: LiveData<String> = _fact

    private val _length = MutableLiveData<Int>()
    val length: LiveData<Int> = _length

    fun updateFact() {
        viewModelScope.launch(dispatcher) {
            when (val response = factRepository.getFact()) {
                is FactResponse.Success -> {
                    _fact.postValue(response.factResponse.fact)
                    _length.postValue(response.factResponse.length)
                }

                is FactResponse.Error -> {
                    Timber.e(response.error)
                }
            }
        }
    }
}
