package com.example.cupcake.data


data class HandlelisteItems(
    val varenavn : String,
    val enhetspris : Double,
    val antall : Int,
    val sumPrVare : Double = enhetspris * antall,
 //   var items : List<HandlelisteItems>, // hvordan legger jeg instanser i tabellen?

)




