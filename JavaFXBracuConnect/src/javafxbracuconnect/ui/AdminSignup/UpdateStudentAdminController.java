/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxbracuconnect.ui.AdminSignup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sayee
 */
public class UpdateStudentAdminController implements Initializable {

    @FXML
    private JFXTextField studentname;
    @FXML
    private JFXTextField studentemail;
    @FXML
    private JFXButton updatebutton;
    @FXML
    private JFXTextField studentid;
    @FXML
    private JFXTextField studentnumber;
    @FXML
    private JFXTextField studentdept;
    @FXML
    private JFXButton cancelbutton;
    Connection con;
    Statement stm;
    int res;
    String qu;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStudentAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void addname(ActionEvent event) {
    }

    @FXML
    private void addemail(ActionEvent event) {
    }

    @FXML
    private void studentupdate(ActionEvent event) throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        String StudentID = studentid.getText();
        String StudentName = studentname.getText();
        String StudentEmail = studentemail.getText();
        String DuplicateChecker2 = "SELECT * FROM SAYEEM.STUDENT WHERE EMAIL = '" + StudentEmail + "'";
        PreparedStatement pst2 = con.prepareStatement(DuplicateChecker2);
        System.out.println(DuplicateChecker2);
        ResultSet rs2 = pst2.executeQuery();
        if (rs2.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An account already exists for this Student Email");
            alert.showAndWait();
            return;
        }
        String StudentNumber = studentnumber.getText();
        String DuplicateChecker3 = "SELECT * FROM SAYEEM.STUDENT WHERE PHONENUMBER = '" + StudentNumber + "'";
        PreparedStatement pst3 = con.prepareStatement(DuplicateChecker3);
        System.out.println(DuplicateChecker3);
        ResultSet rs3 = pst3.executeQuery();
        if (rs3.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An account already exists for this Student Contact Number");
            alert.showAndWait();
            return;
        }
        String StudentDept = studentdept.getText();
        
        
        if(StudentID.isEmpty() && StudentName.isEmpty() && StudentNumber.isEmpty() && StudentEmail.isEmpty() && StudentDept.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Student ID and the field(s) you want to update");
            alert.showAndWait();
            return;
        }
        else if(StudentName.isEmpty() && StudentNumber.isEmpty() && StudentEmail.isEmpty() && StudentDept.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter the field(s) you want to update");
            alert.showAndWait();
            return;
        }
        else if (StudentID.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Student's ID");
            alert.showAndWait();
            return;
        }
        String DuplicateChecker = "SELECT * FROM SAYEEM.STUDENT WHERE ID = '" + StudentID + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            System.out.println("ok");
        }
        else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Existing Student's ID");
            alert.showAndWait();
            return;
        }
        if(StudentEmail.isEmpty() && StudentNumber.isEmpty() && StudentDept.isEmpty()){
        qu = "UPDATE SAYEEM.STUDENT SET NAME = '" + StudentName + "' WHERE ID = '" + StudentID + "'";
        }
        else if(StudentName.isEmpty() && StudentNumber.isEmpty() && StudentDept.isEmpty()){
        qu = "UPDATE SAYEEM.STUDENT SET EMAIL = '" + StudentEmail + "' WHERE ID = '" + StudentID + "'";
        }
        else if(StudentName.isEmpty() && StudentEmail.isEmpty() && StudentDept.isEmpty()){
        qu = "UPDATE SAYEEM.STUDENT SET PHONENUMBER = '" + StudentNumber + "' WHERE ID = '" + StudentID + "'";
        }
        else if(StudentName.isEmpty() && StudentNumber.isEmpty() && StudentEmail.isEmpty()){
        qu = "UPDATE SAYEEM.STUDENT SET DEPARTMENT = '" + StudentDept + "' WHERE ID = '" + StudentID + "'";
        }
        else{
        qu = "UPDATE SAYEEM.STUDENT SET NAME = '" + StudentName + "',EMAIL = '" + StudentEmail + "',PHONENUMBER = '" + StudentNumber + "',DEPARTMENT = '" + StudentDept + "' WHERE ID = '" + StudentID + "'";
        }   
        System.out.println(qu);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
         if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update Successful");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
        }
    }

    @FXML
    private void addid(ActionEvent event) {
    }

    @FXML
    private void addnumber(ActionEvent event) {
    }

    @FXML
    private void adddept(ActionEvent event) {
    }

    @FXML
    private void gotoadmindash(ActionEvent event) throws IOException {
            Parent adminDash = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    }
    
}
