package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.Model.DoctorDashboardFieldsReturn.DoctorDashboardFieldsReturn;
import sample.Model.FxmlLoader;
import sample.Model.ModelTable;
import sample.Utils.SQLDatabaseConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorDashBoardController implements Initializable {
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ObservableList<ModelTable> list;

    @FXML
    private Label lblName,lblErrors;

    @FXML
    private JFXButton btnMain;

    @FXML
    private TreeTableView<ModelTable> treeTableView;

    @FXML
    private TreeTableColumn<ModelTable,String> nameCol,curedCol,diagnosisCol;

    public DoctorDashBoardController(){
        try {
            con = SQLDatabaseConnection.getConnection();  //establishing connection to database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent ae) throws SQLException {
        if (ae.getSource() == btnMain) {     //Button to log out and go to main page
            if (con != null) {
                con.close();
            }
            FxmlLoader.loadStage("/views/Doctor/mainPage.fxml");
            ((Node) (ae.getSource())).getScene().getWindow().hide();
        }
    }

    ArrayList<DoctorDashboardFieldsReturn> retrievedFieldsForTreeTableColumn() throws SQLException {
        String SQL = "select full_name, diseases from medical_record, security_user, role_mapper where role_mapper.role_id = 2 AND security_user.security_id = medical_record.SECURITY_ID_PATIENT group by security_user.full_name";

        Statement stmt = null;
        con = SQLDatabaseConnection.getConnection();

        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = stmt.executeQuery(SQL);

        ArrayList<DoctorDashboardFieldsReturn> patientList = new ArrayList<>();

        while(resultSet.next()){
            String Pname = resultSet.getString(1);
            String diseases = resultSet.getString(2);
            String cured = "No";
            patientList.add(new DoctorDashboardFieldsReturn(Pname, diseases));
        }
        return patientList;
    }

    private void populateTable() throws SQLException {

//        nameCol.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
//                diagnosisCol.setCellValueFactory(param -> param.getValue().getValue().diagnosisProperty());
//        curedCol.setCellValueFactory(param -> param.getValue().getValue().curedProperty());

//        nameCol.setCellFactory(TextFieldTreeTableCell.<ModelTable>forTreeTableColumn());
//        diagnosisCol.setCellFactory(TextFieldTreeTableCell.<ModelTable>forTreeTableColumn());
//        curedCol.setCellFactory(TextFieldTreeTableCell.<ModelTable>forTreeTableColumn());

        //Instantiate the Observable list
        list = FXCollections.observableArrayList();

        //Fill the table with data
//        TreeItem<ModelTable> root = new RecursiveTreeItem<ModelTable>(list, RecursiveTreeObject::getChildren);
//        treeTableView.setRoot(root);
//        treeTableView.setShowRoot(false);

//            //Take data from database and feed to list
//            String username = SessionSaver.getUsername();
//            String department = SessionSaver.getDepartment();

        ArrayList<DoctorDashboardFieldsReturn> fieldsToDisplay = new ArrayList<>() ;
        try {
            fieldsToDisplay = retrievedFieldsForTreeTableColumn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        treeTableView.setPrefWidth(400);

        for (int i = 0; i < fieldsToDisplay.size(); i++){
            String pName = fieldsToDisplay.get(i).getPname();
            String diseases = fieldsToDisplay.get(i).getDiagnosis();
            String cured = fieldsToDisplay.get(i).getCured();
            //list.add(new ModelTable(pName,diseases,cured));
            nameCol = new TreeTableColumn<>(pName);
            diagnosisCol = new TreeTableColumn<>(diseases);
            curedCol = new TreeTableColumn<>(cured);

            treeTableView.getColumns().add(nameCol);
            treeTableView.getColumns().add(diagnosisCol);
            treeTableView.getColumns().add(curedCol);
        }

        // Create the VBox
        VBox root = new VBox(treeTableView);

//            String sql = "SELECT * FROM patients Where department = ?";
//            try {
//                preparedStatement = con.prepareStatement(sql);
//                preparedStatement.setString(1, department);
//                resultSet = preparedStatement.executeQuery();
//                while(resultSet.next()) {
//                    String pName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
//                    list.add(new ModelTable(pName, resultSet.getString("report"), resultSet.getString("disease"),  ///Push the data collected from database to ModelTable
//                            resultSet.getString("medicine"), resultSet.getString("cured")));                       //and store in list
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }finally {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//            }

        //call to database to retrieve the name of Doctor who logged in                      //Retrieve the data from ModelTable and set the data into table cells
//                nameCol.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
//                diagnosisCol.setCellValueFactory(param -> param.getValue().getValue().diagnosisProperty());
        //cureCol.setCellValueFactory(param -> param.getValue().getValue().curedProperty());



        String sql_2 = "SELECT * FROM security_user Where username = ?";

//            try {
//                preparedStatement = con.prepareStatement(sql);
//                preparedStatement.setString(1, username);
//                resultSet = preparedStatement.executeQuery();
//                if (resultSet.next()) {
//                    String Name = resultSet.getString("full_name");
//                    lblName.setText(Name);
//                } else {
//                    lblErrors.setTextFill(Color.TOMATO);
//                    lblErrors.setText("Cannot Retrieve Data. Server Error");
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }finally {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");  //let user know error in establishing connection to database
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up, All Good!");  //let the user know connection to database is established
            try {

                populateTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
        //Cho cua minh lam

//        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            showDetails(newValue);
//            btnEdit.setDisable(false);
//            editableFields(false);
//            showButtons(false);
//            lblEdit.setText("");
//            TreeItem<ModelTable> selectedItem = treeTableView.getSelectionModel().getSelectedItem();
//            if(selectedItem.getValue().getCured().equals("Yes")){
//                btnDel.setDisable(false);
//                btnEdit.setDisable(true);
//            }
//        });



    }

