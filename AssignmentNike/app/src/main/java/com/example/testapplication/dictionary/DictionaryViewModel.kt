package com.example.testapplication.dictionary

import androidx.lifecycle.MutableLiveData
import com.example.testapplication.core.functional.map
import com.example.testapplication.core.platform.BaseViewModel
import com.example.testapplication.dictionary.model.ResultView
import com.example.testapplication.dictionary.model.UiState
import com.example.testapplication.dictionary.usecases.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DictionaryViewModel constructor(private val getResult: GetResult) : BaseViewModel() {

    var resultLiveData: MutableLiveData<UiState> = MutableLiveData()
    private var ascending = MutableLiveData<Boolean>()
    private var job: Job? = null
    /**
     * Get data from api using use case.
     */
    fun getSuggestions(term: String) {
        resultLiveData.postValue(UiState.Loading)
        job?.cancel()
        job = launch(Dispatchers.IO) {
            getResult.run(GetResult.Param(term))
                .map { list ->
                    list.map { it.toResultView() }
                }
                .either({
                    resultLiveData.postValue(UiState.Error(it))
                }, {
                    resultLiveData.postValue(toSuccessUiState(ascending.value == true, it))
                })
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun orderResult(ascending: Boolean) {
        this.ascending.value = ascending
        with(resultLiveData.value) {
            when (this) {
                is UiState.Success -> {
                    resultLiveData.value = toSuccessUiState(ascending, this.result)
                }
            }
        }
    }

    private fun toSuccessUiState(
        ascending: Boolean,
        result: List<ResultView>
    ): UiState {
        return if (ascending) {
            UiState.Success(result.sortedBy { it.thumbsUp })
        } else {
            UiState.Success(result.sortedByDescending { it.thumbsUp })
        }
    }
}