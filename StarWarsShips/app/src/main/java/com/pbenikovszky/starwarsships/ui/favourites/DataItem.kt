package com.pbenikovszky.starwarsships.ui.favourites

import com.pbenikovszky.starwarsships.data.Favourite

sealed class DataItem {

    abstract val id: Int
    abstract val name: String

    data class FavouriteItem(val favourite: Favourite): DataItem() {
        override val id = favourite.shipId
        override val name = favourite.name
    }

    object Header: DataItem() {
        override val id = Int.MIN_VALUE
        override val name = ""
    }
}