
# Weather Checker
Weather Checker is a basic Android application made in Kotlin whose main feature is to check the current forecast for any city in the world.

![The forecast view.](https://github.com/danfelix-dev/weather-checker/blob/master/screenshots/forecast-portrait.png?raw=true)

## Try it out!
In case you want to try Weather Checker out of the box, you can download the latest apk from [Firebase's App Distribution](https://appdistribution.firebase.dev/i/zDeoqfCn).  If, instead, you want to compile the project yourself, keep reading.

## Running the project

### Prerequisites
- [The Maps SDK For Android 3.0](https://developers.google.com/maps/documentation/android-sdk/v3-client-migration)
- [A valid Maps API key](https://developers.google.com/maps/documentation/android-sdk/get-api-key#get-the-api-key)
- [A valid OpenWeather API key](https://openweathermap.org/appid)

### Putting everything in place
1. Either clone or download the repository.
2. Move the `maps-sdk-3.0.0-beta.aar` file previously downloaded into `app/libs/`.
3. On `app/src/main/AndroidManifest.xml`, replace the value for `com.google.android.geo.API_KEY` with the Maps API Key previously obtained.
4. On `app/src/main/java/com/zshock/weatherchecker/data/network/Api.kt` replace the `API_KEY` var value with the OpenWeather API key previously obtained.
5. And that's it! Press play or debug at your heart's content.

## How to use it

### Getting a city's forecast
1. Start by typing the city's name on the search bar
2. When you find the city corresponding to its country code, click it
3. The city's forecast will be downloaded and shown on the screen

## Features
- Material Day/Night support
- History section for previously queried cities.
- Settings section where you can select the unit used to show the temperature (K, °C, °F).
- Portrait/landscape support for the forecast detail.
- Empty state views for when the network query goes wrong.
