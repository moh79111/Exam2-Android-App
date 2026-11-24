# Exam2 — Android Java/XML

A two-Activity Android application implementing the requested login validation, double-back exit Snackbar, swipeable tabs, tab-aware back navigation, Light/Night/System PopupMenu, and toolbar Up navigation.

## Project requirements

- Android Studio (recent stable version)
- JDK 17
- Android SDK Platform 35
- Gradle 8.9
- Android Gradle Plugin 8.7.3
- Internet connection for the first Gradle dependency download

## Open on Windows

1. Extract the ZIP file to a normal folder (for example `D:\Projects\Exam2`).
2. If the project does not yet contain Gradle Wrapper files, double-click `Prepare-Project.bat` once. It downloads Gradle 8.9 from the official Gradle distribution service and generates the standard wrapper files. If you have limited space on C:, set `GRADLE_BOOTSTRAP_DIR` to a folder on a drive with free space before running the script. If `ANDROID_AVD_HOME` is already set, the script uses a `gradle-bootstrap` folder under that location.
3. Open the `Exam2` folder in Android Studio.
4. Set Gradle JDK to JDK 17 if Android Studio asks.
5. Install Android SDK Platform 35 from SDK Manager if it is missing.
6. Wait for Gradle Sync to finish, then choose an emulator or connect an Android phone with USB debugging enabled and press Run.

On a Mac/Linux machine, install Gradle 8.9 and run `gradle wrapper --gradle-version 8.9` in the project root once, then open the folder in Android Studio.

## Features implemented

### MainActivity
- Email is checked with Android's `Patterns.EMAIL_ADDRESS`.
- Login remains disabled until the email is valid and password length is at least six characters.
- Login opens `ModeActivity`.
- Back displays the exact message `Press back again to exit`; a second press within 2.5 seconds exits the task.

### ModeActivity
- `TabLayout` connected to `ViewPager2` with First, Second, and Third pages.
- Swipe gestures and tab taps both change pages.
- Back from Second/Third returns to First; Back from First returns to MainActivity.
- Set Mode PopupMenu offers Light, Night, and System; selection is saved.
- Toolbar Up returns directly to MainActivity.

## Test credentials for demonstration

Use any syntactically valid email and any password with at least six characters. Example:

- Email: `student@example.com`
- Password: `abc123`

These are sample inputs only. **This assignment implements client-side validation, not real secure authentication.** No backend, account database, or password verification service is included. Do not enter a real password.

## Build APK

After Gradle Sync, choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**. The debug APK is created at:

`app/build/outputs/apk/debug/app-debug.apk`

The GitHub Actions workflow in `.github/workflows/android-build.yml` also builds the debug APK on each push and uploads it as a workflow artifact.

## Before submission

1. Run the app and test all items in the checklist above.
2. Run **Build > Clean Project**.
3. Do not upload `local.properties`, real credentials, or generated build folders.
4. Push the source to your public GitHub repository.
5. Record your own demo (maximum 3 minutes), beginning with your full name and student ID.
6. Submit the repository link using the required `StudentName_ID_Exam2` format.

## Important

This is a source project, not a prebuilt APK. The target computer still needs Android Studio, JDK 17, Android SDK 35, and an internet connection for first-time dependency downloads. The source package includes a one-time Gradle Wrapper setup script and GitHub Actions build workflow.
