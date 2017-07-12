package commands;


import service.TrainService;

public class ShortestPath extends AbstractStartDestinationCommand {



    public ShortestPath(TrainService service) {
        super(service);
    }


    @Override
    public String execute() {
        return getReceiver().shortestPathBetween(start, destination);
    }

}
