package service;


import util.VillageMap;
import util.VillageMapImpl;

public class TrainServiceImpl implements TrainService{

    private VillageMap map;

    public TrainServiceImpl(VillageMap map) {
        this.map = map;
    }

    @Override
    public String shortestPathBetween(String start, String destination) {
        try {
            return map.shortestPathBetween(start, destination);
        } catch (VillageMapImpl.NoSuchRouteException e) {
            throw new TrainServiceException("NO SUCH ROUTE");
        }
    }
    @Override
    public int lengthOfShortestPathBetween(String start, String destination) {
        try {
            return map.lengthOfShortestPathBetween(start, destination);
        } catch (VillageMapImpl.NoSuchRouteException e) {
            throw new TrainServiceException("NO SUCH ROUTE");
        }    }

    @Override
    public int distance(String... villages) {
        try {
            return map.distance(villages);
        } catch (VillageMapImpl.NoSuchRouteException e) {
            throw new TrainServiceException("NO SUCH ROUTE");
        }    }

    @Override
    public int countRoutesWithMaxJumps(String start, String destination, int maxJumps) {
        return map.countRoutesWithMaxHops(start, destination, maxJumps);
    }

    @Override
    public int countRoutesWithJumps(String start, String destination, int jumps) {
        return map.countRoutesWithHops(start, destination, jumps);
    }

    @Override
    public int countRoutesWithMaxDistance(String start, String destination, int maxDistance) {
        return map.countRoutesWithMaxDistance(start, destination, maxDistance);
    }
}
