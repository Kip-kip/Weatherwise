package com.example.weatherwise.presentation.favourite_weather.screen.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherwise.R
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.data.model.ForecastWeather
import com.example.weatherwise.presentation.ui.theme.KamiliDark
import com.example.weatherwise.presentation.utility.LocationUtils.getLocationDetails

@Composable
fun WeatherItem(weather: FavouriteWeatherDetailsEntity,
                OnCardClicked:(lat:Double,lon:Double)->Unit,OnDeleteFavourite:(timeStamp:String)->Unit) {

    val paddingModifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .background(Color(0XFFFFFFFF))
        .height(230.dp)

    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current

    Card(
        modifier = paddingModifier, shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp, end = 5.dp)) {

                Text(
                    getLocationDetails(context,weather.lat,weather.lon),
                    style = TextStyle(
                        Color(0xff222222),
                        fontSize = 13.sp,
                        fontFamily = fontName,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp, end = 20.dp)

                )
            }
            Image(
                modifier = Modifier
                    .padding(10.dp, 10.dp)
                    .clip(CircleShape)
                    .size(100.dp).pointerInput(Unit) {
                        detectTapGestures {
                            OnCardClicked(weather.lat,weather.lon)
                        }
                    }
                    .graphicsLayer(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.weather),
                contentDescription = ""
            )

            Column() {

                Text(fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = fontName,
                    textAlign = TextAlign.Left,
                    text = "${weather.weatherKind}",
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                        .align(Alignment.Start))

                Text(fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = fontName,
                    textAlign = TextAlign.Left,
                    text = "Temp: ${weather.temp}",
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                        .align(Alignment.Start))

                Spacer(modifier = Modifier.height(5.dp))


                Row(horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 0.dp, end = 5.dp)) {

                    Text(
                        weather.timeStamp,
                        style = TextStyle(
                            Color(0xff222222),
                            fontSize = 13.sp,
                            fontFamily = fontName,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp).weight(3f)

                    )

                    Icon(
                        Icons.Filled.Delete, "",tint = KamiliDark,modifier = Modifier
                            .padding(end = 10.dp).weight(1f)
                            .size(20.dp).pointerInput(Unit) {
                                detectTapGestures {
                                    OnDeleteFavourite(weather.timeStamp)
                                }
                            }
                            .graphicsLayer())
                }



            }


        }

    }

}