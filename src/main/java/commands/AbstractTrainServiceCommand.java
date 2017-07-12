package commands;


import service.TrainService;

public abstract class AbstractTrainServiceCommand implements Command {

     private TrainService receiver;

    public AbstractTrainServiceCommand(TrainService service){
        this.receiver = service;
    }

    public TrainService getReceiver() {
        return this.receiver;
    }
}
