# CSE682-Sudoku-Android
Term project for CSE682 Software Engineering, Syracuse University

## Introduction

This Android application allows a user to solve  Sudoku puzzle and check solutions by taking a photograph with their phone camera and uses a distributed system approach.

[Link to SRS Document](https://docs.google.com/document/d/1i3jTwvAZrSgjs6TnRywGZNwpDQP4tRTtefdVMilIm94/edit?usp=sharing)

[Link to deployed development instance](https://sudoku-dev.herokuapp.com)

## Deployment

- Debug:

```
./gradlew assembleDebug
```

- Release:

The project is not currently configured to automatically sign for release. Configure with your key and then run:

```
./gradlew assembleRelease
```


## Dependencies

The back-end services this application depends on are accessible at the following repositories:

- [Solver System](https://github.com/Wrenky/sudoKu) written by Kevin Wren
- [REST API](https://github.com/ryanbmilbourne/syr-sudoku-backend)  written by Ryan Milbourne

Android Application dependencies are linked in the build.gradle file:

- [Retrofit](https://github.com/square/retrofit)
- [EventBus](https://github.com/greenrobot/EventBus)
- [Parceler](https://github.com/johncarl81/parceler)
- [AndroidPhotoFilters](https://github.com/Zomato/AndroidPhotoFilters)

## UML Diagram

Android SDK methods, and Image Processing logic from NewPuzzle, removed for brevity.

![UML Diagram](https://github.com/carlpoole/CSE682-Sudoku-Android/blob/master/UML.png?raw=true)