package com.pbenikovszky.starwarsships.ui.favourites

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pbenikovszky.starwarsships.LOG_TAG
import com.pbenikovszky.starwarsships.R
import com.pbenikovszky.starwarsships.data.Favourite
import com.pbenikovszky.starwarsships.ui.shared.SharedViewModel
import com.pbenikovszky.starwarsships.utilities.MarginItemDecoration

class FavouritesFragment : Fragment(), FavouritesRecyclerAdapter.FavouriteItemListener {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private var favList: List<Favourite> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerView = root.findViewById(R.id.favRecyclerView)

        sharedViewModel =
            ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment
        )

        val adapter = FavouritesRecyclerAdapter(this)
        recyclerView.adapter = adapter

        sharedViewModel.favData.observe(this, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 2
                else -> 1
            }
        }

        recyclerView.layoutManager = manager
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.fav_item_padding).toInt()
            )
        )
        return root
    }

    override fun onFavouriteItemClick(favourite: Favourite) {

        Log.i(LOG_TAG, "Selected ship: ${favourite.name}")
        val action = FavouritesFragmentDirections.actionNavToFavDetails()
        action.setParamManufacturer(favourite.manufacturer)
        action.setParamCrew(favourite.crew)
        action.setParamName(favourite.name)
        action.setParamHyperdrive(favourite.hyperdrive_rating)
        action.setParamClass(favourite.starship_class)
        action.setParamModel(favourite.model)
        action.setParamCost(favourite.cost_in_credits)
        navController.navigate(action)
    }
}