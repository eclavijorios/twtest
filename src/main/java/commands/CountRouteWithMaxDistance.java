package commands;


import service.TrainService;

public class CountRouteWithMaxDistance extends AbstractStartDestinationCommand {
    private int maxDistance;

    public CountRouteWithMaxDistance(TrainService service) {
        super(service);
    }


    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithMaxDistance(start, destination, maxDistance);
    }
}
