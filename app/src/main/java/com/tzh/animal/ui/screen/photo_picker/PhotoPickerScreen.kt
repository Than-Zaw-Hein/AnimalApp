package com.tzh.animal.ui.screen.photo_picker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.tzh.animal.R
import com.tzh.animal.ulti.fromHex
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.PhotoPickerViewModel

@Composable
fun PhotoPickerScreen(
    viewModel: PhotoPickerViewModel = hiltViewModel(),
    onShowFullScreen: (UnsplashPhoto) -> Unit
) {
    val photoLazyPagingItems: LazyPagingItems<UnsplashPhoto> =
        viewModel.photosLiveData.collectAsLazyPagingItems()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        flingBehavior = ScrollableDefaults.flingBehavior(),
    ) {
        items(photoLazyPagingItems.itemCount) { item ->
            ListViewItem(item = photoLazyPagingItems[item]!!) {
                onShowFullScreen(photoLazyPagingItems[item]!!)
            }
        }
        photoLazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                }

                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                }

                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                }
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListViewItem(item: UnsplashPhoto, onShowFullScreen: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .combinedClickable(
                onLongClick = onShowFullScreen
            ) {

            }
            .background(
                item.color?.fromHex() ?: Color.Transparent
            )
            .padding(4.dp)
    ) {
        val image = rememberImagePainter(data = item.urls.small)

        Image(
            painter = image,
            contentDescription = item.id,
            modifier = Modifier.aspectRatio((item.height.toDouble() / item.width.toDouble()).toFloat()),
            contentScale = ContentScale.FillBounds
        )
        if (image.state is ImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        }

        if (image.state is ImagePainter.State.Error) {
            Image(
                painter = painterResource(id = R.drawable.broken_image),
                contentDescription = "Error",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        }

        Text(
            text = item.user.name,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}
