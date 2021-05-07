package com.example.covidstatsindia

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Locale.filter

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: StatsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.parseColor("#126e82")
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        progressbar.visibility = View.VISIBLE
        mAdapter = StatsAdapter()
        recyclerView.adapter = mAdapter
    }



    private fun fetchData() {
        val url = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                progressbar.visibility = View.GONE
                val regionDataJsonArray = it.getJSONArray("regionData")
                val regionDataArray = ArrayList<CovidData>()
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