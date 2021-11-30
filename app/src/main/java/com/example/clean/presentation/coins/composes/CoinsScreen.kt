package com.example.clean.presentation.coins.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clean.presentation.coins.CoinsViewModel
import com.example.core.models.Coin
import org.koin.androidx.compose.getViewModel

@Composable
fun CoinsScreen(navController: NavController){
    val viewModel = getViewModel<CoinsViewModel>()

    val coinsState = viewModel.coinsState
    val loaderState = viewModel.loaderState

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn {
            items(coinsState.value) { coin ->
                CoinItem(coin)
            }
        }

        Surface(modifier = Modifier.align(Alignment.Center), color = Color.Transparent) {
            CircularProgressIndicator(modifier = Modifier.alpha(if (loaderState.value) 1f else 0f))
        }
    }
}

@Composable
fun CoinItem(coin: Coin) = Row(
    Modifier
        .background(MaterialTheme.colors.secondary)
        .fillMaxWidth()
        .wrapContentHeight()
) {

        Text(
            text = coin.id,
            color = MaterialTheme.colors.onSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .clickable {

                }
        )

        Text(
            text = coin.name,
            color = MaterialTheme.colors.onSecondary,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = coin.symbol,
            color = MaterialTheme.colors.onSecondary,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = coin.symbol,
            color = MaterialTheme.colors.onSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

}