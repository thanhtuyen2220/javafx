package sample.Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelTable extends RecursiveTreeObject<ModelTable> {
    private StringProperty name, diagnosis, cured;

    public ModelTable(String name, String diagnosis, String cured) {
        this.name = new SimpleStringProperty(name) ;
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.cured = new SimpleStringProperty(cured) ;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public StringProperty diagnosisProperty() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public String getCured() {
        return cured.get();
    }

    public StringProperty curedProperty() {
        return cured;
    }

    public void setCured(String cured) {
        this.cured.set(cured);
    }
}
