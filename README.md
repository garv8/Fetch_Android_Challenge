# Fetch Rewards Coding Exercise - Android App

This is an Android application developed as part of a coding exercise for Fetch Rewards. The app fetches data from an API, displays a list of items grouped by their `listId`, allows users to save items, and view saved items. It demonstrates efficient handling of data, network requests, and user-friendly UI design with error handling for network connectivity.

## Features

- **Data Fetching**: Fetches data from an external API hosted on AWS S3.
- **List Grouping and Sorting**: Items are grouped by `listId`, sorted by `listId`, and then by `name`.
- **Filtering**: Filters out items with a blank or null `name`.
- **Save/Unsave Items**: Users can save items to a separate list, and toggle between saved and unsaved states.
- **Navigation**: Users can navigate between the data fetched from the api and their saved data.
- **Network Error Handling**: Checks for internet connectivity and displays a dialog if not connected.
- **Loading Indicator**: Shows a loading indicator while data is being fetched.

- ## Tech Stack

- **Kotlin**: Main programming language used for app development.
- **Android Jetpack Components**:
  - **ViewModel**: Handles the app's data in a lifecycle-aware manner, ensuring data persists across configuration changes.
  - **LiveData**: Allows the UI to observe changes in the data.
  - **Navigation Component**: Manages app navigation, ensuring a smooth user experience.
  - **Data Binding**: Binds UI components in the XML layouts to data sources in the app, enabling reactive programming.
  - **Room**: Used for local storage to store the user's saved items.
- **Retrofit**: For making HTTP requests to fetch data from the API.
- **Gson Converter**: Converts JSON responses into Kotlin data classes.
- **Coroutines**: Allows for asynchronous network requests, keeping the UI responsive.
- **RecyclerView**: Efficiently displays lists of grouped items.

- ## Project Structure

The project is structured as follows:
- MainActivity.kt                   # Hosts the navigation component
- fragments/
  - ListSelectionFragment.kt         # Displays grouped list IDs
  - ItemListFragment.kt              # Displays items for a selected list ID
- viewmodel/
  - ItemViewModel.kt                 # Handles data fetching and saving states
  - ViewModelFactory.kt              # Creates ViewModel with dependencies
- adapter/
  - ListAdapter.kt                   # Adapter for ListSelectionFragment
  - ItemAdapter.kt                   # Adapter for ItemListFragment
- model/
  - Item.kt                          # Data class representing an item
- repository/
  - ItemRepository.kt                # Manages data fetching logic
- utils/
  - NetworkUtils.kt                  # Checks for internet connectivity
- layout/
  - activity_main.xml                # Layout for MainActivity
  - fragment_list_selection.xml      # Layout for ListSelectionFragment
  - fragment_item_list.xml           # Layout for ItemListFragment

## How to Run the Project

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Make sure you have a device/emulator with internet connectivity to test the API call.
4. Build and run the project on your device/emulator.
