package com.example.tabatatimer.reproductor

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Gif(url: String, modifier: Modifier = Modifier){
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context).components{
        add(ImageDecoderDecoder.Factory())
    }.build()

    val request = ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .build()

    AsyncImage(
        model = request,
        contentDescription = "GIF",
        imageLoader = imageLoader,
        modifier = modifier
    )
}