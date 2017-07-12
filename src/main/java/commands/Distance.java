package commands;

import service.TrainService;

public class Distance extends AbstractStartDestinationCommand {
    private String[] villageNames;


    public Distance(TrainService service) {
        super(service);
    }


    public void setVillageNames(String[] villageNames) {
        this.villageNames = villageNames;
    }


    @Override
    public Integer execute() {
        return getReceiver().distance(villageNames);
    }

}
