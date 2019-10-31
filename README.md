# tUtorSearCher
A revolutionary new app that connects USC Viterbi students, tutor to tutee.

## Modules
### Android App
The entire repository, except for [tUtorSearCher-server/](tUtorSearCher-server/) and [databse/](database/), is the android application.
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
