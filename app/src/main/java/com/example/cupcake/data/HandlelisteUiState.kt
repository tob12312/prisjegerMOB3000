package com.example.cupcake.data

data class HandlelisteUiState(
    val navn: String = "",
    val handleliste: List<HandlelisteItems>,
    val handlelisteData: HandlelisteData,
    val prisliste: List<Pair<String, Double>>,
    val sum: Double = handlelisteData.sum,
    val antall : Int = 0,
)

fun oppdaterSumUI(handleliste : List<HandlelisteItems>): Double {
    var nySum = 0.0
    handleliste.forEach {e -> nySum += 1}
    return nySum
}
