/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxbracuconnect.ui.AdminSignup;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class AdminAddCourseController implements Initializable {
    
    //ObservableList <String> timedropdownlist = FXCollections.observableArrayList("8:00","9:30");
    @FXML
    private TextField code;
    @FXML
    private TextField dept;
    @FXML
    private TextField facultyname;
    @FXML
    private TextField section;
    @FXML
    private Button add;
    @FXML
    private Button cancel;
    Connection con;
    Statement stm;
    int res;
    @FXML
    private ChoiceBox daysdropdown;
    @FXML
    private ChoiceBox timedropdown;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(AdminAddCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        timedropdown.setItems(observableArrayList("8:00","9:30","11:00","12:30","2:00","3:30","5:00"));
        timedropdown.setTooltip(new Tooltip("Select Class Starting Time"));
        daysdropdown.setItems(observableArrayList("Sunday & Tuesday","Monday & Wednesday",
                "Thursday & Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Saturday"));
        daysdropdown.setTooltip(new Tooltip("Select Class Days"));
    }
    @FXML
    private void addcode(ActionEvent event) {
    
    }

    @FXML
    private void adddept(ActionEvent event) {
    }

    @FXML
    private void addfacultyname(ActionEvent event) {
    }

    @FXML
    private void addsection(ActionEvent event) {
    }


    @FXML
    private void updatetable(ActionEvent event) throws SQLException, IOException {
    String Code = code.getText();
    String Department = dept.getText();
    String Name = facultyname.getText();
    String Section = section.getText();
            String DuplicateChecker = "SELECT * FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The section already exists.");
            alert.showAndWait();
            return;
        }
        if (Code.isEmpty() || Department.isEmpty() || Name.isEmpty() || Section.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all the fields");
            alert.showAndWait();
            return;
        }
            String Days = daysdropdown.getValue().toString();
            String Time = timedropdown.getValue().toString();

    String qu = "INSERT INTO COURSE VALUES("
                + "'" + Code + "',"
                + "'" + Department + "',"
                + "'" + Section + "',"
                + "'" + Name + "',"
                + "'" + Days + "',"
                + "'" + Time + "'"
                + ")";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
        
        if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Added Successful");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("AdminCourseManagement.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
            
        }
    }

    @FXML
    private void gotocourse(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("AdminCourseManagement.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    }
    
}
