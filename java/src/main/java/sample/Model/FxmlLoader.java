package sample.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FxmlLoader {
    private static String fxmlId = "";
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void loadStage(String fxml) {
        try {
            fxmlId = fxml;

            Class currentClass = new Object() {}.getClass().getEnclosingClass();
            Parent root = FXMLLoader.load(currentClass.getResource(fxmlId));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if(fxmlId.contains("DoctorLogin")){
                stage.setTitle("Login: DOCTOR");
            }else if(fxmlId.contains("PatientLogin")){
                stage.setTitle("Login: PATIENT");
            }else if(fxml.contains("MainPage")) {
                stage.setTitle("Welcome");
            }else if (fxml.contains("DoctorDashBoard")) {
                stage.setTitle("Doctor: Dashboard");
            }else if(fxml.contains("PatientSignUp")){
                stage.setTitle("Patient Registration");
            }else if(fxml.contains("PatientDashboard")) {
                stage.setTitle("Patient: DashBoard");
            }

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("media/icon.png"));
            stage.getScene().getStylesheets().addAll(currentClass.getResource("/style/style.css").toExternalForm());

            ////**********Move Window on Mouse Drag anywhere on the screen - Not implemented now*****/////
            stage.initStyle(StageStyle.TRANSPARENT);
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            //move around here
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            stage.show();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}