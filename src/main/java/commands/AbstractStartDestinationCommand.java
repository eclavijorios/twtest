package commands;


import service.TrainService;

public abstract class AbstractStartDestinationCommand extends AbstractTrainServiceCommand {

    protected String start;
    protected String destination;

    public AbstractStartDestinationCommand(TrainService service) {
        super(service);
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
