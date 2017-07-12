package commands;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandProcessor {

    private CommandFactory commandFactory;

    public CommandProcessor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    private List<Command> loadFile(String path) throws FileNotFoundException {
        List<Command> commands = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(path);
        Scanner in = new Scanner(fileInputStream);

        while (in.hasNext()) {
            String input = in.next();
            commands.add(commandFactory.createCommand(input));
        }

        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }


    public String runAll(String path) throws FileNotFoundException {
        List<Command> commands = loadFile(path);

        if (!commands.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < commands.size(); i++) {
                Command command = commands.get(i);
                sb.append(command.execute());
                sb.append("\n");
            }

            Command command = commands.get(commands.size() - 1);
            sb.append(command.execute());

            return sb.toString();
        } else {
            throw new NoSuchElementException("No such element");
        }
    }

    public String run(String input) {
        if (input != null) {
            try {
                Command command = commandFactory.createCommand(input);
                Object result = command.execute();
                return result.toString();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException("Illegal Argument, Can't parse Argument");
            }
        } else {
            throw new IllegalArgumentException("Illegal Argument, Can't parse Argument");
        }
    }

}
