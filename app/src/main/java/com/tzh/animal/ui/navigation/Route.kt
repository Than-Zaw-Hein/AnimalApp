package com.tzh.animal.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto

interface Destination {
    val route: String
    var title: String
}

data object AnimalListRoute : Destination {
    override val route: String
        get() = "AnimalList"
    override var title: String = "AnimalWallpaper"
}


data object AnimalDetailRoute : Destination {
    override val route: String
        get() = "AnimalDetail"
    var argumentName: String = "name"
    var argumentImage: String = "image"
    val routeWithArg = "$route/{$argumentName}/{$argumentImage}"

    override var title: String = ""
    var photoImage: Int? = null
    val arguments = listOf(
        navArgument(argumentName) {
            type = NavType.StringType
        },
        navArgument(argumentImage) {
            type = NavType.IntType
        },
    )

    fun navigate(name: String, resId: Int): String {
        title = name
        photoImage = resId
        return "$route/$name/$resId"
    }


}


data object PhotoPickerRoute : Destination {
    override val route: String
        get() = "PhotoPicker"
    var argument: String = "criteria"
    val routeWithArg = "$route/{$argument}"

    override var title: String = ""
    val arguments = listOf(
        navArgument(argument) {
            type = NavType.StringType
        },
    )

    fun navigate(criteria: String): String {
        title = criteria
        return "$route/$criteria"
    }
}

data object FullScreenPhotoRoute : Destination {
    override val route: String
        get() = "FullScreenPhoto"
    var argument: String = "unsplash"
    val routeWithArg = "$route/{$argument}"

    override var title: String = ""
    val arguments = listOf(
        navArgument(argument) {
            type = UnsPlashParamType()
        },
    )

    fun navigate(photo: UnsplashPhoto): String {
        title = photo.user.name
        val json = Uri.encode(Gson().toJson(photo))
        return "$route/$json"
    }

}


class UnsPlashParamType : NavType<UnsplashPhoto>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UnsplashPhoto? = bundle.getParcelable(key)

    override fun parseValue(value: String): UnsplashPhoto =
        Gson().fromJson(value, UnsplashPhoto::class.java)

    override fun put(bundle: Bundle, key: String, value: UnsplashPhoto) {
        bundle.putParcelable(key, value)
    }
}
