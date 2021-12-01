package com.example.clean.presentation.coin_details.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clean.presentation.coin_details.CoinDetailsViewModel
import com.example.clean.ui.theme.TealFFA4FCF3
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun CoinDetailScreen(navController: NavController, coindId: String) {

    val viewModel = getViewModel<CoinDetailsViewModel>() {
        parametersOf(coindId)
    }

    val coinDetailSate = viewModel.coinDetailLiveData.observeAsState()
    val loaderState = viewModel.loaderState

    Box(
        Modifier
            .fillMaxSize()
            .background(color = TealFFA4FCF3)
    ) {

        coinDetailSate.value?.let {
            Text(
                text = """
                ${it.id}
                ${it.name}
                ${it.contract}
                ${it.developmentStatus}
                ${it.isActive}
                ${it.isNew}
            """.trimIndent(),
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { navController.popBackStack() }
            )
        }

        Surface(
            color = Color.Transparent, modifier = Modifier.align (Alignment.Center)
        ) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier
                    .alpha(if (loaderState.value) 1f else 0f)
            )
        }

    }
}