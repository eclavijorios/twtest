package util;

import model.Village;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class VillageMapImpl implements VillageMap {

    public static class NoSuchRouteException extends RuntimeException {
        public NoSuchRouteException(String message) {
            super(message);
        }
    }

    private DirectedGraph<Village> graph;
    private Map<String, Village> villages;


    public VillageMapImpl() {
        graph = new DirectedGraph<>();
        villages = new HashMap<>();
    }


    private void addVillage(Village village) {
        graph.addNode(village);
        if (!villages.containsKey(village.getName())) {
            villages.put(village.getName(), village);
        }
    }


    @Override
    public void parseInput(String input) {
        if (input.length() < 2) {
            throw new IllegalArgumentException("Invalid input format");
        } else {
            String start = String.valueOf(input.charAt(0));
            String destination = String.valueOf(input.charAt(1));

            int weight = 0;

            if (input.length() > 2) {
                try {
                    weight = Integer.parseInt(input.substring(2));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Weight for input: "+ input +" is not valid!");
                }
            }

            Village startVillage = new Village(start);
            Village destinationVillage = new Village(destination);

            addVillage(startVillage);
            addVillage(destinationVillage);
            graph.addEdge(startVillage, destinationVillage, weight);

        }
    }

    @Override
    public void init(String path) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        Scanner in = new Scanner(fileInputStream);

        while (in.hasNext()) {
            String input = in.next();
            parseInput(input);
        }

        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String shortestPathBetween(String start, String destination) throws NoSuchRouteException {
        try {
            DirectedGraph.GraphPath path = graph.shortestPathBetween(villages.get(start), villages.get(destination));

            return path.toString();
        } catch (DirectedGraph.NodeNotReachableException | NoSuchElementException e) {
            throw new NoSuchRouteException(e.getMessage());
        }
    }

    @Override
    public int lengthOfShortestPathBetween(String start, String destination) throws NoSuchRouteException {
        try {
            DirectedGraph.GraphPath path = graph.shortestPathBetween(villages.get(start), villages.get(destination));

            return path.distance();
        } catch (DirectedGraph.NodeNotReachableException | NoSuchElementException e) {
            throw new NoSuchRouteException(e.getMessage());
        }
    }

    @Override
    public int distance(String... VillagesNames) throws NoSuchRouteException {
        List<Village> villageList = new ArrayList<Village>();
        for (String villageName : VillagesNames) {
            villageList.add(villages.get(villageName));
        }

        try {
            return graph.distance(villageList);
        } catch (NoSuchElementException | DirectedGraph.NodeNotReachableException e) {
            throw new NoSuchRouteException(e.getMessage());
        }
    }


    @Override
    public int countRoutesWithMaxJumps(String start, String destination, int maxJumps) {
        return graph.countRoutesWithMaxHops(villages.get(start), villages.get(destination), maxJumps);
    }

    @Override
    public int countRoutesWitJumps(String start, String destination, int jumps) {
        return graph.countRoutesWithHops(villages.get(start), villages.get(destination), jumps);
    }

    @Override
    public int countRoutesWithMaxDistance(String start, String destination, int maxDistance) {
        return graph.countRoutesWithMaxDistance(villages.get(start), villages.get(destination), maxDistance);
    }

}

