# ShuffleAndLearn App

ShuffleAndLearn is an Android application designed to facilitate the learning of English vocabulary through interactive flashcards and a word game. This app offers various features to enhance language learning, including a word list, a section for learned words, and a timed game for testing translation skills.

## Features

### Splash Screen
- **Functionality:** Displays a splash screen for 4 seconds when the app launches.
- **Purpose:** Provides a smooth transition into the app and sets up initial loading.

### Home Screen
- **Functionality:** Shows a list of 50 English words, each with a Turkish translation and an associated image.
- **Features:**
  - **Refresh Button:** Randomizes the order of the words.
  - **Learned Words Management:** Allows users to mark words as learned, which are then moved to the Learned Words screen.

### Detail Screen (DetailFragment)
- **Functionality:** Provides detailed information about a selected word from the Home or Learned Words screen.
- **Features:**
  - **Translation:** Shows the English translation of the selected Turkish word.
  - **Example Sentence:** Displays an example sentence to demonstrate the use of the word in context.
  - **Image Display:** Shows a relevant image that helps users associate the word visually.
  - **Navigation Back:** Allows users to return to the previous screen (Home or Learned Words).
  - **Learned Word Toggle:** Users can mark or unmark a word as learned from this screen.

### Learned Words Screen
- **Functionality:** Lists words that have been marked as learned.
- **Features:**
  - **Review:** Users can review learned words and unmark them if needed.

### Word Game Screen
- **Functionality:** A timed game where users translate Turkish words into English.
- **Features:**
  - **Timer:** 1-minute countdown to answer as many questions as possible.
  - **Score Tracking:** Keeps track of correct and incorrect answers.
  - **Animations:** Displays different animations based on the correctness of the answers (happy for correct, sad for incorrect).

## Project Structure

### 1. MVVM Architecture
The app follows the MVVM architecture pattern:
- **Model:** Contains the data logic and interacts with the JSON file to provide a list of words.
- **ViewModel:** Manages UI-related data in a lifecycle-conscious way. The `WordGameViewModel` handles the logic for the word game, including the timer and score tracking.
- **View:** Fragments that display the UI, observing the ViewModel's LiveData.

### 2. Fragments and Navigation
- **SplashScreenFragment:** Displays an introductory splash screen for 4 seconds.
- **HomeFragment:** Displays the list of 50 randomly ordered words fetched from the `words.json` file.
- **LearnedWordsFragment:** Displays words marked as learned, stored in `SharedPreferences`.
- **DetailFragment:** Provides detailed information about a selected word, including its translation, image, and an example sentence.
- **WordGameFragment:** Displays the word game, allowing the user to translate Turkish words into English within a 1-minute time limit.

### 3. Word Game
The word game is implemented in the `WordGameFragment` and is controlled by the `WordGameViewModel`. Key features include:
- **Timer:** A 1-minute countdown managed by a Coroutine in the ViewModel.
- **Score Tracking:** Correct and incorrect translations are tracked and displayed on the screen in real-time.
- **Animations:** Correct and incorrect answers trigger animations using Lottie (e.g., a happy or sad animation).
- **Game Reset:** After the game ends, users are prompted to either restart or return to the home screen.

## Key Classes

### 1. `MainActivity.kt`
The main activity of the app, which sets up the `BottomNavigationView` and manages fragment navigation using Jetpack Navigation.

### 2. `WordGameFragment.kt`
This fragment handles the word game, including:
- Displaying Turkish words and accepting user input.
- Running the game timer.
- Updating the UI based on the user's input (e.g., showing animations for correct/incorrect answers).
- Tracking correct and incorrect answers using LiveData.

### 3. `WordGameViewModel.kt`
The ViewModel responsible for:
- Fetching and shuffling words from the repository.
- Managing the game timer and tracking the remaining time.
- Handling user input and updating the correct and incorrect count.
- Resetting the game and controlling the game state.

### 4. `SplashScreenFragment.kt`
Displays a simple splash screen for 4 seconds before navigating to the home screen.

### 5. `HomeFragment.kt`, `LearnedWordsFragment.kt`, and `DetailFragment.kt`
- **HomeFragment:** Displays the list of 50 words in a RecyclerView, fetched from a local JSON file.
- **LearnedWordsFragment:** Displays the learned words, stored in `SharedPreferences`.
- **DetailFragment:** Provides detailed word information, including translation, image, and example sentence.

### 6. `WordRepository.kt`
Responsible for fetching words from the local `words.json` file and providing them to the ViewModel.

## Navigation
The app uses Jetpack Navigation for smooth transitions between fragments. Navigation actions are handled within the `MainActivity` and Fragments. The `BottomNavigationView` includes three main tabs:
1. **HomeFragment:** Shows the list of 50 words.
2. **LearnedWordsFragment:** Shows the user's learned words.
3. **WordGameFragment:** Launches the word game.

## SharedPreferences
The app saves and retrieves learned words using `SharedPreferences`. Words marked as learned are removed from the main word list and displayed in the "Learned Words" section.

## Animations
- **Lottie Animations:** Used in the word game to provide visual feedback for correct and incorrect answers.
- **Shake Animation:** Triggered for incorrect answers using Android's `AnimationUtils`.

## How to Run

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Run the app on an emulator or a physical device.
4. Enjoy the flashcards and word game experience!

## Dependencies
- **Glide:** For loading images from URLs.
- **Lottie:** For displaying animations.
- **Jetpack Navigation:** For managing fragment navigation.
- **ViewModel and LiveData:** To manage UI-related data and observe changes.


### Project Video



https://github.com/user-attachments/assets/d3abd450-31d2-41d8-bd33-c9a57e923587





### Screenshoots

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/11f6fb1f-0c8a-4452-ad9a-fe8ba5e3b797" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/58458408-5692-4127-b58b-8559c05242bc" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/60b0b69b-86e2-4dbd-a867-c62ba1318048" width="290"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/31c320ef-003d-415c-8979-356a04cad87f" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/68f2ac71-58b6-49cb-820e-62ababf5f77d" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/257c8e8c-d17f-4d1c-8845-99064139854c" width="290"></td>
  </tr>
    <tr>
    <td><img src="https://github.com/user-attachments/assets/ab088cda-a60b-41a2-9c17-ead3d4c61bc5" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/47ac375e-96ae-4678-84de-814fd4051f73" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/13f5fd17-a26e-4c2d-a589-7483b3f1ed9f" width="290"></td>
  </tr>
</table>



