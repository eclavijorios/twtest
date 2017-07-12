package commands;


import service.TrainService;

public class CountRouteWithMaxJumps extends AbstractStartDestination {

    private int maxJumps;


    public  CountRouteWithMaxJumps (TrainService service){
        super(service);
    }

    public void setMaxHops(int maxJumps) {
        this.maxJumps = maxJumps;
    }


    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithMaxJumps(start, destination, maxJumps);
    }

}
