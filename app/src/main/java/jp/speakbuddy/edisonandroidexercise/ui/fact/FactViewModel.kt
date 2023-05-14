package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.speakbuddy.edisonandroidexercise.data.local.`interface`.Storage
import jp.speakbuddy.edisonandroidexercise.data.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.network.view_data.Fact
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class FactViewModel(
    private val factRepository: FactRepository,
    private val dispatcher: CoroutineDispatcher,
    private val factStorage: Storage<Fact>
) : ViewModel() {

    private val _fact = MutableLiveData<String>()
    val fact: LiveData<String> = _fact

    private val _length = MutableLiveData<Int>()
    val length: LiveData<Int> = _length

    fun updateFact() {
        viewModelScope.launch(dispatcher) {
            when (val response = factRepository.getFact()) {
                is FactResponse.Success -> {

                    factStorage.clearAll()
                    factStorage.insert(response.factResponse).collect()

                    _fact.postValue(response.factResponse.fact)
                    _length.postValue(response.factResponse.length)
                }

                is FactResponse.Error -> {
                    Timber.e(response.error)
                    factStorage.getAll()
                        .catch {
                            println(it)
                        }.collect {
                            it.firstOrNull()?.let { factData ->
                                _fact.postValue(factData.fact)
                                _length.postValue(factData.length)
                            }
                        }
                }
            }
        }
    }
}
