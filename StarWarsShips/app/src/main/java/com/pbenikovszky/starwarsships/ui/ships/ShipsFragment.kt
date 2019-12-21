package com.pbenikovszky.starwarsships.ui.ships

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pbenikovszky.starwarsships.LOG_TAG
import com.pbenikovszky.starwarsships.R
import com.pbenikovszky.starwarsships.data.Ship
import com.pbenikovszky.starwarsships.ui.shared.SharedViewModel
import com.pbenikovszky.starwarsships.utilities.MarginItemDecoration

class ShipsFragment : Fragment(), ShipRecyclerAdapter.ShipItemListener {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        val root = inflater.inflate(R.layout.fragment_ships, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        swipeLayout =  root.findViewById(R.id.swipeLayout)
        navController = Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment
        )

        sharedViewModel =
            ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        swipeLayout.setOnRefreshListener {
            sharedViewModel.refreshData()
        }

        sharedViewModel.shipData.observe(this, Observer {
            val adapter = ShipRecyclerAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })

        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.fav_item_padding).toInt()
            )
        )

        return root

    }

    override fun onShipItemClick(ship: Ship) {
        Log.i(LOG_TAG, "Selected ship: ${ship.name}")
        sharedViewModel.selectedShip.value = ship
        navController.navigate(R.id.action_nav_to_detail)
    }
}