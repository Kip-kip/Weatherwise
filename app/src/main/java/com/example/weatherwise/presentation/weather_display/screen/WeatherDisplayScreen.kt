package com.example.weatherwise.presentation.weather_display.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.common.LoadingEffect
import com.example.weatherwise.common.WeatherWiseUiEvents
import com.example.weatherwise.presentation.utility.Utils.convertDateStringToDayAndTime
import com.example.weatherwise.presentation.weather_display.events.WeatherDisplayEvents
import com.example.weatherwise.presentation.weather_display.view_model.WeatherDisplayViewModel
import com.example.weatherwise.presentation.ui.theme.cloudy
import com.example.weatherwise.presentation.ui.theme.rainy
import com.example.weatherwise.presentation.ui.theme.sunny
import com.example.weatherwise.presentation.utility.Utils.convertLongTimeStampToDayAndTime
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun WeatherDisplayScreen (onPopBackStack: () -> Unit,viewModel: WeatherDisplayViewModel = hiltViewModel()) {

    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getLocationCurrentCoordinates(context)
    }

    val weatherKind = viewModel.state.value.currentWeather?.weather?.firstOrNull()?.main
    val tempMin = viewModel.state.value.currentWeather?.main?.temp_min
    val temp = viewModel.state.value.currentWeather?.main?.temp
    val tempMax = viewModel.state.value.currentWeather?.main?.temp_max

    val backgroundColor = when (weatherKind) {
        "Clear" -> sunny
        "Clouds" -> cloudy
        "Rain" -> rainy
        else -> cloudy
    }
    val backgroundImage = when (weatherKind) {
        "Clear" -> R.drawable.forest_sunny
        "Clouds" -> R.drawable.forest_cloudy
        "Rain" -> R.drawable.forest_rainy
        else -> R.drawable.solid_cloudy_image
    }

    fun getWeatherIconResource(weatherKind: String): Int {
        return when (weatherKind) {
            "Clear" -> R.drawable.clear3x
            "Clouds" -> R.drawable.partlysunny3x
            "Rain" -> R.drawable.rain3x
            else -> R.drawable.partlysunny3x
        }
    }

    LaunchedEffect(key1 = true){
        viewModel.uiEvents.receiveAsFlow().collect(){
            when(it){
                is WeatherWiseUiEvents.ShowSnackbar->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        if(viewModel.state.value.currentWeatherIsLoading){
            Column(modifier = Modifier.padding(top=100.dp)) {
                LoadingEffect()
            }
        }
        Column(
            Modifier
                .height(350.dp)
        ) {
            Box(
                modifier = Modifier
            ) {

                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )

                Column(
                    Modifier
                        .height(420.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 10.dp)
                    ) {

                        Text(
                            if (temp!=null) "${viewModel.myCountry.value+", "+viewModel.myCity.value+", "+viewModel.myAddress.value}" else "",
                            style = TextStyle(
                                Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp,start = 20.dp, end = 20.dp)
                        )

                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 10.dp)
                    ) {

                        Text(
                            if(temp!=null) "${temp}\u00B0" else "",
                            style = TextStyle(
                                Color.White,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp, end = 20.dp)
                        )

                    }

                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, end = 10.dp)) {
                        Text(
                            if (temp!=null) "${viewModel.state.value.currentWeather?.weather?.get(0)?.main}" else "",
                            style = TextStyle(
                                Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp, end = 0.dp)
                        )

                    }

                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, end = 10.dp)) {
                        Text(
                            if (temp!=null) "Last updated: ${viewModel.state.value.currentWeather?.let {
                                convertLongTimeStampToDayAndTime(
                                    it.last_updated)
                            }}" else "",
                            style = TextStyle(
                                Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp, end = 0.dp)
                        )

                    }



                }


            }

        }

        Column(modifier = Modifier
            .fillMaxSize()) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, end = 0.dp)
            ) {

                Column() {
                    Text(
                        if(tempMin!=null) "${tempMin}\u00B0" else "" ,
                        style = TextStyle(
                            Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W800,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp,start = 20.dp)
                    )
                    Text(
                        if(tempMin!=null) "Min" else "" ,
                        style = TextStyle(
                            Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W800,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(top = 5.dp,start = 20.dp)
                    )
                }
                Column() {
                    Text(
                        if(temp!=null) "${temp}\u00B0" else "" ,
                        style = TextStyle(
                            Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W800,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp,start = 20.dp)
                    )
                    Text(
                        if(temp!=null) "Current" else "" ,
                        style = TextStyle(
                            Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W800,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(top = 5.dp,start = 20.dp)
                    )
                }
                Column() {
                    Text(
                        if(tempMax!=null) "${tempMax}\u00B0" else "",
                        style = TextStyle(
                            Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W800,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp,start = 20.dp, end = 20.dp)
                    )
                    Text(
                        if(tempMax!=null) "Max" else "",
                        style = TextStyle(
                            Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W800,
                            fontFamily = fontName
                        ),
                        modifier = Modifier
                            .padding(top = 5.dp,start = 20.dp)
                    )
                }

            }

            if (temp!=null) Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if(viewModel.state.value.forecastWeatherIsLoading){
                Column() {
                    LoadingEffect()
                }
            }
            LazyColumn{
                items(viewModel.state.value.forecastWeather?.list ?: emptyList()){ forecastItem->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, end = 0.dp)
                    ) {

                        Text(
                            "${convertDateStringToDayAndTime(forecastItem.dt_txt)}",
                            style = TextStyle(
                                Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp,start = 20.dp)
                        )

                        Icon(
                            painter = painterResource(getWeatherIconResource(forecastItem.weather.firstOrNull()?.main.toString())),
                            contentDescription = "Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(30.dp)
                        )
                        Text(
                            "${forecastItem.main.temp}",
                            style = TextStyle(
                                Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = fontName
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp,start = 20.dp, end = 20.dp)
                        )

                        Column(modifier =   Modifier.padding(top = 10.dp,start = 20.dp, end = 20.dp)) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = if (forecastItem.favourite) Color.Red else Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            viewModel.receiveUIEvents(
                                                WeatherDisplayEvents.OnMarkAsFavourite(
                                                    forecastItem.dt_txt,
                                                    viewModel.state.value.forecastWeather?.city?.coord?.lat,
                                                    viewModel.state.value.forecastWeather?.city?.coord?.lon,
                                                    forecastItem.main.temp,
                                                    forecastItem.weather[0].main,
                                                )

                                            )
                                        }
                                    }
                                    .graphicsLayer()
                            )

                        }

                    }
                }
            }



            Spacer(modifier = Modifier.height(10.dp))



        }


    }
}