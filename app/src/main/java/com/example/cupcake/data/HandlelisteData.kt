package com.example.cupcake.data

import androidx.compose.runtime.mutableStateListOf
import com.example.cupcake.navigasjon.BottomNavItem


data class HandlelisteData(
    // val bruker : Bruker,
    val navn: String,
    val handleliste : List<HandlelisteItems> = mutableStateListOf<HandlelisteItems>(),
    val sum: Double = oppdaterSum(handleliste), // burde aggregert grandTotal av alle rader i handleliste
)

fun oppdaterSum(handleliste : List<HandlelisteItems>): Double {
    var nySum = 0.0
    handleliste.forEach {e -> nySum += 1}
    return nySum
}
