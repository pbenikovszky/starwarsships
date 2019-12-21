package com.pbenikovszky.starwarsships.ui.shared

import android.app.Application
import androidx.lifecycle.*
import com.pbenikovszky.starwarsships.data.Favourite
import com.pbenikovszky.starwarsships.data.Ship
import com.pbenikovszky.starwarsships.data.ShipRepository

class SharedViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo = ShipRepository(app)
    val shipData = dataRepo.shipData
    val favData = dataRepo.favData

    val selectedShip = MutableLiveData<Ship>()

    val formattedCrewText = Transformations.map(selectedShip) {
        it.crew + " person"
    }

    fun refreshData() {
        dataRepo.refreshData()
    }

    fun addShipToFavourites(fav: Favourite) {
        dataRepo.addShipToFavourites(fav)
    }

    fun refreshFavourites() {
        dataRepo.refreshFavourites()
    }

}