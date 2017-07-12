package util;


import model.Village;

import java.io.FileNotFoundException;

public interface VillageMap {
    void parseInput(String input);


    void init(String path) throws FileNotFoundException;

    String shortestPathBetween(String start, String destination) throws VillageMapImpl.NoSuchRouteException;

    int lengthOfShortestPathBetween(String start, String destination) throws VillageMapImpl.NoSuchRouteException;

    int distance(String... VillagesNames) throws VillageMapImpl.NoSuchRouteException;

    int countRoutesWithMaxHops(String start, String destination, int maxHops);

    int countRoutesWithHops(String start, String destination, int hops);

    int countRoutesWithMaxDistance(String start, String destination, int maxDistance);


}
