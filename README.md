# RestaurantsAround
Displays restaurants around a location

## Architecture pattern followed
* This projects follows MVP architecture. This enables unit testing the code with ease
* The project has used Android's architectural components like ViewModel to support orientation change
 Hence on orientation change, there is no need to reload the data again from the server
* Dagger 2.11 is used for dependency injection.
* Retrofit with RxJava support is used for Networking
* The app is developed in Kotlin

## Testing
* Mockito and Robolectric are used for Unit testing
* Espresso is used to write UI tests. 
