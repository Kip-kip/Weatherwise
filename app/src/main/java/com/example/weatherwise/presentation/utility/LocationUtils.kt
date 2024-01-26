package com.example.weatherwise.presentation.utility

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.CameraPositionState
import java.util.Locale

object LocationUtils {

    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(context: Context):Boolean{
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return false
    }

     fun initiatePermissionRequestDialog(context: Context){
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1001)
    }
     fun shouldShowRequestPermissionRationale(context: Context, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
    }

     fun showAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

     fun createLocationRequest(context: Context){
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,10000)
            .setMinUpdateIntervalMillis(5000).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest((locationRequest))
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
        }
        task.addOnFailureListener{ e->
            if(e is ResolvableApiException){
                try{
                    e.startResolutionForResult(context as Activity,100)
                } catch (sendEx: java.lang.Exception){

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentActiveCoordinates(context: Context):LatLng{
        var lat = 0.0
        var lon = 0.0
        if (isLocationEnabled(context)){
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            var result = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token)
            result.addOnCompleteListener {
                Toast.makeText(context,"returning ${lat}", Toast.LENGTH_SHORT).show()
                lat = it.result.latitude
                lon = it.result.longitude
            }

            return LatLng(lat,lon)

        }else{
        }

        return LatLng(0.0,0.0)
    }

    fun getLocationDetails(context: Context,lat: Double,lon: Double):String {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getFromLocation(lat, lon, 3)
            if (address != null) {
                return address[0].countryName+","+address[0].locality+", "+address[0].getAddressLine(0)
            }
        }
        catch (e:Exception){

        }
        return ""
    }

    suspend fun CameraPositionState.centerOnLocation(
        location: LatLng
    ) = animate(
        update = CameraUpdateFactory.newLatLngZoom(
            location,
            15f
        )
    )
}
