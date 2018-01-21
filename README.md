# ArcMovies

This is my submission to the ArcTouch Code Challenge.

ArcMovies shows a list of Upcoming Movies in a selected region with details such as movie title, release date, genre and overview. 

It was build with Clean Architecture Principals in mind. 

## Dependencies

Platform
 - Android SDK 26 (minimum Android version: 21)
 - Kotlin v1.+ (built with v1.1.20)
 - Gradle 3.+ (built with 3.0.1)
 
Libraries:
 - **RxJava 2.1.8:** for using the observer pattern on network calls and Use Cases
 - **RxAndroid 2.0.1**: for the Android Schedulers to observe/subscribe streams on appropriate threads
 - **Retrofit 2.3.0**: for making network calls to the API
 - **Retrofit Gson Converter**: for deserializing JSON objects returned by the API
 - **Retrofit RxJava2 Adapter**: for getting network calls as Observers
 - **Picasso 2.5.2**: for loading images from the network
 - **Picasso Transformations 2.1.2**: for creating the vignette transformation on the backdrop images

## Build

Build using Gradle with command

```
./gradlew assembleDebug
```


Or install on device with:

```
./gradlew installDebug
```

Or open project on Android Studio and run
