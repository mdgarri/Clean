package com.example.clean.presentation.base

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun BaseScreen (block: @Composable() () -> Unit) {
    val loaderState = rememberSaveable{ mutableStateOf(false) }
    Box {
        CircularProgressIndicator(modifier = Modifier.alpha(if (loaderState.value) 1f else 0f).align(Alignment.Center))
        block()
    }
}

