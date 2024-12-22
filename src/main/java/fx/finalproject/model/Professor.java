package fx.finalproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Professor {
    private final SimpleStringProperty profId;
    private final SimpleStringProperty profName;

    public Professor(String profId, String profName) {
        this.profId = new SimpleStringProperty(profId);
        this.profName = new SimpleStringProperty(profName);
    }
    public String getProfId(){
        return profId.getValue();
    }
    public void setProfName(String profName){
        this.profName.setValue(profName);
    }

    public String getProfName() {
        return profName.getValue();
    }

}
