# Proiect GlobalWaves

## Overview of Packages and Classes

In this phase of the project, several new packages and classes have been
introduced to enhance the functionality and structure of the application:

### 1. artist Package
- **Artist:** Contains core functionalities related to artists, including
details about artists, albums, events, etc.

### 2. host Package
- **Host:** Encompasses functionalities associated with hosts, announcements, etc.

### 3. outputsandmediaplayer Package
- **OutputClass:** Facilitates the printing of outputs.
- **MediaPlayer:** Manages media playback.
- **CommandRunner:** Verifies and executes commands, utilizing utility classes
like `Play` and `Check`.

### 4. printcurrentpage Package
- **PrintPage:** Manages the printing of pages.
- **Print:** Implements the Visitor design pattern for printing different pages 
efficiently.

## Design Pattern - Visitor

The Visitor design pattern is implemented in the `printcurrentpage` package.
The `PrintPage` class contains the main function, `printPageFunc`, which uses
the `Print` class as a visitor. This approach enables the application to print
pages dynamically based on their type.

## Interaction Between Classes

- **CommandRunner:** Executes commands stored in a list of `CommandInput`
objects. Manages a global list of artists and hosts, adding new entries as
needed.
- **Play:** Simulates the playback of songs, albums, playlists, or podcasts.
- **User:** Creates new users, specifying their type (normal user, artist,
or host), and sets default online status and homepage.

## Code Execution Flow

1. **Command Input:** Commands are stored in a list of `CommandInput` objects.
2. **Command Execution:** The `CommandRunner` class processes commands, updating
the list of outputs stored in `ArrayNode` objects.
3. **Global Lists:** Artists and hosts are managed globally in the
`CommandRunner` class, providing a centralized repository for these entities.
4. **Play Class:** Simulates media playback, enhancing the user experience.
5. **User Class:** Creates new users with specific roles and default settings.

## Areas for Improvement and Conclusion

While the project is deemed successful, several areas for improvement have been
identified:

- **Code Refactoring:** Consider optimizing the `solution` method in the
`CommandRunner` class to reduce redundancy.

In conclusion, the current project iteration demonstrates effective
implementation of packages, classes, and design patterns. Identified areas for
improvement provide valuable insights for refining and expanding the
application in future development stages.

<div align="center"><img src="https://media1.tenor.com/m/xd5G6GQBh8QAAAAd/kitty-claws-christmas-tree-cat-playing-with-christmas-tree.gif" width="300px"></div>
