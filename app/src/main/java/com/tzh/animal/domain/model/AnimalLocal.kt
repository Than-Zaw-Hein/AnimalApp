package com.tzh.animal.domain.model

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class AnimalLocal(val name: String, @DrawableRes val image: Int)

data class Animal(
    val name: String,
    val taxonomy: Taxonomy,
    val locations: List<String>,
    val characteristics: Characteristics
)

data class Taxonomy(
    val kingdom: String,
    val phylum: String,
    @SerializedName("class")
    val animalClass: String,
    val order: String,
    val family: String,
    val genus: String,
    @SerializedName("scientific_name")
    val scientificName: String
)

data class Characteristics(
    // You can add more properties based on the actual data
    val prey: String?,
    val name_of_young: String?,
    val group_behavior: String?,
    val biggest_threat: String?,
    val most_distinctive_feature: String?,
    val gestation_period: String?,
    val litter_size: String?,
    val habitat: String?,
    val diet: String?,
    val type: String?,
    val common_name: String?,
    val origin: String?,
    val number_of_species: String?,
    val location: String?,
    val color: String?,
    val skin_type: String?,
    val top_speed: String?,
    val lifespan: String?,
    val weight: String?,
    val height: String?,
    val length: String?,
    val age_of_sexual_maturity: String?,
    val age_of_weaning: String?
)