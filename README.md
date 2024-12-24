# Crypto Tracker - Android App

This repository contains the source code for Crypto Tracker, an Android app that allows users to track the prices and market information of over 1,000 cryptocurrencies. It provides real-time data, historical price charts, and detailed information for each coin.

## Key Features

-   **Extensive Coin List:** Displays a comprehensive list of over 1,000 cryptocurrencies.
-   **Real-Time Data:** Provides up-to-date pricing and market activity for each coin.
-   **Detailed Coin Information:** Shows key metrics like market capitalization, price, and 24-hour price change.
-   **Historical Price Chart:** Includes a custom line chart displaying the price history of each coin over the past 6 hours.
-   **Responsive Design:** Adapts to different screen sizes, including tablets, providing an optimal viewing experience.
-   **Adaptive Navigation:** Utilizes Material 3 Adaptive Navigation to display a two-column layout on larger screens when viewing coin details.
-   **Dark/Light Theme:** Supports both dark and light themes, automatically adjusting based on the device's settings.

## Technologies and Patterns

-   **Kotlin:** The primary programming language used for the app.
-   **Jetpack Compose:** Modern UI toolkit for building native Android UIs.
-   **MVI (Model-View-Intent):** Follows the MVI architectural pattern for a unidirectional data flow and clear separation of concerns.
-   **Clean Architecture:** Implements Clean Architecture principles for a scalable, testable, and maintainable codebase.
-   **Ktor:** Used for making network requests to fetch data from the CoinCap API.
-   **Koin:** Used for dependency injection, simplifying the management of dependencies.
-   **Material 3:** Implements Material Design 3 components for a modern and consistent UI.
-   **Material 3 Adaptive Navigation:** Uses `material3-adaptive` to create responsive layouts that adapt to different screen sizes, including a two-column layout for coin details on larger screens.
-   **Material Icons:** Used to display icons throughout the app.
-   **Coroutines:** Utilizes Kotlin Coroutines for asynchronous programming, ensuring a smooth user experience.
-   **Dark and Light Theme**: The app support dark and light theme based on the device settings.

## Dependencies

### Libraries
```
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
desugar-jdk-libs = { module = "com.android.tools:desugar_jdk_libs", version.ref = "desugar-jdk-libs" }
koin-android = { group = "io.insert-koin", name = "koin-android", version.ref = "koin" }
koin-core = { group = "io.insert-koin", name = "koin-core", version.ref = "koin" }
koin-androidx-compose = { group = "io.insert-koin", name = "koin-androidx-compose", version.ref = "koin" }
ktor-client-cio = { module = "io.ktor:ktor-client-cio", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
androidx-lifecycle-runtime-compose = { module = "androidx.lifecycle:lifecycle-runtime-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-compose-material3-adaptive-navigation = { module = "androidx.compose.material3.adaptive:adaptive-navigation", version.ref = "material3-adaptive" }
compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended", version.ref = "compose-material-icons-extended" }
```

### Plugins
```
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

## Installation and Setup
This section provides instructions on how to clone, build, and run the Focus Timer Android application on your local machine using Android Studio.

1.  **Clone the Repository:**

    -   Open your terminal or command prompt.
    -   Navigate to the directory where you want to store the project.
    -   Use the following command to clone the repository:
```
 git clone https://github.com/JairGuzman1810/CryptoTracker/
```
2.  **Open in Android Studio:**

    -   Launch Android Studio.
    -   If you see the "Welcome to Android Studio" screen, select "Open an Existing Project."
    -   If Android Studio is already open, go to "File" > "Open..."
    -   Navigate to the directory where you cloned the repository and select it.
    -   Click "Open."

3.  **Sync Project with Gradle Files:**

    -   Once the project is open, Android Studio will prompt you to sync the project with the Gradle files.
    -   Click "Sync Now" in the notification bar that appears at the top of the editor.
    -   Alternatively, you can go to "File" > "Sync Project with Gradle Files."

4.  **Build the Project:**

    -   After the Gradle sync is complete, go to "Build" > "Make Project" to build the project.
    -   This step compiles the code and checks for any errors.

5.  **Run the App:**

    -   Connect an Android device to your computer via USB, or start an Android emulator.
    -   In Android Studio, select your connected device or emulator from the device dropdown menu in the toolbar.
    -   Click the "Run" button (green play icon) in the toolbar to run the app on your selected device or emulator.
  
## Screenshots

### Light Theme

<div style="display:flex; flex-wrap:wrap; justify-content:space-between;">
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Light-1.jpg" alt="App-Light-1" width="180"/>
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Light-2.jpg" alt="App-Light-2" width="180"/>
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Light-3.jpg" alt="App-Light-3" width="180"/>
</div>

<div style="display:flex; flex-wrap:wrap; justify-content:space-between;">
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Light-4.jpg" alt="App-Light-4" width="300"/>
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Light-5.jpg" alt="App-Light-5" width="300"/>
</div>

### Dark Theme

<div style="display:flex; flex-wrap:wrap; justify-content:space-between;">
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Dark-1.jpg" alt="App-Dark-1" width="180"/>
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Dark-2.jpg" alt="App-Dark-2" width="180"/>
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Dark-3.jpg" alt="App-Dark-3" width="180"/>
</div>

<div style="display:flex; flex-wrap:wrap; justify-content:space-between;">
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Dark-4.jpg" alt="App-Dark-4" width="300"/>
    <img src="https://github.com/JairGuzman1810/CryptoTracker/blob/master/resources/App-Dark-5.jpg" alt="App-Dark-5" width="300"/>
</div>

