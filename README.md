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


