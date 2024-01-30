This is a realtime weather app fetching both current and forecast weather data from https://api.openweathermap.org

The app has been developed following MVVM and clean architectural principles to ensure improved maintainability, scalability, and testability.
It facilitates the separation of concerns, making it easier to understand, extend, and modify the codebase over time.

The following are third party libraries used;
1. Retrofit - responsible for initiating and managing network requests.
2. Dagger Hilt - responsible for managing and facilitating dependency injection.
3. Room - responsible for facilitating local database interractions.
4. Truth - responsible for assertions in tests.

Additional libraries;
1. Google places - facilitates the exploration and search for locations on maps.
2. Google play services location - responsible for handing map-related activities.

Requirements;
1. In order to access google map features ensure you have a secure.properties file in the root folder of the project alongside the app folder. This file should contain your Google Cloud API Key 


