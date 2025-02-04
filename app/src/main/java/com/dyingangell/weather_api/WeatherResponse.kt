data class WeatherResponse(
    val location: Location,
    val current: CurrentWeather
)

data class Location(
    val name: String,
    val region: String,
    val country: String
)

data class CurrentWeather(
    val temp_c: Double,  // Температура в Цельсиях
    val condition: WeatherCondition,
    val wind_kph: Double,
    val feelslike_c: Double,
    val wind_dir: String,
    val last_updated: String,
)

data class WeatherCondition(
    val text: String,  // Описание состояния погоды (например, "Clear", "Rain", и т.д.)
    val icon: String
)