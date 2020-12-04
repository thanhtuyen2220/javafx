package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sample.Model.FxmlLoader;
import sample.Model.SessionSaver;
import sample.Utils.SQLDatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientLoginController implements Initializable {
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label lblErrors;

    @FXML
    private JFXButton btnSignin,btnSignup,btnMain;

    public PatientLoginController(){
        try {
            con = SQLDatabaseConnection.getConnection();  //establishing connection to database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void HandleButtonClicks(javafx.event.ActionEvent event) throws SQLException {
        if (event.getSource() == btnMain) {
            if (con != null) {
                con.close();
            }
            FxmlLoader.loadStage("/views/Doctor/MainPage.fxml");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else if (event.getSource() == btnSignup) {
            if (con != null) {
                con.close();
            }
            FxmlLoader.loadStage("/views/Patient/PatientSignUp.fxml");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else if (event.getSource() == btnSignin) {
            if (Login().equals("Success")) {
                if (con != null) {
                    con.close();
                }
                FxmlLoader.loadStage("/views/Patient/PatientDashBoard.fxml");
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }

        }
    }
    private String Login() throws SQLException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String sql = "SELECT * FROM SECURITY_USER WHERE USERNAME=? AND PASSWORD=?";
        try{
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText("Enter Correct Username/Password");
                txtUsername.clear();
                txtPassword.clear();
                return "Error";
            }else {
                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Login Successful..Redirecting..");
                SessionSaver.setUsername(txtUsername.getText());
                return "Success";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Exception";
        } finally {
            if(resultSet!=null){
                resultSet.close();
            }
            if(preparedStatement !=null){
                preparedStatement.close();
            }
        }
    }

    public void initialize(URL location, ResourceBundle resource){
        if(con == null){
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error: Check");
        } else{
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }

        txtPassword.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                try{
                    if(Login().equals("Success")){
                        if(con != null){
                            con.close();
                        }
                        FxmlLoader.loadStage("/views/Patient/PatientDashBoard.fxml");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

}
