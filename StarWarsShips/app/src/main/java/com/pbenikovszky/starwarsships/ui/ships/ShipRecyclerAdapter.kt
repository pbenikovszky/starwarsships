package com.pbenikovszky.starwarsships.ui.ships

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbenikovszky.starwarsships.R
import com.pbenikovszky.starwarsships.data.Ship

class ShipRecyclerAdapter(val context: Context,
                          val ships: List<Ship>,
                          val itemListener: ShipItemListener): RecyclerView.Adapter<ShipRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val nameText = itemView.findViewById<TextView>(R.id.fdNameText)
        val classText = itemView.findViewById<TextView>(R.id.fdClassText)

    }

    interface ShipItemListener {
        fun onShipItemClick(ship: Ship)
    }

    override fun getItemCount() = ships.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ship_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ship = ships[position]
        with(holder) {
            nameText?.let {
                it.text = ship.name
                it.contentDescription = ship.name
            }
            classText?.let {
                it.text = ship.starship_class
                it.contentDescription = ship.starship_class
            }
            holder.itemView.setOnClickListener{
                itemListener.onShipItemClick(ship)
            }
        }
    }

}