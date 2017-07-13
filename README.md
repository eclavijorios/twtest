# twtest

**Requires**

- JDK 1.8
- Gradle

**Usage:**
navigate to  project path,

use ```gradle build``` to build,
use ```gradle test``` to run junit test

if it's needed, to change the graph or the commands:
 replace under ```src/main/resources/``` the files and maintain same structure given before


to run the app(once build)

```java -jar build/libs/Trains.jar src/main/resources/graph.txt src/main/resources/commands.txt```

Algorithm used :
Dijkstra for resolving shortest paths
DSF (Depth First Traversal) for exploring the graph

quick overview of the application:

Main package contain application main method that runs the given commands in Commands.txt passed as argument in the program execution line, it also receives the graph itself in the Graph.txt.

Under commands package you cand find implementation of command design patter and command procesor design pattern used to handle this last one is used to handle and schedule the implemented command by the command patter such like : "CountRouteWithJumps.java" , "CountRouteWithMaxDistance.java","CountRouteWithMaxJumps.java","Distance.java","ShortestPath.java"and "ShortestPathLength.java" ,in this way is extendable to N numbers of command that just needs to be implemented 

Command processor is the one who executes this commands instantiated by the Train service command factory wich will create this each time based on the commmand that are being passed as input on each command creation, spliting the command input into proper attributes for the above mentioned "command creation".

AbstractStartDestination.java (that contains the start/destination and the service) is extending AbstractTrainService.java ( wich implements the Command.java class and containt the receiver for the command pattern and the service) are extended by the commands mentioned above

Under model package you will find an entity object(Village.java) representing a village, wich will be used by the DirectedGraph.java to create the Graph.

Under util package you will find the VillageMap.java interface containing the methods for each functionality  and VillageMapImpl.java wich implements such methods using the mentioned above graph DirectedGraph.java (T generic object) wich dont need casting for ussage and wich will implement DSF and Dijkstra algorithms for finding the shortest path and travel througth the graph both algorithms are not developed by me and their functionalities are widly covered in serveral internet pages from where i get them, it also implements method for adding finding and comparing graph elements and implement NodeNotReachableException wich extends from Exception for throwing especific expection for this app.

It's good to mention that over the wide internet there are hundred of implementation of Graph problems serverals used as references to build this application.
I'm notincing this in sake of transparency and mind peace of myself =). 
