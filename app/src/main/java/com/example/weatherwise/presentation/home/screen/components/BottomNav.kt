package com.example.weatherwise.presentation.home.screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherwise.R
import com.example.weatherwise.common.Routes
import com.example.weatherwise.domain.model.BottomNavData
import com.example.weatherwise.presentation.utility.LocationUtils
import com.example.weatherwise.presentation.ui.theme.KamiliMustard

@Composable
fun BottomNav(
    navController: NavController,
    fontName: FontFamily, modifier: Modifier,
) {
    val context = LocalContext.current
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    val items = listOf(
        BottomNavData(title = "Weather", icon = R.drawable.weather),
        BottomNavData(title = "Favourites", icon = R.drawable.favourites),
        BottomNavData(title = "Fav Locations", icon = R.drawable.fav_places),
        BottomNavData(title = "Places", icon = R.drawable.places),
    )


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,

        ) {
        items.forEachIndexed { index, item ->
            BotomNavItem(item, (index == selectedIndex), fontName) {
                selectedIndex = index
                if(LocationUtils.hasLocationPermission(context) && LocationUtils.isLocationEnabled(context)){
                    when(selectedIndex){
                        0->{
                            navController.navigate(Routes.WEATHER_SCREEN)
                        }
                        1->{
                            navController.navigate(Routes.FAVOURITE_WEATHER)
                        }
                        2->{
                            navController.navigate(Routes.MULTIPLE_FAVOURIRE_WEATHER_PLACES)
                        }
                        3->{
                            navController.navigate(Routes.SEARCH_PLACES)
                        }

                    }
                }else{

                    Toast.makeText(context,"Weatherwise requires location permissions",
                        Toast.LENGTH_SHORT).show()
                }



            }
        }

    }

}

@Composable
fun BotomNavItem(
    itemName: BottomNavData,
    isSelected: Boolean,
    fontName: FontFamily,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clickable {
                onItemClick()
            }, horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(30.dp)
                .background(
                    if (isSelected) Color(0xff1e5360) else Color.Transparent,
                    shape = RoundedCornerShape(9.dp)
                )
                .clickable {
                    onItemClick()
                }) {

            Icon(
                painter = painterResource(id = itemName.icon),
                contentDescription = null,
                tint = if (isSelected) KamiliMustard else Color.White,
                modifier = Modifier.size(20.dp)
                // decorative element
            )
        }
        Text(
            text = itemName.title, color = if (isSelected) KamiliMustard else Color.White,
            style = androidx.compose.ui.text.TextStyle(fontFamily = fontName, fontSize = 12.sp)
        )

    }
}