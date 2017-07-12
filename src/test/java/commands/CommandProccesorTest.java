package commands;

import commands.CommandFactory;
import commands.CommandProcessor;
import commands.TrainServiceCommandFactory;
import org.junit.Before;
import org.junit.Test;
import service.TrainService;
import service.TrainServiceImpl;
import util.VillageMap;
import util.VillageMapImpl;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;


public class CommandProccesorTest {
    private CommandProcessor processor;

    @Before
    public void initObjects() {
        VillageMap map = new VillageMapImpl();
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File graphFile = new File(classLoader.getResource("graph.txt").getFile());
            map.init(graphFile.getAbsolutePath());
            TrainService service = new TrainServiceImpl(map);

            CommandFactory commandFactory = new TrainServiceCommandFactory(service);

            processor = new CommandProcessor(commandFactory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRunAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        File commandsFile = new File(classLoader.getResource("commands.txt").getFile());
        String ans = null;
        try {
            ans = processor.runAll(commandsFile.getAbsolutePath());
            assertEquals("9\n5\n13\n22\n2\n3\n9\n9\n7\n7", ans);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRunCommand1() {
        String ans = processor.run("distance,A,D");
        assertEquals("5", ans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunUnknownCommand1() {
        String ans = processor.run("xxxx,A,D");
    }


}
