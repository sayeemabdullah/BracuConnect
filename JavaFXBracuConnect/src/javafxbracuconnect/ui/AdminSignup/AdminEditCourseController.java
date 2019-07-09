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
import static javafx.collections.FXCollections.observableArrayList;
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
public class AdminEditCourseController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private TextField section;
    @FXML
    private Button update;
    @FXML
    private Button cancel;
    @FXML
    private ChoiceBox <String> daysdropdown;
    @FXML
    private ChoiceBox <String> timedropdown;
    Connection con;
    Statement stm;
    int rest;
    int res;
    int re; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(AdminEditCourseController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void addsection(ActionEvent event) {
    }

    @FXML
    private void updatetable(ActionEvent event) throws SQLException, IOException {
        //con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        //stm = con.createStatement();
        String Code = code.getText();
        String Section = section.getText();
        //String timeset = "SELECT TIME FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        String Days = daysdropdown.getValue();
        String Time = timedropdown.getValue();
  
        if (Code.isEmpty() || Section.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Both Course Code & Section");
            alert.showAndWait();
            return;
        }
        if (Days==null || Time==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Both Days & Time");
            alert.showAndWait();
            return;
        }
        String DuplicateChecker = "SELECT * FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            System.out.println("Done");
        }
        else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The course or the section doesnot exists.");
            alert.showAndWait();
            return;
        }

        
        /*String timeset = "SELECT TIME FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        ResultSet re = stm.executeQuery(timeset);
        if(re.next()) {
                 timeset = re.getString("time");
        }
        String dayset = "SELECT DAYS FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        ResultSet rest = stm.executeQuery(dayset);
        if(rest.next()) {
                 dayset = rest.getString("days");
        }
        //timedropdown.setValue(timeset);
        //daysdropdown.setValue(dayset);
        //String Days = daysdropdown.getValue();
        //String Time = timedropdown.getValue();
        System.out.println(Days);
        System.out.println(Time);
        String qu;
        if (Days==null && Time==null) {
            qu = "UPDATE SAYEEM.COURSE SET DEPARTMENT = '" + Department + "' WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        }/*
        /*else if(Days==null && Department.isEmpty()){
            qu = "UPDATE SAYEEM.COURSE SET TIME = '" + Time  +"' WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
            System.out.println(qu);
        }
        else if(Time==null && Department.isEmpty()){
            qu = "UPDATE SAYEEM.COURSE SET DAYS = '" + Days  +"' WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
            System.out.println(qu);
        }*/
        /*else{
            if(Days==null){
                
                String dayset = "SELECT DAYS FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
                ResultSet re = stm.executeQuery(dayset);
                while(re.next()) {
                     Days = re.getString("days");
                }
                Days = "Sunday"; // Bugs
            }
            if(Time==null){
                 Time = "2:00";
            }*/
            String qu = "UPDATE SAYEEM.COURSE SET DAYS = '" + Days  + "',TIME = '" + Time  +"' WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
            System.out.println(qu);
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
            stm = con.createStatement();
            res = stm.executeUpdate(qu);
            if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update Successful");
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
