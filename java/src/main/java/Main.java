import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Utils.SQLDatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            String doctorDashboard = "/views/doctorDashboard.fxml";
            String patientLogin ="views/Patient/patientLogin.fxml";
            String mainPage = "views/Patient/mainPage.fxml";

            Parent root = FXMLLoader.load(getClass().getResource("/views/Doctor/DoctorDashBoard.fxml"));
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(root, 1200, 700));
            primaryStage.getIcons().add(new Image("media/icon.png"));

            primaryStage.getScene().getStylesheets().addAll(getClass().getResource("style/style.css").toExternalForm());

            ////**********Move Window on Mouse Drag anywhere on the screen*****/////
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            //move around here
            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });
            primaryStage.show();

            //testing
           /* Connection con = SQLDatabaseConnection.getConnection();
            String sql = "select * from BANGXH";
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(sql);
            int id = res.getRow();
            System.out.print(id);*/
            primaryStage.show();
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
