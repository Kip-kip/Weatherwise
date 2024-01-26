import com.example.weatherwise.common.Constants.APPID
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteLocationEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import com.example.weatherwise.data.remote.WeatherApi
import com.example.weatherwise.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
                local.insertCurrentWeather(currentWeather)
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

                val modifiedWeatherList =
                    forecastWeather.list.mapIndexed { index, forecastWeatherItem ->
                        forecastWeatherItem.copy(list_id = index)
                    }
                val modifiedForecastWeather = forecastWeather.copy(list = modifiedWeatherList)
                local.insertForecastWeather(modifiedForecastWeather)
                local.getForecastWeather()
            }
        } catch (e: Exception) {
            println("Error fetching current weather: ${e.message}")
            withContext(Dispatchers.IO) {
                local.getForecastWeather()
            }
        }
    }

    override suspend fun updateForecastWeatherFavoriteStatus(listId: Int) {
        withContext(Dispatchers.IO) {
            var existingEntity = local.getForecastWeatherById()

            val updatedList = existingEntity?.list?.map { forecastWeather ->
                if (forecastWeather.list_id == listId) {
                    forecastWeather.copy(favourite = !forecastWeather.favourite)
                } else {
                    forecastWeather
                }
            }
            val updatedEntity = updatedList?.let { existingEntity?.copy(list = it) }
            updatedEntity?.let { local.updateForecastWeather(it) }
        }
    }

    override suspend fun insertFavouritePlace(lat: Double, lon: Double) {
        withContext(Dispatchers.IO) {
            local.insertFavouriteLocation(FavouriteLocationEntity(lat, lon))
        }
    }

}


