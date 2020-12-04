package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.Model.MakeAppointmentFields.MakeAppointmentFieldsToReturn;
import sample.Utils.SQLDatabaseConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientAppointmentController implements Initializable {
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private Label lblErrors;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton appointment;
    @FXML
    private JFXComboBox<String> drname;
    @FXML
    private JFXComboBox<Time> availTimeCbox;

    ///Constructor call on opening page
    public PatientAppointmentController() {
        try {
            con = SQLDatabaseConnection.getConnection();  //establishing connection to database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    /*public void handleButtonClicks(javafx.event.ActionEvent ae) throws SQLException {
        if (ae.getSource() == btnMain) {   ///button event to go back to main page
            if (con != null) {
                con.close();
            }
            FxmlLoader.loadStage("/views/mainPage.fxml");
            ((Node) (ae.getSource())).getScene().getWindow().hide();
        } else if (ae.getSource() == btnSignin) {    ////button to check signin and go to doctor's dashboard
            if (logIn().equals("Success")) {
                if (con != null) {
                    con.close();
                }
                FxmlLoader.loadStage("/views/doctorDashboard.fxml");
                ((Node) (ae.getSource())).getScene().getWindow().hide();
            }
        }
    }*/

    //method to check login
    /*private String logIn() throws SQLException {

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String department = dropDept.getValue();

        String sql = "SELECT * FROM doctors Where username = ? and password = ? and department = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, department);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText("Enter Correct Username/Password/Department");
                txtUsername.clear();
                txtPassword.clear();
                dropDept.setValue(null);
                return "Error";
            } else {
                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Login Successful..Redirecting..");
                SessionSaver.setUsername(txtUsername.getText());   ////save the successful login's username
                SessionSaver.setDepartment(dropDept.getValue());    /////save the successful login's department
                return "Success";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Exception";
        }finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }*/

    public ArrayList<MakeAppointmentFieldsToReturn> retrievedDocs() throws SQLException {
        String SQL ="SELECT FULL_NAME,START_TIME FROM security_user JOIN in_hospital_doctor ON security_user.SECURITY_ID = in_hospital_doctor.SECURITY_ID_DOCTOR where in_hospital_doctor.IS_AVAILABLE = 1";

        Statement stmt = null;
        con = SQLDatabaseConnection.getConnection();

        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        resultSet = stmt.executeQuery(SQL);

        ArrayList<MakeAppointmentFieldsToReturn> docList = new ArrayList<>();


        while(resultSet.next()){
            String fname = resultSet.getString(1);
            Time time = resultSet.getTime(2);
            docList.add(new MakeAppointmentFieldsToReturn(fname, time));
        }

        return docList;

    }
    public void initialize(URL location, ResourceBundle resources) {
        //Add Departments to dropdown...
        drname.getItems().clear();
        //String[] names = {"abc","lang bam", "bac si meomeo"};
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Time> times = new ArrayList<>();
        ArrayList<MakeAppointmentFieldsToReturn> makeAppointmentFieldsToReturns = new ArrayList<>();
        try {
            makeAppointmentFieldsToReturns = retrievedDocs();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for(int i = 0; i < makeAppointmentFieldsToReturns.size(); i++){
            names.add(makeAppointmentFieldsToReturns.get(i).getDoctorsName());
            times.add(makeAppointmentFieldsToReturns.get(i).getTime());
        }

        drname.getItems().addAll(names);
        availTimeCbox.getItems().addAll(times);

        //Setup logic for triggering
        /*dropDown.setOnAction(e -> {
            double value = Double.parseDouble(input.getText());
            double convertedValue = dropDown.getValue().convert(value);
            output.setText(String.format("%.2f", convertedValue));
        });*/
//        if (con == null) {
//            lblErrors.setTextFill(Color.TOMATO);
//            lblErrors.setText("Server Error : Check");  //let user know error in establishing connection to database
//        } else {
//            lblErrors.setTextFill(Color.GREEN);
//            lblErrors.setText("Server is up : Good to go");  //let the user know connection to database is established
//        }


    }
}
