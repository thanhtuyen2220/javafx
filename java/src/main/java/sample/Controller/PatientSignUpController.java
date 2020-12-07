package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sample.Model.FxmlLoader;
import sample.Model.Selector;
import sample.Utils.PasswordValidator;
import sample.Utils.SQLDatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientSignUpController implements Initializable {
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    @FXML
    private JFXTextField txtPhoneNumber, txtUsername, txtLastName, txtFirstName,txtAddress;
    @FXML
    private JFXPasswordField txtPassword, txtCPassword;
    @FXML
    private JFXComboBox<String> dropDown;
    @FXML
    private JFXButton btnSignUp,btnSignIn;
    @FXML
    private DatePicker txtDob;
    @FXML
    private Label lblErrors;

    ///Constructor call on opening page
    public PatientSignUpController() {
        try {
            con = SQLDatabaseConnection.getConnection();  //establishing connection to database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent ae) throws SQLException {
        if (ae.getSource() == btnSignIn) {    ///button to go back to signin page
            if (con != null) {
                con.close();
            }
            FxmlLoader.loadStage("/views/Patient/patientLogin.fxml");
            ((Node) (ae.getSource())).getScene().getWindow().hide();
        }
        else if (ae.getSource() == btnSignUp){
            createPressed();
        }
    }

    //button is pressed => validates all data fields and performs data insertion
    private void createPressed() throws SQLException {
        //check if all fields are filled
        if (dropDown.getSelectionModel().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtDob.getValue().toString().equals("")
                || txtPhoneNumber.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || txtCPassword.getText().isEmpty()) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Enter all details");
        }
        //check if password and confirm password is equal
        else if (!txtPassword.getText().equals(txtCPassword.getText())) {
            txtPassword.clear();
            txtCPassword.clear();
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Passwords donot match!");
        }

        //check if username already exists in database
        else if(checkUsername(txtUsername.getText())){
            txtUsername.clear();
            txtPassword.clear();
            txtCPassword.clear();
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Username Exists!");
        }

        //create the account if all above conditions are fulfilled
        else {
            String verify = saveData();
            if (verify.equals("Success")){
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    //Method to check if same username exists
    private boolean checkUsername(String text) throws SQLException {
        String sql = "SELECT * FROM security_user Where username = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, text);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return true;
        }finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }


    //method to take the data entered and create an account
    private String saveData() throws SQLException {
        try {
            String sql = "INSERT INTO security_user(username,password,age,address,full_name) VALUES (?,?,?,?,?)";
            String patientComplain = dropDown.getValue();
            String coressDept= Selector.setDepartment(patientComplain);
            Integer year = txtDob.getValue().getYear();
            year = 2020 - year;
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, txtUsername.getText());
            preparedStatement.setString(2, txtPassword.getText());
            preparedStatement.setString(3,String.valueOf(year));
            preparedStatement.setString(4,txtAddress.getText());
            preparedStatement.setString(5, txtFirstName.getText() + " " + txtLastName.getText());

            preparedStatement.executeUpdate();

            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Added Successfully");
            reset();

            //*********to show the patient which Doctor has been assigned*********//
           /* String sql = "SELECT * FROM doctors Where department = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, coressDept);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String doctorName = resultSet.getString(3);
                    String message = "Thanks for Registering. You have been Assigned to Dr. " +doctorName+ " of "+coressDept+ " Department." +
                            " Your Appointment Has Been Set For 2 P.M. today. Please SIGN-IN to check your Report after Appoinment.";
                    showToast(message);
                } else {
//                    lblSaved.setTextFill(Color.TOMATO);
//                    lblSaved.setText("No Doctor found");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //******************************************************************************************/

            return "Success";
        } catch (SQLException ex) {
            ex.printStackTrace();
            if(ex.toString().contains("Incorrect integer")){
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText("Please Enter Valid Phone Number");
            }else {
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText(ex.getMessage());
            }
            return "Exception";
        }finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    //method to clear all fields that has data entered
    private void reset() {
        dropDown.setValue(null);
        txtFirstName.clear();
        txtLastName.clear();
        txtDob.setValue(null);
        txtPhoneNumber.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtCPassword.clear();

    }


    public void initialize(URL location, ResourceBundle resources) {
        ///Add Complains to dropdown...
        dropDown.getItems().clear();
        dropDown.getItems().addAll(Selector.getCOMPLAINS());

        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");  //let user know error in establishing connection to database
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");  //let the user know connection to database is established
        }

        ///Register on pressing enter on Keyboard
        txtCPassword.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                try {
                    createPressed();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //
    }
}
