package com.example.clean.presentation.coins

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Exceptions
import com.example.core.common.NetResult
import com.example.core.models.Coin
import com.example.domain.interactors.GetCoinsInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class CoinsViewModel(
    private val getCoinsInteractor: GetCoinsInteractor
): ViewModel() {

    val coinsState = mutableStateOf<List<Coin>>( emptyList() )
    val loaderState = mutableStateOf(false)

    init {

        viewModelScope.launch {

            //todo launched effect inside the compose
            getCoinsInteractor.invoke()
                .collectNetResult(
                    withLoading = true,
                    actionOnSuccess = {
                      coinsState.value = it
                    },
                    actionOnError = {
                        when(it){
                            is NetResult.Error.NetworkError -> {
                                Log.e("Exceptions_TAG", "NetworkException")
                            }
                        }
                    }
                )
        }

    }

    //todo
    fun handleExceptions(it: Throwable, extraHandles: ((Throwable) -> Unit)? = null) {
        when(it as Exception){
            is Exceptions.NetworkException -> Log.e("Exceptions_TAG", "NetworkException")
            is Exceptions.ServerException -> Log.e("Exceptions_TAG", "ServerException")
            is Exceptions.FailureException -> Log.e("Exceptions_TAG", "FailureException")
            is Exceptions.EmptyResponseException -> Log.e("Exceptions_TAG", "EmptyResponseException")
            else -> Log.e("Exceptions_TAG", "else")
        }
        extraHandles?.invoke(it)
    }


    //todo
    suspend fun <T : Any> Flow<NetResult<T>>.collectNetResult(
        withLoading: Boolean = false,
        actionOnSuccess: suspend (value: T) -> Unit,
        actionOnError: suspend (value: NetResult.Error) -> Unit,
    ) =
        this.collect { result ->
            when{
                result is NetResult.Loading && withLoading -> {
                    loaderState.value = true
                }
                result is NetResult.Success<T> -> {
                    if (withLoading) loaderState.value = false
                    actionOnSuccess.invoke(result.data)
                }
                result is NetResult.Error -> {
                    if (withLoading) loaderState.value = false
                    actionOnError.invoke(result)
                }
            }
        }



}