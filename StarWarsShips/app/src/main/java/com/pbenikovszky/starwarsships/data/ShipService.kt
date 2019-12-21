package com.pbenikovszky.starwarsships.data

import com.pbenikovszky.starwarsships.WEB_SERVICE_PATH
import retrofit2.Response
import retrofit2.http.GET

interface ShipService {
    @GET(WEB_SERVICE_PATH)
    suspend fun getShipData(): Response<List<Ship>>
}