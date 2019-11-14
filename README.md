# tUtorSearCher
A revolutionary new app that connects USC Viterbi students, tutor to tutee.

## How to Run and Generate Test Reports (with Coverage)
- Sync gradle
- Open file `build.gradle` for app
- Click the play button next to `task jacocoTestReport`
- The reports can be found in `app/build/reports`. Coverage can be found in `/app/build/reports/coverage/debug/index.html`.
- Yes, these web pages are generated

## How to Run:

### Requirements:
- Android Studio 
- Android Emulator, Pixel 2 with API 28 or 29
    - To add, go to Tools->AVD Manager 

### Steps:
  - Download the project zip and extract the contents into a tUtorSearCher folder. Alternatively, clone the repo using git.
  - Open Android Studio and select the project (it should be recognized as an Android Project, and have the same logo when viewing from     the AS file browser)
  - Once the project opens, it should automatically start building. If not, press Build (Ctrl + F9)
  - Android Studio may ask you to download certain packages for the project. Do so. However, ignore any other pop-ups, such as the        Gradle update suggestion.
  - Once the project is done building, Run App (Shift + F10)

## Modules
### Android App
The entire repository, except for [tUtorSearCher-server/](tUtorSearCher-server/) and [database/](database/), is the android application.
### Remote Server
Server related files are in the [tUtorSearCher-server/](tUtorSearCher-server/) directory. Implemented with Java and Spring Boot.
### Remote Database
Database related files are in the [database/](database/) directory. It will probably only contain a couple of set-up scripts.

## References for Contributors
### UserProfile
`UserProfile` is a singleton class in the app, retrievable with
```java
UserProfile profile = UserProfile.getCurrentUser();

```
The user profile is loaded from remote server and stored by
```java
UserProfile.setCurrentUser(profile);
```
in `LoginActivity.java`.

### Access Token
Access token is a string, retrievable with
```java
getActivity().getSharedPreferences("accessToken", Context.MODE_PRIVATE);
```
It might not be necessary to know this since RetroFit should be configurable in a way the token is added to the header of every request.

### RemoteServerDAO
The singleton data access object for our remote server, more methods to be implemented.

Essentially, each method returns a `Call` object. `LoginModel` and `LoginActivity` are an example of how to use them. We will need further discussion on the exact content in these HTTP requests.
