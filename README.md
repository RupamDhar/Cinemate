# Cinemate

Cinemate is a Java-based application that allows users to search for movies on IMDb using the IMDb API. This application provides a simple graphical user interface (GUI) for entering a movie name, and it displays information about the movie, including its title, release year, and cast members.


## Features

User-friendly GUI for entering movie names and initiating searches.
Real-time retrieval of movie details from the IMDb API.
Display of movie information, including title, release year, and cast members.
Error handling for cases where movie information is not available.


## Usage

1. Launch the Cinemate application.
2. Enter the name of the movie you want to search for in the input field.
3. Click the "SEARCH" button to initiate the search.
4. The movie details, including IMDb rating, title, release year, and cast members, will be displayed in the output area.
5. If there are multiple search results, they will be displayed one by one.
6. Movie information will also be stored in the local MySQL database.


## Dependency Management

Maven is used as the Java project tool for dependency management. The project's dependencies are defined in the `pom.xml` file.


## To keep in mind

In `CinemateRequest.class` : Remember to use your own X-RapidAPI-Key in `request` builds.
