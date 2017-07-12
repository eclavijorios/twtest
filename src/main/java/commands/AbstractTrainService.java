package commands;


import service.TrainService;

public abstract class AbstractTrainService implements Command {

     private TrainService receiver;

    public AbstractTrainService(TrainService service){
        this.receiver = service;
    }

    public TrainService getReceiver() {
        return this.receiver;
    }
}
