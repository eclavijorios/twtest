package util;


import model.Village;

import java.io.FileNotFoundException;

public interface VillageMap {
    void parseInput(String input);


    void init(String path) throws FileNotFoundException;

    String shortestPathBetween(String start, String destination) throws VillageMapImpl.NoSuchRouteException;

    int lengthOfShortestPathBetween(String start, String destination) throws VillageMapImpl.NoSuchRouteException;

    int distance(String... VillagesNames) throws VillageMapImpl.NoSuchRouteException;

    int countRoutesWithMaxJumps(String start, String destination, int maxJumps);

    int countRoutesWitJumps(String start, String destination, int jumps);

    int countRoutesWithMaxDistance(String start, String destination, int maxDistance);


}
