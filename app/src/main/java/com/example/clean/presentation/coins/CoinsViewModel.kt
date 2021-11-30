package com.example.clean.presentation.coins

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Exceptions
import com.example.core.common.NetResult
import com.example.core.models.Coin
import com.example.domain.interactors.GetCoinsInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class CoinsViewModel(
    private val getCoinsInteractor: GetCoinsInteractor
) : ViewModel() {

    val coinsState = mutableStateOf<List<Coin>>(emptyList())
    val loaderState = mutableStateOf(false)

    init {

        viewModelScope.launch {
            //todo launched effect inside the compose
            getCoinsInteractor(true).collectNetResult(
                actionOnLoading = {
                    loaderState.value = true
                },
                actionOnCache = {
                    coinsState.value = it
                    loaderState.value = true
                },
                actionOnSuccess = {
                    coinsState.value = it
                    loaderState.value = false
                },
                actionOnError = {
                    loaderState.value = false
                    //todo handle exceptions
                }
            )
        }

    }

    //todo
    fun handleExceptions(it: Throwable, extraHandles: ((Throwable) -> Unit)? = null) {
        when (it as Exception) {
            is Exceptions.NetworkException -> Log.e("Exceptions_TAG", "NetworkException")
            is Exceptions.ServerException -> Log.e("Exceptions_TAG", "ServerException")
            is Exceptions.FailureException -> Log.e("Exceptions_TAG", "FailureException")
            is Exceptions.EmptyResponseException -> Log.e(
                "Exceptions_TAG",
                "EmptyResponseException"
            )
            else -> Log.e("Exceptions_TAG", "else")
        }
        extraHandles?.invoke(it)
    }


    //todo
    suspend fun <T : Any> Flow<NetResult<T>>.collectNetResult(
        collectorDispatcher: CoroutineDispatcher = Dispatchers.IO,
        actionOnLoading: () -> Unit,
        actionOnCache: (value: T) -> Unit,
        actionOnSuccess: (value: T) -> Unit,
        actionOnError: (value: NetResult.Error) -> Unit,
    ) =
        withContext(collectorDispatcher) {
            this@collectNetResult.collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is NetResult.Loading -> {
                            actionOnLoading()
                        }
                        is NetResult.Cache -> {
                            actionOnCache(result.data)
                        }
                        is NetResult.Success<T> -> {
                            actionOnSuccess(result.data)
                        }
                        is NetResult.Error -> {
                            actionOnError(result)
                        }
                    }
                }
            }
        }


}