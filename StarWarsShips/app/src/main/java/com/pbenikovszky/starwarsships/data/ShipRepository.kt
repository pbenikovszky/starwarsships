package com.pbenikovszky.starwarsships.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.pbenikovszky.starwarsships.LOG_TAG
import com.pbenikovszky.starwarsships.R
import com.pbenikovszky.starwarsships.WEB_SERVICE_URL
import com.pbenikovszky.starwarsships.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ShipRepository(val app: Application) {

    val shipData = MutableLiveData<List<Ship>>()
    val favData = MutableLiveData<List<Favourite>>()

    private val shipListType = Types.newParameterizedType(
        List::class.java, Ship::class.java
    )

    private val shipDao = ShipDatabase.getDatabase(app).shipDAO()

    init {

        CoroutineScope(Dispatchers.IO).launch {
            val data = shipDao.getAllShips()
            if (data.isEmpty()) {
                if (networkAvailable()) {
                    callWebService()
                } else {
                    withContext(Dispatchers.Main) {
                        getShipDataFromLocalFiles()
                    }
                }
            } else {
                shipData.postValue(data)
                Log.i(LOG_TAG, "Get shipdata from DB")
            }

            val favouritesData = shipDao.getAllFavourites()

            if (favouritesData.isNotEmpty()) {
                favData.postValue(favouritesData)
                Log.i(LOG_TAG, "Get favdata from DB")

            } else {
                favData.postValue(emptyList())
            }


        }

    }

    @WorkerThread
    suspend fun callWebService() {

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(WEB_SERVICE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val service = retrofit.create(ShipService::class.java)
        val serviceData = service.getShipData().body() ?: emptyList()
        shipData.postValue(serviceData)
        shipDao.deleteAllShips()
        shipDao.insertShips(serviceData)
        Log.i(LOG_TAG, "Get data from Web service")

    }

    private fun getShipDataFromLocalFiles() {

        val shipText = FileHelper.getTextFromResourceFile(app, R.raw.ships)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<List<Ship>> = moshi.adapter(shipListType)
        shipData.value = adapter.fromJson(shipText) ?: emptyList()
        Log.i(LOG_TAG, "Get data from local file")
        CoroutineScope(Dispatchers.IO).launch {
            shipDao.deleteAllShips()
            shipDao.insertShips(shipData.value ?: emptyList())
        }

    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun refreshData() {

        if (networkAvailable()) {
            CoroutineScope(Dispatchers.IO).launch {
                callWebService()
            }
        } else {
            getShipDataFromLocalFiles()
        }

    }

    fun addShipToFavourites(fav: Favourite) {
        CoroutineScope(Dispatchers.IO).launch {
            val favData = shipDao.getFavouriteByName(fav.name)
            if (favData == null) {
                shipDao.insertFavourite(fav)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "${fav.name} is already in the database", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    fun refreshFavourites() {
        CoroutineScope(Dispatchers.IO).launch {

            val favouritesData = shipDao.getAllFavourites()

            if (favouritesData.isNotEmpty()) {
                favData.postValue(favouritesData)
                Log.i(LOG_TAG, "Get favdata from DB")

            } else {
                favData.postValue(emptyList())
            }

        }
    }

}