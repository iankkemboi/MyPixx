# MyPixx
Below are my comments on my approach to this interview challenge and tools used.The main approach I used was Android Architecture Components with Kotlin as the development language

# MVVM
The app architecture design pattern I elected to go with was MVVM to enjoy the benefits associated with it.These include:
a) Allows for even easier testability of code in comparison with MVC and MVP
b) The ViewModel allows for more decoupling of business logic away from the View making the View as dumb as possible
c) Google highly recommends it as the proposed architectural pattern for Android hence likely to receive more support from them in the future 
d) This architecture is also very lifecycle aware leading to predictable behavior on activity/app lifecycle changes etc etc
# LiveData
I implemented LiveData to observe underlying changes to the Pixabay API especially when user initiates a new search and use the observed change to update the UI elements accordingly

# Retrofit
A popular networking library that is less verbose,robust and comparatively processes networking requests quickly

# Dagger2 
Used this DI Library to specifically  Retrofit instance to the ViewModel and any other possible ViewModels in the future.Helps in code re-use and code elegance
# Data Binding
Implemented Data Binding to bind all UI components on the app to the data sources in View Model

#RxJava 2
Implemented Reactive Programming using RxJava 2 for certain scenarios such as: Data Operations on RoomDB,networking request to Pixabay
#Room
Implemented Room for offline data persistence of search results of Pixabay.The caching design was to cache only the result for query "fruits" to reduce operations on Room
#UI Design
- Branded the app 'MyPixx'
- Implemented Android Chips on the recyclerview item on each image item 'tags' instead of showing a list,just to give it some nice UI effect in my opinion



