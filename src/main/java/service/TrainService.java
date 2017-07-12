package service;

public interface TrainService {

    String shortestPathBetween(String start, String destination);

    int lengthOfShortestPathBetween(String start, String destination);

    int distance(String... villages);

    int countRoutesWithMaxJumps(String start, String destination, int maxJumps);

    int countRoutesWithJumps(String start, String destination, int jumps);

    int countRoutesWithMaxDistance(String start, String destination, int maxDistance);
}
