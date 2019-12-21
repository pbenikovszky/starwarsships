package com.pbenikovszky.starwarsships.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pbenikovszky.starwarsships.LOG_TAG
import com.pbenikovszky.starwarsships.R
import com.pbenikovszky.starwarsships.data.Favourite
import com.pbenikovszky.starwarsships.data.Ship
import com.pbenikovszky.starwarsships.databinding.FragmentDetailBinding
import com.pbenikovszky.starwarsships.ui.shared.SharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var navController: NavController
    private lateinit var selectedShip: Ship

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setHasOptionsMenu(true)

        sharedViewModel =
            ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment
        )

        sharedViewModel.selectedShip.observe(this, Observer {
            selectedShip = it
        })



        val binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )

        binding.addToFavsButton.setOnClickListener {
            sharedViewModel.addShipToFavourites(Favourite(
                selectedShip.shipId,
                selectedShip.name,
                selectedShip.model,
                selectedShip.manufacturer,
                selectedShip.cost_in_credits,
                selectedShip.length,
                selectedShip.max_atmosphering_speed,
                selectedShip.crew,
                selectedShip.passengers,
                selectedShip.cargo_capacity,
                selectedShip.consumables,
                selectedShip.hyperdrive_rating,
                selectedShip.MGLT,
                selectedShip.starship_class
            ))
            Thread.sleep(100)
            sharedViewModel.refreshFavourites()
            Log.i(LOG_TAG, "Click on ship ${selectedShip.name}")
        }

        binding.lifecycleOwner = this

        binding.viewModel = sharedViewModel

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }


}
