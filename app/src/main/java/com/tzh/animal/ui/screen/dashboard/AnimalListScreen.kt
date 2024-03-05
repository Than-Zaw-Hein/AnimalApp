package com.tzh.animal.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.tzh.animal.ulti.Constant


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DashboardScreen(
    viewModel: AnimalListViewModel = hiltViewModel(),
    onNavigateToDetail: (String, Int) -> Unit, onNavigatePhotoPicker: (String) -> Unit
) {

    val lazyState = rememberLazyListState()
    LazyColumn(
        state = lazyState, modifier = Modifier.fillMaxSize()
    ) {
        items(Constant.animalLocalLists) {
            ElevatedCard(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), onClick = {
                onNavigatePhotoPicker(it.name)
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Use rememberImagePainter to load and display the image
                    val painter = rememberImagePainter(data = it.image)
                    Box(modifier = Modifier.size(100.dp), Alignment.Center) {
                        if (painter.state is ImagePainter.State.Loading) {
                            CircularProgressIndicator()
                        }
                        Image(
                            painter = painter,
                            contentDescription = it.name,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.None
                        )
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        text = it.name,
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    IconButton(onClick = {
                        onNavigateToDetail(it.name, it.image)
                    }, modifier = Modifier.align(Alignment.Top)) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = it.name + "-About"
                        )
                    }
                }
            }
        }
    }
}
