package com.tzh.animal.ui.screen.full_screen_photo

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.tzh.animal.R
import com.tzh.animal.ulti.requestFullScreen
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FullScreenPhoto(photo: UnsplashPhoto?, viewModel: ImageViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val downloadEnabled by produceState(initialValue = false) {
        val rg = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val wg = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val mg = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        value = rg && wg && mg
    }

    LaunchedEffect(key1 = viewModel.message.value) {
        if (viewModel.message.value.isNotEmpty()) {
            Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
        }
    }

    if (viewModel.isDownloading.value) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    if (photo == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.broken_image),
                contentDescription = "",
            )
        }
    } else {
        val view = LocalView.current
        SideEffect {
            requestFullScreen(view)
        }
        // Load and display the photo
        val painter = rememberImagePainter(data = photo.urls.raw)
        Surface(color = Color.Black) {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                if (painter.state is ImagePainter.State.Loading) {
                    CircularProgressIndicator()
                }

                Image(
                    painter = painter,
                    contentDescription = photo.user.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
                DownloadButton(downloadEnabled, photo, viewModel)
            }
        }
    }
}

@Composable
private fun BoxScope.DownloadButton(
    downloadEnabled: Boolean,
    photo: UnsplashPhoto,
    viewModel: ImageViewModel
) {
    if (downloadEnabled) {
        IconButton(
            onClick = {
                if (!photo.urls.raw.isNullOrEmpty()) {
                    viewModel.downloadImageFromUrl(photo.urls.raw)
                }
            }, modifier = Modifier.Companion
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.download),
                contentDescription = "Download icon",
                modifier = Modifier
                    .size(58.dp)
                    .padding(4.dp),
                tint = Color.White
            )
        }
    }
}