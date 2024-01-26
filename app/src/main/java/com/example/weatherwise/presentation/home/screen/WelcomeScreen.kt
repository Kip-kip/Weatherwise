package com.example.weatherwise.presentation.home.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherwise.R
import com.example.weatherwise.common.Routes
import com.example.weatherwise.common.WeatherWiseUiEvents
import com.example.weatherwise.presentation.home.view_model.WelcomeScreenViewModel
import kotlinx.coroutines.flow.receiveAsFlow


@Composable
fun WelcomeScreen(navController: NavController,onPopBackStack: () -> Unit,
    viewModel: WelcomeScreenViewModel = hiltViewModel()
) {
    val fontName = FontFamily(Font(R.font.raleway_regular))
    val context = LocalContext.current


    LaunchedEffect(key1 = true){
        viewModel.uiEvents.receiveAsFlow().collect(){
            when(it){
                is WeatherWiseUiEvents.OnLocationCheckCleared->{
                    navController.navigate(Routes.WEATHER_SCREEN)
                }
                else -> {}
            }
        }
    }


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(200.dp))
            Text(fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                fontFamily = fontName,
                textAlign = TextAlign.Center,
                text = "Welcome to Weatherwise. Click button below to explore our app",
                modifier = Modifier.padding(start = 10.dp, end = 10.dp))
            Button(onClick = {
                viewModel.validatePermissionsAndGPS(context)
            },modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = fontName,
                    text = "What's it like outside?",
                    )

        }



    }
}