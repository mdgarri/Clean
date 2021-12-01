package com.example.clean.presentation.coin_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.RequestType
import com.example.core.common.collectNetResult
import com.example.core.models.CoinDetails
import com.example.domain.interactors.GetCoinDetailInteractor
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
    private val coinId: String,
    private val getCoinDetailInteractor: GetCoinDetailInteractor
): ViewModel() {

    val coinDetailLiveData = MutableLiveData<CoinDetails>()
    val loaderState = mutableStateOf(false)


    init {
        viewModelScope.launch {
            getCoinDetails(coinId)
        }
    }

    private suspend fun getCoinDetails(coinId: String) {
        getCoinDetailInteractor(RequestType.CACHE_AND_API, coinId).collectNetResult(
            actionOnLoading = {
                loaderState.value = true
            },
            actionOnCache = {
                loaderState.value = true
                coinDetailLiveData.value = it
            },
            actionOnSuccess = {
                loaderState.value = false
                coinDetailLiveData.value = it
            },
            actionOnError = {
                loaderState.value = false
                //todo handle errors
            }
        )
    }

}