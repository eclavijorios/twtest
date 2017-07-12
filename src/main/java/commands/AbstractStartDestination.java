package commands;


import service.TrainService;

public abstract class AbstractStartDestination extends AbstractTrainService {

    protected String start;
    protected String destination;

    public AbstractStartDestination(TrainService service) {
        super(service);
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
