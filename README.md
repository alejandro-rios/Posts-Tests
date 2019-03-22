# Posts Test

Posts Test is a sample app that shows a list of posts from [JSONPlaceHolder](https://jsonplaceholder.typicode.com/). The app runs from API 21 and above, just copy the project and run it as you normally run an Android project.

## Proposed Architecture
This app is builded with a MVP Architecture, which is one of the most popular patterns to organize the presentation layer for Android applications.

## Third-party libraries
The app uses this libraries:

  - [Retrofit 2](https://square.github.io/retrofit/): type-safe client for Android and Java, used to make API REST calls.
  - [Guava](https://github.com/google/guava): is a set of libraries from Google that facilitates things like sort an array, filter from a list, Null validations, etc.
  - [ButterKnife](https://github.com/JakeWharton/butterknife): Library that uses annontations to bind fields and methods for android views.
  - [SwipeRevealLayout](https://github.com/chthai64/SwipeRevealLayout): Simple library used to swipe or slide a layout to show a different layout.
  - [Mockito2](https://github.com/mockito/mockito): One of the most popular frameworks to run test on Android.

## Project Structure

```sh
.
├── adapters
├── models
├── service
│   ├── api
│   └── network
├── ui
│   ├── allPosts
│   ├── favoritePosts
│   └── postDetails
└── utils
```

- `. app` main folder of the project.
- `adapters` contains the implementation of the 3 lists used (AllPost, FavoritePosts, Comments).
- `models` POJO files for the API call response.
- `service` API classes for the Retrofit calls.
- `ui` main content folder, where all the magic happens :)
- `utils` Realm configuration class, Manager for Realm queries, Dialog.

### Notes and considerations

  - App logo taken from this source: http://chittagongit.com/icon/posts-icon-13.html
  - Maybe the MVP pasive structure breaks(design not functionality, the app runs ok) because the `FloatingButton` used in `MainActivity.class`.
  
  ![Sample Video](https://youtu.be/JtIgiycjWJ8)