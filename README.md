# RoadCastAssignment

This is a two screen app which show your current location and top rated movies respectively.

## Features

1. Track your current location using GPS on the Map.
2. Handling of location permission and GPS setting efficiently.
3. Explore top rated Movies.
4. Single Activity Architecture and uses Jetpack navigation to navigate
   through destination (fragments).
5. Retrofit to do api calls and gson to parse response.
6. Paging3 to load only required Movies and for infinite scroll.
7. Hilt for dependency injection.

|                                                                                                               |                                                                                                                 |
|---------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| ![home](https://github.com/sDevPrem/RoadCastAssignment/assets/130966261/db118399-005d-4994-bc69-7fc427993d01) | ![movies](https://github.com/sDevPrem/RoadCastAssignment/assets/130966261/9ce6d9fd-0c78-4c14-a0a3-d3968bf2aedb) |

https://github-production-user-asset-6210df.s3.amazonaws.com/130966261/330739656-3369a578-3d0f-40f2-964f-0504ed265985.mp4

## TODO

* Show a progress at bottom when appending pages in movies screen.
* Show a Error message and give the user a button to retry.

## Architecture

This app follows MVVM architecture with Clean architecture, Uni Directional Flow (UDF) pattern
and Single Activity architecture pattern.

### Packages

* [`:data:`](app/src/main/java/com/sdevprem/roadcastassignment/data) -
  The data origin point.
* [`:di:`](app/src/main/java/com/sdevprem/roadcastassignment/di) -
  Hilt modules.
* [`:presentation:`](app/src/main/java/com/sdevprem/roadcastassignment/presentation) -
  All the Screens UI lies here in subpackages.

## Build With

[Kotlin](https://kotlinlang.org/):
As the programming language.

[Jetpack Navigation](https://developer.android.com/guide/navigation) :
To navigate between destinations(fragments).

[Google Maps API](https://developers.google.com/maps/documentation/android-sdk) :
To track user's location on the Map.

[Hilt](https://developer.android.com/training/dependency-injection/hilt-android) :
As a dependency injector.

[Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview):
For infinite scrolling.

[Retrofit](https://square.github.io/retrofit):
As Api Client.

[Coroutines](https://developer.android.com/kotlin/coroutines):
For Asynchronous programming.

## Installation

Simple clone this app and open in Android Studio.

### Google Map Integration

Do these steps if you want to show google maps. The tracking
functionalities will work as usual even if you don't do
these step.

1. Create and Get Google Maps API key by using this official
   [guide](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
2. Open `local.properties` file.
3. Enter your API key like this:

```
MAPS_API_KEY=your_maps_api_key
```

