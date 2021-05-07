package com.example.covidstatsindia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class StatsAdapter() : RecyclerView.Adapter<StatsViewHolder>() {
    val items: ArrayList<CovidData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_stats_view, parent, false)
        return StatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.state.text = currentItem.state
        holder.activeCases.text = currentItem.activeCases.toString()
        holder.newInfectedCases.text = currentItem.newInfectedCases.toString()
        holder.totalRecovered.text = currentItem.totalRecovered.toString()
        holder.newRecovered.text = currentItem.newRecovered.toString()
        holder.totalDeath.text = currentItem.totalDeath.toString()
        holder.newDeath.text = currentItem.newDeath.toString()
        holder.totalInfected.text = currentItem.totalInfected.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateNews(updatedItems: ArrayList<CovidData>) {
        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()
    }

}

class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val state: TextView = itemView.findViewById(R.id.tv_state)
    val activeCases: TextView = itemView.findViewById(R.id.tv_active_cases)
    val newInfectedCases: TextView = itemView.findViewById(R.id.tv_new_infected_cases)
    val totalRecovered: TextView = itemView.findViewById(R.id.tv_total_recovered)
    val newRecovered: TextView = itemView.findViewById(R.id.tv_new_recovered)
    val totalDeath: TextView = itemView.findViewById(R.id.tv_total_death)
    val newDeath: TextView = itemView.findViewById(R.id.tv_new_death)
    val totalInfected: TextView = itemView.findViewById(R.id.tv_total_infected)
}