package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Model.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController  {
    @FXML
    private JFXButton btnPatient, btnDoctor;

    //Constructor
    public MainPageController(){

    }

    //Method to open respective login pages on button press
    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent ae) {
        if (ae.getSource() == btnDoctor) {
            FxmlLoader.loadStage("/views/Doctor/DoctorLogin.fxml"); //loads doctor login page
            ((Node)(ae.getSource())).getScene().getWindow().hide(); //hides the main selection page
        } else if (ae.getSource() == btnPatient) {
            FxmlLoader.loadStage("/views/Patient/PatientLogin.fxml"); //loads patients login page
            ((Node)(ae.getSource())).getScene().getWindow().hide();
        }
    }


}
