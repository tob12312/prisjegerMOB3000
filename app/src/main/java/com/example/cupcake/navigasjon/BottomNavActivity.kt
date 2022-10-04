package com.example.cupcake.navigasjon

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.HandlelisteScreen
import com.example.cupcake.ui.OrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cupcake.ui.LoginScreen


class BottomNavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenView()
        }
    }
}


@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        NavigationGraph(navController = navController)
    }
}
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Hjem,
        BottomNavItem.Handleliste,
        BottomNavItem.Prissammenligning,
        BottomNavItem.Notification,
        BottomNavItem.Login
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: OrderViewModel = viewModel(),
) {

    val uiState2 by viewModel.uiState2.collectAsState()

    NavHost(navController, startDestination = BottomNavItem.Hjem.screen_route) {
        composable(BottomNavItem.Hjem.screen_route) {
            HomeScreen()
        }


        // FOR HANDLELISTE
        composable(BottomNavItem.Handleliste.screen_route) {
       //     NetworkScreen()
            HandlelisteScreen(
                navn = uiState2.navn,
                handleliste = uiState2.handleliste,
                handlelisteData = uiState2.handlelisteData,
                prisliste = uiState2.prisliste,
                sum = uiState2.sum,
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {
                    viewModel.setHandleliste(uiState2.handleliste)
                }
            )
        }



        composable(BottomNavItem.Prissammenligning.screen_route) {
            AddPostScreen()
        }
        composable(BottomNavItem.Notification.screen_route) {
            NotificationScreen()
        }
        composable(BottomNavItem.Login.screen_route) {
            LoginScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    MainScreenView()
}