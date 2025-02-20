# Chef's Corner

Chef's Corner is a powerful and user-friendly food planner application that helps users discover, plan, and save their favorite meals with ease. Built using Java and following the MVP architecture, Chef's Corner leverages the [TheMealDB API](https://www.themealdb.com/api.php) to provide a seamless experience for food enthusiasts.

## Features

### Authentication & User Modes

- **Multiple Authentication Options**: Log in using Email & Password, Google, or Facebook, or continue as a guest.
- **Profile Management**: Users can edit their profile details, including their name and profile picture.

### Meal Discovery & Search

- **Daily Inspirations**: Displays a selection of meals as daily inspiration to help users explore new dishes.
- **Category-Based Suggestions**: Meals from two predefined categories are showcased daily as recommendations.
- **Recently Viewed Meals**: Users can revisit their recently accessed meals.
- **Advanced Search**: Search by category, area, ingredient, or any keyword.
- **Detailed Meal View**:
  - Displays meal image, steps, ingredients, category, and area.
  - Shows a YouTube tutorial video.

### Meal Planning & Favorites

- **Add Meals to Favorites**: Users can save meals they like.
- **Week Meal Planner**: Assign meals to specific dates.
- **Offline Access**: Favorites and planned meals are available offline.
- **Remove or View Meals**: Users can manage their saved lists easily.

### Network-Aware UI

- **Instant Offline Mode Detection**: The UI adapts dynamically when the network connection changes.

### Additional Features

- **Customizable Home Page**: Toggle recently viewed meals on or off.
- **Social Sharing & Contact**:
  - Share the app link with others.
  - Direct access to the developer’s social media profiles (e.g., Facebook).

## Tech Stack

- **Architecture**: MVP (Model-View-Presenter)
- **Networking**: Retrofit
- **Database & Storage**:
  - Room (for local storage)
  - Firebase Realtime Database (backup favorites and plans across devices)
  - SharedPreferences
- **Concurrency & Data Handling**:
  - RxJava for reactive programming
  - Gson for object serialization
- **UI/UX Enhancements**:
  - Glide (image loading)
  - [Lottie Animations](https://github.com/airbnb/lottie-android): Used for splash screens, loading indicators, empty states, and offline views.
  - [Carousel RecyclerView](https://github.com/sparrow007/CarouselRecyclerview) for smooth item displays
  - WebView for displaying YouTube videos
  - Flags displayed as emojis based on the meal’s area
  - [Curved Bottom Navigation](https://github.com/susonthapa/curved-bottom-navigation) for a modern UI experience

## Third-Party Libraries & APIs

- **[TheMealDB API](https://www.themealdb.com/api.php)** - Provides meal data including recipes, ingredients, and meal categories.
- **[Glide](https://github.com/bumptech/glide)** - Efficient image loading and caching.
- **[Lottie](https://github.com/airbnb/lottie-android)** - Beautiful animations for splash screens, loading indicators, and error states.
- **[Carousel RecyclerView](https://github.com/sparrow007/CarouselRecyclerview)** - Smooth scrolling list for daily inspirations.
- **[Curved Bottom Navigation](https://github.com/susonthapa/curved-bottom-navigation)** - Provides a visually appealing bottom navigation bar.

## Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Abdelrahman-Kamel8886/ChefsCorner.git
   ```

2. **Open in Android Studio**:

   - Import the project into Android Studio.
   - Sync Gradle dependencies.

3. **Run the Application**:

   - Ensure an active internet connection for API calls.
   - Run the application on an emulator or physical device.

## Contributing
Contributions are welcome! If you have suggestions or improvements, please fork the repository and submit a pull request. Ensure that your contributions adhere to the coding standards and guidelines of the project.

## Contact
For any questions or inquiries, please contact:
- ### Abdelrahman Kamel
  - **LinkedIn: [LinkedIn Profile](www.linkedin.com/in/abdelrahman-kamel-7a7457200)**
  - **Email: abdelrahmankamel8886@gmail.com**

