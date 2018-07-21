# Work Hours Statistics

This project is Android application for work hours statistics.  
The application contain two screens:

  - Configuration screen - the user can enter his work address.
  - Statics screen - list of working days with the duration.

# Architecture:
The application develop base MVVM design pattern as described below:

![alt text](https://codelabs.developers.google.com/codelabs/android-room-with-a-view/img/3840395bfb3980b8.png)


### Tech
  - Google Place Autocomplete API via PlacesAutocompleteTextView - Give the user nice user experience for enter his work address.
  - Google Geofence API - After the user enter his working address, the application register for get notify when the device is get into or leave the work location.

  - Dagger - The application use compile-time dependency injection for the storage components, like DB and Shared preference. 
  That allow:
    - Easy instances creation management.
    - Test or logic with mock storge.


  - Android Architecture Components:
    - ViewModel - for save the activity state across configuration changes.
    - LiveData - For connect the DB and the view, any change in the DB automatic reflect in the UI.
    - Room Persistence Library - The application store user arrival and leaving time in Room DB.
    
  - Apache Commons Lang - The application convert long parameter into nice ui format using DurationFormatUtils. (e.g: ‘8 Hours 1 Minutes 50 Seconds’).
  - Robolectric - Allow to test android components like Service, Activity and Etc on the local JVM. So, we test our Geofence service logic.
  - Recyclerview - For display the working hours statics.
 

### Configuration:
The application use productFlavors for separate the Demo running configuration to production. For example in demo mode the ‘geofence loitering delay’ parameter in a second and in production is a five minutes. The application also use variantFilter to avoid accident of release application to the store in demo mode.

### Demo:
The project contain two GPX file in the root folder, this files declare round routes via Google Maps. So you can get a real demo in you PC by load this files in the emulator.

### Quick Start:
After clone the repository set in gradle.properties the field GoogleMapsApiKey.

### Known issue:
[Geofence isn't triggered until another app uses GPS](https://github.com/googlesamples/android-play-location/issues/124)

This project build as home assignment as part of work acceptance at [Bringg](https://www.bringg.com/).
