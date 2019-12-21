package com.pbenikovszky.starwarsships.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ships")
data class Ship (

    @PrimaryKey(autoGenerate = true)
    val shipId: Int = 0,
    val name: String,
    val model: String,
    val manufacturer: String,
    val cost_in_credits: String,
    val length: String,
    val max_atmosphering_speed: String,
    val crew: String,
    val passengers: String,
    val cargo_capacity: String,
    val consumables: String,
    val hyperdrive_rating: String,
    val MGLT: String,
    val starship_class: String

)
