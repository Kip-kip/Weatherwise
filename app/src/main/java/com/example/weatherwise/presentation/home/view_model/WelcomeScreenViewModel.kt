package com.example.weatherwise.presentation.home.view_model

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.common.WeatherWiseUiEvents
import com.example.weatherwise.presentation.utility.LocationUtils.createLocationRequest
import com.example.weatherwise.presentation.utility.LocationUtils.hasLocationPermission
import com.example.weatherwise.presentation.utility.LocationUtils.initiatePermissionRequestDialog
import com.example.weatherwise.presentation.utility.LocationUtils.isLocationEnabled
import com.example.weatherwise.presentation.utility.LocationUtils.shouldShowRequestPermissionRationale
import com.example.weatherwise.presentation.utility.LocationUtils.showAppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
):ViewModel() {

    private val _uiEvents = Channel<WeatherWiseUiEvents>()
    val uiEvents = _uiEvents

    fun validatePermissionsAndGPS(context: Context){
        if(!hasLocationPermission(context)){
            val permission = Manifest.permission.ACCESS_FINE_LOCATION
            if(shouldShowRequestPermissionRationale(context,permission)){
                showAppSettings(context)
            }else{
//                Toast.makeText(context,"Please allow location permission",Toast.LENGTH_SHORT).show()
                initiatePermissionRequestDialog(context)
            }
        }
        else if (!isLocationEnabled(context)){
            createLocationRequest(context)
            Toast.makeText(context,"Please turn on your location",Toast.LENGTH_SHORT).show()
        }else{
            sendUiEvent(WeatherWiseUiEvents.OnLocationCheckCleared)
        }
    }



    fun sendUiEvent(event: WeatherWiseUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}