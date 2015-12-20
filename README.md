Final Project

1.Package yanchen.asg3
I am responsible for the UI part and trip plan part.
The main UI is designed for the user to input information need to plan their trip and show the map. The  input includes algorithm, start location, destination location, max transfer number. All the inputs are denoted using integer flags. You should follow the instruction on the top of the window. Once you click on the “Search!” button, if your input is incorrect, error message will be displayed on the console. If the input is correct, and you will get the result of the search. The CSS of menubar is changed in order for it to be smaller along with the buttons.

The trip plan uses the result of heuristic plan to calculate the true time of a trip, that is, including the waiting time of a MBTA vehicle. Every time the user reach a stop in the stop list, the app will compare the arrival time with the latest vehicle of a certain route_id which we get from the heuristic algorithm. Whats more, the app will trace the trip_id of the route that the user will take to reach the next stop and check the arrival time of the next time. Using basic iteration, the app is able to calculate the real time taken in the process.

2.Package MBTA
In package MBTA, the model of the MBTA been able to be serialized is store here. Considering the naming conflicts with my teammates, I use my own style of naming pattern and store the model in a different package to avoid confusion. Each class defined in this package strictly follow the json format of the MBTA response get using the URL. The rule of creating those models could be found in MBTA documentation.

3.Package UsfTools
This package store some useful tools that will be called by the main UI. The instance of CoordinateList class store the coordinates got from the algorithm, including the coordinates pair of each stop visited and the name of the stop. The coordinates are for add markers and draw line. The name of the stop is for display when user place their cursor on the Marker.

The FileIO class is used as an alternative way to store the response of MBTA in case of running out of API key. I intended to store the whole map in a 'txt' file for the UI to 'read' and avoid the using of MBTA request. The user will not going to user this function in reality.

The OutputLabelRoutePlan class defines the structure of the hint of the search result on the console. I use PREFORMATTED format for the Label in order to use formatted output to control the lines of output to make the result prettier. The font size of the letter and font weight is changed to make the hint more compact via SCSS in vaadin theme.

4.Package Coordinates
Package Coordinates defines the form the coordinates stored in our project. The MBTA using String representation to annotate coordinates so I choose String instead of Double which is used by Google Map plugin. Every time the user what to display a certain location on the map, one has to transform the representation of coordinates first.

5.MBTAResponse
MBTAResponse package is responsible to handle MBTA request and map them to the class that we defined in our project. User will call different MBTA request on the UI window and then call the mapper function to store the response.
==============

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.


Workflow
========

To compile the entire project, run "mvn install".
To run the application, run "mvn jetty:run" and open http://localhost:8080/ .

To develop the theme, simply update the relevant theme files and reload the application.
Pre-compiling a theme eliminates automatic theme updates at runtime - see below for more information.

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean vaadin:compile-theme package"
  - See below for more information. Running "mvn clean" removes the pre-compiled theme.
- test with "mvn jetty:run-war

Using a precompiled theme
-------------------------

When developing the application, Vaadin can compile the theme on the fly when needed,
or the theme can be precompiled to speed up page loads.

To precompile the theme run "mvn vaadin:compile-theme". Note, though, that once
the theme has been precompiled, any theme changes will not be visible until the
next theme compilation or running the "mvn clean" target.

When developing the theme, running the application in the "run" mode (rather than
in "debug") in the IDE can speed up consecutive on-the-fly theme compilations
significantly.

Using Vaadin pre-releases
-------------------------

If Vaadin pre-releases are not enabled by default, use the Maven parameter
"-P vaadin-prerelease" or change the activation default value of the profile in pom.xml .
