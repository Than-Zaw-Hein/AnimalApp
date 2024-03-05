package com.tzh.animal.ui.screen.animal_detail

import android.app.Application
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tzh.animal.R
import com.tzh.animal.domain.model.Animal
import com.tzh.animal.ui.navigation.AnimalDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(
    private val application: Application, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val animalName = savedStateHandle.get<String>(AnimalDetailRoute.argumentName)
    val animalImage = savedStateHandle.get<Int>(AnimalDetailRoute.argumentImage)
    val animalList = MutableStateFlow<List<Animal>>(emptyList())

    init {
        viewModelScope.launch {
            try {
                animalList.value =
                    animalName.toString().toAnimalMapper(application.applicationContext)
                        ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

suspend fun String.toAnimalMapper(context: Context): List<Animal>? {
    return when (this) {
        "Lion" -> readJsonFromRaw(
            context, R.raw.lion
        ).filter { it.taxonomy.animalClass == "Mammalia" }

        "Elephant" -> readJsonFromRaw(context, R.raw.elephant)

        "Giraffe" -> readJsonFromRaw(context, R.raw.giraffe)

        "Tiger" -> readJsonFromRaw(context, R.raw.tiger)

        "Zebra" -> readJsonFromRaw(context, R.raw.zebra)

        "Cheetah" -> readJsonFromRaw(context, R.raw.cheetah)
        "Dolphin" -> readJsonFromRaw(context, R.raw.dolphin)
        "Whale" -> readJsonFromRaw(context, R.raw.whale)
        "Penguin" -> readJsonFromRaw(context, R.raw.penguin)
        "Koala" -> readJsonFromRaw(context, R.raw.koala)
        "Kangaroo" -> readJsonFromRaw(context, R.raw.kangaroo)
        "Gorilla" -> readJsonFromRaw(context, R.raw.gorilla)
        "Polar Bear" -> readJsonFromRaw(context, R.raw.polar_bear)
        "Panda" -> readJsonFromRaw(context, R.raw.panda)
        "Hippopotamus" -> readJsonFromRaw(context, R.raw.hippopotamus)
        "Crocodile" -> readJsonFromRaw(context, R.raw.crocodile)
        "Snake" -> readJsonFromRaw(context, R.raw.snake)
        "Eagle" -> readJsonFromRaw(context, R.raw.eagle)
        "Octopus" -> readJsonFromRaw(context, R.raw.octopus)
        else -> null
    }
}

suspend fun readJsonFromRaw(context: Context, resourceId: Int): List<Animal> {
    return withContext(Dispatchers.IO) {
        val inputStream: InputStream = context.resources.openRawResource(resourceId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        parseJsonToAnimal(jsonString)
    }
}

suspend fun parseJsonToAnimal(jsonString: String): List<Animal> {
    return withContext(Dispatchers.IO) {
        val gson = Gson()
        val type = object : TypeToken<List<Animal>>() {}.type
        gson.fromJson(jsonString, type)
    }
}
