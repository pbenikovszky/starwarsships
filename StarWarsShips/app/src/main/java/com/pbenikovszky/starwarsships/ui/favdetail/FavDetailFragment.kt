package com.pbenikovszky.starwarsships.ui.favdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pbenikovszky.starwarsships.R

/**
 * A simple [Fragment] subclass.
 */
class FavDetailFragment : Fragment() {

    private var name: String? = null
    private var model: String? = null
    private var manufacturer: String? = null
    private var starshipClass: String? = null
    private var cost: String? = null
    private var crew: String? = null
    private var hyperdrive: String? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args =
                FavDetailFragmentArgs.fromBundle(
                    it
                )
            name = args.paramName
            model = args.paramModel
            manufacturer = args.paramManufacturer
            starshipClass = args.paramClass
            cost = args.paramCost
            crew = args.paramCrew
            hyperdrive = args.paramHyperdrive

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setHasOptionsMenu(true)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment
        )

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_fav_detail, container, false)

        root.findViewById<TextView>(R.id.fdNameText).text = name
        root.findViewById<TextView>(R.id.fdModelText).text = model
        root.findViewById<TextView>(R.id.fdManufacturerText).text = manufacturer
        root.findViewById<TextView>(R.id.fdClassText).text = starshipClass
        root.findViewById<TextView>(R.id.fdCostText).text = cost
        root.findViewById<TextView>(R.id.fdCrewText).text = crew
        root.findViewById<TextView>(R.id.fdHyperdriveText).text = hyperdrive

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }


}
