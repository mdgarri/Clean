package com.example.clean.presentation.coins

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.RequestType
import com.example.core.common.collectNetResult
import com.example.core.models.Coin
import com.example.domain.interactors.GetCoinsInteractor
import kotlinx.coroutines.*

class CoinsViewModel(
    private val getCoinsInteractor: GetCoinsInteractor
) : ViewModel() {

    val coinsState = mutableStateOf<List<Coin>>(emptyList())
    val loaderState = mutableStateOf(false)

    init {
        viewModelScope.launch {
            //todo launched effect inside the compose
            getCoinsInteractor(RequestType.CACHE_AND_API).collectNetResult(
                actionOnLoading = {
                    loaderState.value = true
                },
                actionOnCache = {
                    loaderState.value = true
                    coinsState.value = it
                },
                actionOnSuccess = {
                    loaderState.value = false
                    coinsState.value = it
                },
                actionOnError = {
                    loaderState.value = false
                    //todo handle errors
                }
            )
        }

    }

}