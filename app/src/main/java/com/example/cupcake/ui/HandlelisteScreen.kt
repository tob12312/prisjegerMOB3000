package com.example.cupcake.ui

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cupcake.data.HandlelisteData
import com.example.cupcake.data.HandlelisteItems
import com.example.cupcake.data.HandlelisteUiState
import com.example.cupcake.ui.theme.CupcakeTheme

val testendring: String = "dette er test"
val testendring2: String = "dette er test2"

@Composable
fun HandlelisteScreen(
    navn: String,
    handlelisteData: HandlelisteData,
    handleliste: List<HandlelisteItems>,
    prisliste: List<Pair<String, Double>>,
    sum: Double,
    quantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    var handleModus by rememberSaveable { mutableStateOf(true) }
    var grandTotal by rememberSaveable { mutableStateOf(true) }

    if (handleModus) {
        Column {
            HeadingHandleModus(iHandleModus = { handleModus = false }, handlelisteData.sum)
          //  Greetings()
            HandlelisteItems(handlelisteData, onNextButtonClicked)
        }
    } else {
        Column {
            HeadingLagHandleliste(iHandleModus = { handleModus = true }, handlelisteData.sum)
            //  Greetings()
            HandlelisteItems(handlelisteData, onNextButtonClicked)
        }
    }
}

@Composable
fun VelgButikk() {
    val listItems = arrayOf("Rema 1000", "Kiwi", "Meny", "Spar")
    // val disabledItem = 0
    val contextForToast = LocalContext.current.applicationContext
    var tekst by rememberSaveable { mutableStateOf("Rema 1000") }

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {

        // options button
        Button(
            onClick = {
                expanded = true
            }) {
            Text(
                text = tekst
            )
        }

        // drop down menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            listItems.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        Toast.makeText(contextForToast, itemValue, Toast.LENGTH_SHORT)
                            .show()
                        expanded = false
                        tekst = itemValue
                    },
                    //        enabled = (itemIndex != disabledItem)
                ) {
                    Text(text = itemValue)
                }
            }
        }
    }
}

@Composable
private fun HeadingLagHandleliste(iHandleModus: () -> Unit, sum: Double) {
    var grandTotal by remember { mutableStateOf(0) }
    Card(
        backgroundColor = MaterialTheme.colors.onPrimary,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(vertical = 10.dp),
                onClick = iHandleModus
            ) {
                Text("Nå er vi IKKE i handlemodus")
            }
            Column {
                Text(sum.toString())
            }
            Column(
            ) {
                VelgButikk()
            }
        }
    }
}


@Composable
private fun HeadingHandleModus(iHandleModus: () -> Unit, sum: Double) {
    Card(
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(vertical = 10.dp),
                onClick = iHandleModus
            ) {
                Text("Nå er vi i handlemodus")
            }
            Column {
                Text(sum.toString())
            }
            Column {
                VelgButikk()
            }
        }
    }

}





@Composable
private fun HandlelisteItems(handlelisteData : HandlelisteData, onNextButtonClicked : (Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = handlelisteData.handleliste) { handleliste -> // TODO: names sin varenavn, enhetspris
            ListeRamme(handleliste = handleliste, onNextButtonClicked)
        }
    }
}

@Composable
private fun ListeRamme(handleliste: HandlelisteItems, onNextButtonClicked : (Int) -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ListeInnhold(handleliste, onNextButtonClicked)

    }
}

@Composable
private fun ListeInnhold(handleliste: HandlelisteItems, onNextButtonClicked : (Int) -> Unit) {
    var antall by rememberSaveable { mutableStateOf(handleliste.antall) }
    var sumPrVare by rememberSaveable { mutableStateOf(handleliste.sumPrVare) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var varenavn = ""


    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(2.dp)
                .align(Alignment.CenterVertically)
        ) {
            //    Text(text = "Varenavn")
            Text(
                text = handleliste.varenavn,
            )
            if (expanded) {
                //          antall++
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .align(Alignment.CenterVertically)
        ) {
            //    Text(text = "enhetsPris")
            Text(
                text = handleliste.enhetspris.toString(),
            )
        }
        Column( // sumPrVare
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .align(Alignment.CenterVertically)
        ) {
            //     Text(text = "sumPrVare")
            Text(
                text = sumPrVare.toString(),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .align(Alignment.CenterVertically)
        ) {
            Button( // knapp for å trekke fra
                //     onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White),
                onClick = {
                    antall--
                    sumPrVare = antall * handleliste.enhetspris
                }
            ) {
                Text(antall.toString())
            }

        }
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .align(Alignment.CenterVertically)
        ) {
            Button( // knapp for å legge til
                //    onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green,
                    contentColor = Color.White),
                onClick = {
                    antall++
                    sumPrVare = antall * handleliste.enhetspris
                }
            ) {
                Text(antall.toString())
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    CupcakeTheme {
        //ListeItems(handleliste = )
    }
}
