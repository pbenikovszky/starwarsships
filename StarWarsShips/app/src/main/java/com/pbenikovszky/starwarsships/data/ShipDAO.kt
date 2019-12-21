package com.pbenikovszky.starwarsships.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShipDAO {

    @Query ("SELECT * from ships")
    fun getAllShips(): List<Ship>

    @Query("SELECT * from favourites")
    fun getAllFavourites(): List<Favourite>

    @Query ("SELECT * from favourites WHERE name=:favName")
    fun getFavouriteByName(favName: String): Favourite

    @Insert
    suspend fun insertShip(ship: Ship)

    @Insert
    suspend fun insertShips(ships: List<Ship>)

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Query("DELETE from ships")
    suspend fun deleteAllShips()

}