package com.example.covidstatsindia

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: StatsAdapter
  private lateinit var regionDataArray: ArrayList<CovidData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.parseColor("#126e82")
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        progressbar.visibility = View.VISIBLE
        mAdapter = StatsAdapter()
        recyclerView.adapter = mAdapter
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionSearch -> {
                    cardView.visibility = View.GONE
                    searchState(it)
                    true
                }
                else -> false
            }
        }
    }

    private fun searchState(it: MenuItem?) {
         val searchView: SearchView = it?.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return false
            }

        })
    }

    private fun filter(newText: String?) {
       val filteredList= ArrayList<CovidData>()
        val i : CovidData
       for(i in regionDataArray){
           if(i.state.toLowerCase().contains(newText?.toLowerCase().toString())){
               filteredList.add(i)
           }
       }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            mAdapter.filterList(filteredList);
        }
    }


    private fun fetchData() {
        val url =
            "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                progressbar.visibility = View.GONE
                //new code start
                val overallActiveCases = it.getInt("activeCases")
                val overallRecoveredCases = it.getInt("recovered")
                val overallDeath = it.getInt("deaths")
                val overallTotalCases = it.getInt("totalCases")
                tv_ovl_activeCases.text = overallActiveCases.toString()
                tv_ovl_recovered_Cases.text = overallRecoveredCases.toString()
                tv_ovl_death_cases.text = overallDeath.toString()
                tv_ovl_total_cases.text = overallTotalCases.toString()
                //new code end
                val regionDataJsonArray = it.getJSONArray("regionData")
                regionDataArray = ArrayList()
                for (i in 0 until regionDataJsonArray.length()) {
                    val dataJsonObject = regionDataJsonArray.getJSONObject(i)
                    val data = CovidData(
                        dataJsonObject.getString("region"),
                        dataJsonObject.getInt("activeCases"),
                        dataJsonObject.getInt("newInfected"),
                        dataJsonObject.getInt("recovered"),
                        dataJsonObject.getInt("newRecovered"),
                        dataJsonObject.getInt("deceased"),
                        dataJsonObject.getInt("newDeceased"),
                        dataJsonObject.getInt("totalInfected"),
                    )
                    regionDataArray.add(data)
                }
                mAdapter.updateNews(regionDataArray)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}