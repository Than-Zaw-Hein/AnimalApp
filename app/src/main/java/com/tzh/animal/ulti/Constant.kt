package com.tzh.animal.ulti

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.tzh.animal.R
import com.tzh.animal.domain.model.AnimalLocal

object Constant {
    val animalLocalLists = listOf(
        AnimalLocal("Lion", R.drawable.lion),
        AnimalLocal("Elephant", R.drawable.elephant),
        AnimalLocal("Giraffe", R.drawable.giraffe),
        AnimalLocal("Tiger", R.drawable.tiger),
        AnimalLocal("Zebra", R.drawable.zebra),
        AnimalLocal("Cheetah", R.drawable.cheetah),
        AnimalLocal("Dolphin", R.drawable.dolphin),
        AnimalLocal("Whale", R.drawable.whale),
        AnimalLocal("Penguin", R.drawable.penguin),
        AnimalLocal("Koala", R.drawable.koala),
        AnimalLocal("Kangaroo", R.drawable.kangaroo),
        AnimalLocal("Gorilla", R.drawable.gorilla),
        AnimalLocal("Polar Bear", R.drawable.polar_bear),
        AnimalLocal("Panda", R.drawable.panda),
        AnimalLocal("Hippopotamus", R.drawable.hippopotamus),
        AnimalLocal("Crocodile", R.drawable.crocodile),
        AnimalLocal("Snake", R.drawable.snake),
        AnimalLocal("Eagle", R.drawable.eagle),
        AnimalLocal("Octopus", R.drawable.octopus)
    )
}

fun String.fromHex() = try {
    Color(this.replace("#", "0xFF").toLong())
} catch (e: Exception) {
    Color.Transparent
}

fun requestFullScreen(view: View) {
    val window = view.context.getActivity()?.window
    window?.let {
        WindowCompat.getInsetsController(it, view).hide(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
        )
    }
}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}