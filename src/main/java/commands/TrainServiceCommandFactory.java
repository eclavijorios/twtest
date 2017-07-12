package commands;


import service.TrainService;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class TrainServiceCommandFactory implements CommandFactory {

    private final String DISTANCE = "distance";
    private final String SHORTEST_PATH = "shortest_path";
    private final String LENGTH_SHORTEST_PATH = "length_of_shortest_path";
    private final String ROUTES_WITH_MAX_HOPS = "count_routes_with_max_jumps";
    private final String ROUTES_WITH_HOPS = "count_routes_with_jumps";
    private final String ROUTES_WITH_MAX_DISTANCE = "count_routes_with_max_distance";

    private final TrainService service;


    public TrainServiceCommandFactory(TrainService service) {
        this.service = service;
    }


    @Override
    public Command createCommand(String input) {
        String[] parts = input.split(",");

        if (parts.length <= 1) {
            throw new IllegalArgumentException("Invalid Input format, Read .md file.");
        } else {
            String name = parts[0];

            switch (name) {
                case SHORTEST_PATH:
                    return createShortestPathCommand(input);
                case LENGTH_SHORTEST_PATH:
                    return createShortestPathLengthCommand(input);
                case DISTANCE:
                    return createDistanceCommand(input);
                case ROUTES_WITH_MAX_HOPS:
                    return createCountRouteWithMaxJumps(input);
                case ROUTES_WITH_HOPS:
                    return createCountRouteWithJumps(input);
                case ROUTES_WITH_MAX_DISTANCE:
                    return createCountRouteWithMaxDistance(input);
                default:
                    throw new NoSuchElementException("Unknown command : " + name);
            }

        }
    }

    private ShortestPath createShortestPathCommand(String input) {
        String[] parts = input.split(",");

        if (parts.length < 3 || parts.length > 3) {
            throw new IllegalArgumentException("wrong input format");
        } else {
            ShortestPath command = new ShortestPath(service);

            command.setStart(parts[1]);
            command.setDestination(parts[2]);

            return command;
        }
    }

    private ShortestPathLength createShortestPathLengthCommand(String input) {
        String[] parts = input.split(",");

        if (parts.length < 3 || parts.length > 3) {
            throw new IllegalArgumentException("wrong input format");
        } else {
            ShortestPathLength command = new ShortestPathLength(service);

            command.setStart(parts[1]);
            command.setDestination(parts[2]);

            return command;
        }
    }

    private Distance createDistanceCommand(String input) {
        String[] parts = input.split(",");

        if (parts.length < 2) {
            throw new IllegalArgumentException("wrong input format");
        } else {
            Distance command = new Distance(service);

            String[] villageNames = Arrays.copyOfRange(parts, 1, parts.length);
            command.setVillageNames(villageNames);

            return command;
        }
    }


    private CountRouteWithMaxJumps createCountRouteWithMaxJumps(String input) {
        String[] parts = input.split(",");

        if (parts.length < 4 || parts.length > 4) {
            throw new IllegalArgumentException("wrong input format");
        } else {
            CountRouteWithMaxJumps command = new CountRouteWithMaxJumps(service);

            command.setStart(parts[1]);
            command.setDestination(parts[2]);

            try {
                command.setMaxHops(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("wrong input format");
            }

            return command;
        }
    }


    private CountRouteWithJumps createCountRouteWithJumps(String input) {
        String[] parts = input.split(",");

        if (parts.length < 4 || parts.length > 4) {
            throw new IllegalArgumentException("wrong input format");
        } else {
            CountRouteWithJumps command = new CountRouteWithJumps(service);

            command.setStart(parts[1]);
            command.setDestination(parts[2]);

            try {
                command.setJumps(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("wrong input format");
            }

            return command;
        }
    }

    private CountRouteWithMaxDistance createCountRouteWithMaxDistance(String input) {
        String[] parts = input.split(",");

        if (parts.length < 4 || parts.length > 4) {
            throw new IllegalArgumentException("wrong input format");
        } else {
            CountRouteWithMaxDistance command = new CountRouteWithMaxDistance(service);

            command.setStart(parts[1]);
            command.setDestination(parts[2]);

            try {
                command.setMaxDistance(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("wrong input format");
            }

            return command;
        }
    }

}
