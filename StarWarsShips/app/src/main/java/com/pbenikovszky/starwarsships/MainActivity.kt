package com.pbenikovszky.starwarsships

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.navigation_profile -> {
                    navController.navigate(R.id.navigation_profile)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_ships -> {
                    navController.navigate(R.id.navigation_ships)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_favourites -> {
                    navController.navigate(R.id.navigation_favourites)
                    return@OnNavigationItemSelectedListener true
                }

                else -> return@OnNavigationItemSelectedListener true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}
