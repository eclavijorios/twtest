package commands;


import service.TrainService;

public class CountRouteWithJumps extends AbstractStartDestinationCommand {

    private int hops;


    public CountRouteWithJumps(TrainService service) {
        super(service);
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithJumps(start, destination, hops);
    }
}
