import com.example.weatherwise.common.Constants.APPID
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import com.example.weatherwise.data.remote.WeatherApi
import com.example.weatherwise.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val local: WeatherDao
) : WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<CurrentWeatherDataEntity> {
        return try {
            withContext(Dispatchers.IO) {
                val currentWeather = api.getCurrentWeather(lat, lon, APPID)
                local.deleteCurrentWeather()
                local.insertCurrentWeather(currentWeather.apply { last_updated = System.currentTimeMillis() })
                local.getCurrentWeather()
            }
        } catch (e: Exception) {
            println("Error fetching current weather: ${e.message}")
            withContext(Dispatchers.IO) {
                local.getCurrentWeather()
            }
        }

    }

    override suspend fun getForecastWeather(
        lat: Double,
        lon: Double
    ): Flow<ForecastWeatherDataEntity> {
        return try {
            withContext(Dispatchers.IO) {
                val forecastWeather = api.getForecastWeather(lat, lon, APPID)

                val favouriteTimeStamps = local.getFavouriteWeatherDetails().firstOrNull()?.map { it.timeStamp }
                val modifiedWeatherList = forecastWeather.list.mapIndexed { index, forecastWeatherItem ->
                    val isFavourite = favouriteTimeStamps?.contains(forecastWeatherItem.dt_txt) == true
                    forecastWeatherItem.copy(list_id = index, favourite = isFavourite)
                    }

                val modifiedForecastWeather = forecastWeather.copy(list = modifiedWeatherList)
                local.deleteForecastWeather()
                local.insertForecastWeather(modifiedForecastWeather)
                local.getForecastWeather()
            }
        } catch (e: Exception) {
            println("Error fetching forecast weather: ${e.message}")
            withContext(Dispatchers.IO) {
                local.getForecastWeather()
            }
        }
    }

    override suspend fun updateForecastWeatherFavoriteStatus(timeStamp: String) {
        withContext(Dispatchers.IO) {
            var existingEntity = local.getForecastWeatherById()
            val updatedList = existingEntity?.list?.map { forecastWeather ->
                if (forecastWeather.dt_txt == timeStamp) {
                    forecastWeather.copy(favourite = !forecastWeather.favourite)
                } else {
                    forecastWeather
                }
            }
            val updatedEntity = updatedList?.let { existingEntity?.copy(list = it) }
            updatedEntity?.let { local.updateForecastWeather(it) }
        }
    }

    override suspend fun insertFavouriteWeatherDetails(timeStamp: String,lat: Double, lon: Double,temp:Double,weatherKind:String) {
        withContext(Dispatchers.IO) {
            val existingFavTimeStamps = local.getFavouriteWeatherDetails().firstOrNull()?.map { it.timeStamp }
            if(existingFavTimeStamps?.contains(timeStamp) == true){
                local.deleteFavouriteWeatherDetails(timeStamp)
            }else{
                local.insertFavouriteWeatherDetails(FavouriteWeatherDetailsEntity(timeStamp,lat,lon,temp,weatherKind))
            }
        }
    }

    override suspend fun getFavouriteWeatherDetails():Flow<List<FavouriteWeatherDetailsEntity>>{
        return local.getFavouriteWeatherDetails()
    }
    override suspend fun deleteFavouriteWeatherDetails(timeStamp: String){
        withContext(Dispatchers.IO) {
            local.deleteFavouriteWeatherDetails(timeStamp)
        }
    }

}


