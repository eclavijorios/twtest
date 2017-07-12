package main;


import commands.CommandFactory;
import commands.CommandProcessor;
import commands.TrainServiceCommandFactory;
import service.TrainService;
import service.TrainServiceImpl;
import util.VillageMap;
import util.VillageMapImpl;

import java.io.FileNotFoundException;

public class Application {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("wrong number of params , please give path to: graph.txt, commands.txt");
            System.exit(1);
        } else {
            String graphFilePath = args[0];
            String commandsFilePath = args[1];

            VillageMap map = new VillageMapImpl();
            try {
                map.init(graphFilePath);
                TrainService service = new TrainServiceImpl(map);
                CommandFactory commandFactory = new TrainServiceCommandFactory(service);
                CommandProcessor processor = new CommandProcessor(commandFactory);

                System.out.println(processor.runAll(commandsFilePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}