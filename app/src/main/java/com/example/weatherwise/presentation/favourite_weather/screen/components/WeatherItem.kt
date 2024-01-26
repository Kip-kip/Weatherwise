package com.example.weatherwise.presentation.favourite_weather.screen.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherwise.R
import com.example.weatherwise.data.model.ForecastWeather
import com.example.weatherwise.presentation.ui.theme.KamiliDark

@Composable
fun WeatherItem(navController: NavController,weather: ForecastWeather,
                city: String,country:String,lat:Double,lon:Double,
                OnCardClicked:(lat:Double,lon:Double)->Unit) {

    val paddingModifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .background(Color(0XFFFFFFFF))
        .height(200.dp)
        .clickable {
        }

    val fontName = FontFamily(Font(R.font.raleway_regular))

    Card(
        modifier = paddingModifier.pointerInput(Unit) {
            detectTapGestures {
                OnCardClicked(0.5,27.6)
            }
        }
            .graphicsLayer(), shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                modifier = Modifier
                    .padding(10.dp, 10.dp)
                    .clip(CircleShape)
                    .size(100.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.weather),
                contentDescription = ""
            )

            Column() {

                Text(fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = fontName,
                    textAlign = TextAlign.Left,
                    text = "${weather.weather[0].main}",
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                        .align(Alignment.Start))

                Text(fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = fontName,
                    textAlign = TextAlign.Left,
                    text = "Temp: ${weather.main.temp}",
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                        .align(Alignment.Start))

                Spacer(modifier = Modifier.height(5.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp)) {

                    Text(
                        country+", "+city,
                        style = TextStyle(
                            Color(0xff222222),
                            fontSize = 15.sp,
                            fontFamily = fontName,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)

                    )

                    Icon(
                        Icons.Filled.Favorite, "",tint = KamiliDark,modifier = Modifier
                            .padding(end = 10.dp)
                            .size(20.dp))
                }

            }


        }

    }

}