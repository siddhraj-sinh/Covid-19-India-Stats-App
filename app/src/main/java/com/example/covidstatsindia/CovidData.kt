package com.example.covidstatsindia

data class CovidData(
    val state: String,
    val activeCases: Int,
    val newInfectedCases: Int,
    val totalRecovered: Int,
    val newRecovered: Int,
    val totalDeath: Int,
    val newDeath: Int,
    val totalInfected: Int
)