package commands;


import service.TrainService;

public class CountRouteWithJumps extends AbstractStartDestination {

    private int jumps;


    public CountRouteWithJumps(TrainService service) {
        super(service);
    }

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }

    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithJumps(start, destination, jumps);
    }
}
