package com.example.cupcake.navigasjon

import com.example.cupcake.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Hjem : BottomNavItem("Hjem", R.drawable.ic_home,"hjem")
    object Handleliste: BottomNavItem("Handleliste",R.drawable.ic_my_network,"my_network")
    object Prissammenligning: BottomNavItem("Post",R.drawable.ic_post,"add_post")
    object Notification: BottomNavItem("Notification",R.drawable.ic_notification,"notification")
    object Login: BottomNavItem("Login",R.drawable.ic_launcher_foreground,"Login")
}
