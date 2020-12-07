package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sample.Model.FxmlLoader;
import sample.Model.ModelTable;
import sample.Model.Selector;
import sample.Model.SessionSaver;
import sample.Utils.SQLDatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorLoginController implements Initializable {
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
    private JFXButton btnSignin, btnMain;
    @FXML
    private JFXComboBox<String> dropDept;
    @FXML
    private TreeTableView<ModelTable> treeTableView;
    @FXML
    private TreeTableColumn<ModelTable, String> nameCol, diagnosisCol, cureCol;

    ///Constructor call on opening page
    public DoctorLoginController() {
        try {
            con = SQLDatabaseConnection.getConnection();  //establishing connection to database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeApp() throws SQLException {
        if (con != null) {
            con.close();
        }
        System.exit(0);
    }

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent ae) throws SQLException {
        if (ae.getSource() == btnMain) {   ///button event to go back to main page
            if (con != null) {
                con.close();
            }
            FxmlLoader.loadStage("/views/Doctor/mainPage.fxml");
            ((Node) (ae.getSource())).getScene().getWindow().hide();
        } else if (ae.getSource() == btnSignin) {    ////button to check signin and go to doctor's dashboard
            if (logIn().equals("Success")) {
                if (con != null) {
                    con.close();
                }
                FxmlLoader.loadStage("/views/Doctor/medicines.fxml");
                ((Node) (ae.getSource())).getScene().getWindow().hide();
            }
        }
    }

    //method to check login
    private String logIn() throws SQLException {

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String department = dropDept.getValue();

        String sql = "SELECT * FROM security_user Where username = ? and password = ? ";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

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
    }


    public void initialize(URL location, ResourceBundle resources) {
        //Add Departments to dropdown...
        dropDept.getItems().clear();
        dropDept.getItems().addAll(Selector.getDEPTS());

        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");  //let user know error in establishing connection to database
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");  //let the user know connection to database is established
        }
        TreeItem<ModelTable> selectedItem = treeTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.getValue();
        }
        showDetails(selectedItem);
        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showDetails(newValue);
        });

        ///SignIn on pressing enter on Keyboard
        txtPassword.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                try {
                    if (logIn().equals("Success")) {
                        if (con != null) {
                            con.close();
                        }
                        FxmlLoader.loadStage("/views/Doctor/DoctorDashBoard.fxml");
                        ((Node) (e.getSource())).getScene().getWindow().hide();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //
    }

    private void showDetails(TreeItem<ModelTable> selectedItem) {
    }
}
