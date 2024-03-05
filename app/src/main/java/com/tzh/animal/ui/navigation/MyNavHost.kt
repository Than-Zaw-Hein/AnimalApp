package com.tzh.animal.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.tzh.animal.ui.screen.animal_detail.AnimalDetailScreen
import com.tzh.animal.ui.screen.dashboard.DashboardScreen
import com.tzh.animal.ui.screen.full_screen_photo.FullScreenPhoto
import com.tzh.animal.ui.screen.photo_picker.PhotoPickerScreen
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun MyNavHost(navController: NavHostController) {


    val currentRoute = navController.currentBackStackEntryAsState().value.toNavDestination()

    Scaffold(topBar = {
        if (currentRoute != FullScreenPhotoRoute) {
            CenterAlignedTopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (currentRoute is AnimalDetailRoute) {
                        currentRoute.photoImage?.let {
                            val painter = rememberImagePainter(data = it)
                            Box(modifier = Modifier.size(80.dp), Alignment.Center) {
                                if (painter.state is ImagePainter.State.Loading) {
                                    CircularProgressIndicator()
                                }
                                Image(
                                    painter = painter,
                                    contentDescription = currentRoute.title,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .border(
                                            2.dp, MaterialTheme.colorScheme.primary, CircleShape
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Text(text = currentRoute.title)
                }

            })
        }
    }) { padding ->
        NavHost(
            navController = navController,
            startDestination = AnimalListRoute.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(AnimalListRoute.route) {
                DashboardScreen(onNavigateToDetail = { name, image ->
                    navController.navigate(AnimalDetailRoute.navigate(name, image))
                }) {
                    navController.navigate(PhotoPickerRoute.navigate(it))
                }
            }

            composable(
                route = PhotoPickerRoute.routeWithArg, arguments = PhotoPickerRoute.arguments
            ) {
                PhotoPickerScreen {
                    navController.navigate(FullScreenPhotoRoute.navigate(it))
                }
            }

            composable(
                route = AnimalDetailRoute.routeWithArg, arguments = AnimalDetailRoute.arguments
            ) {
                AnimalDetailScreen()
            }

            composable(
                route = FullScreenPhotoRoute.routeWithArg,
                arguments = FullScreenPhotoRoute.arguments
            ) { backStackEntry ->
                val photo =
                    backStackEntry.arguments?.getParcelable<UnsplashPhoto>(FullScreenPhotoRoute.argument)
                FullScreenPhoto(photo)
            }
        }
    }
}

private fun NavBackStackEntry?.toNavDestination(): Destination {

    return when (this?.destination?.route) {
        AnimalListRoute.route -> AnimalListRoute
        AnimalDetailRoute.routeWithArg -> AnimalDetailRoute
        PhotoPickerRoute.routeWithArg -> PhotoPickerRoute
        FullScreenPhotoRoute.routeWithArg -> FullScreenPhotoRoute
        else -> AnimalListRoute
    }
}
