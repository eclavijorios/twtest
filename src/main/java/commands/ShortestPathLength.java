package commands;


import service.TrainService;

public class ShortestPathLength extends AbstractStartDestination {


    public ShortestPathLength(TrainService service) {
        super(service);
    }


    @Override
    public Integer execute() {
        return getReceiver().lengthOfShortestPathBetween(start, destination);
    }
}
