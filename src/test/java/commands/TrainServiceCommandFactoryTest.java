package commands;


import org.junit.Before;
import org.junit.Test;
import service.TrainService;
import service.TrainServiceImpl;
import util.VillageMap;
import util.VillageMapImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertTrue;

public class TrainServiceCommandFactoryTest {

    private TrainServiceCommandFactory factory;


    @Before
    public void initObjects() {
        VillageMap map = new VillageMapImpl();
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File graphFile = new File(classLoader.getResource("graph.txt").getFile());
            map.init(graphFile.getAbsolutePath());
            TrainService service = new TrainServiceImpl(map);

            factory = new TrainServiceCommandFactory(service);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCreateShortestPathCommand() {
        Command command = factory.createCommand("shortest_path,A,C");
        assertTrue(command instanceof ShortestPath);
    }

    @Test
    public void testCreateShortestPathLengthCommand() {
        Command command = factory.createCommand("length_of_shortest_path,A,C");
        assertTrue(command instanceof ShortestPathLength);
    }

    @Test
    public void testCreateDistanceCommand() {
        Command command = factory.createCommand("distance,A,B,C");
        assertTrue(command instanceof Distance);
    }

    @Test
    public void testCreateCountRoutesWithMaxHopsCommand() {
        Command command = factory.createCommand("count_routes_with_max_jumps,C,C,3");
        assertTrue(command instanceof CountRouteWithMaxJumps);
    }

    @Test
    public void testCreateCountRoutesWithHopsCommand() {
        Command command = factory.createCommand("count_routes_with_jumps,A,C,4");
        assertTrue(command instanceof CountRouteWithJumps);
    }

    @Test
    public void testCreateCountRoutesWithMaxDistanceCommand() {
        Command command = factory.createCommand("count_routes_with_max_distance,C,C,29");
        assertTrue(command instanceof CountRouteWithMaxDistance);
    }

    @Test(expected = NoSuchElementException.class)
    public void testUnknownCommand1() {
        Command command = factory.createCommand("cccc,C,C,29");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormat1() {
        Command command = factory.createCommand("shortest_path,C,C,29");
    }



}
