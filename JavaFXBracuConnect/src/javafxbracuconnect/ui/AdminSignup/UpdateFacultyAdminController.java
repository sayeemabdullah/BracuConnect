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
public class UpdateFacultyAdminController implements Initializable {

    @FXML
    private JFXTextField facultyname;
    @FXML
    private JFXTextField facultyemail;
    @FXML
    private JFXButton updatebutton;
    @FXML
    private JFXTextField facultyinitial;
    @FXML
    private JFXTextField facultynumber;
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
            Logger.getLogger(UpdateFacultyAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void addname(ActionEvent event) {
    }

    @FXML
    private void addemail(ActionEvent event) {
    }

    @FXML
    private void facultyupdate(ActionEvent event) throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        String FacultyInitial = facultyinitial.getText();
        String DuplicateChecker = "SELECT * FROM SAYEEM.FACULTY WHERE INITIAL = '" + FacultyInitial + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An account already exists for this Faculty Initial");
            alert.showAndWait();
            return;
        }
        String FacultyName = facultyname.getText();
        String FacultyEmail = facultyemail.getText();
        String FacultyNumber = facultynumber.getText();
        String DuplicateChecker3 = "SELECT * FROM SAYEEM.FACULTY WHERE PHONENUMBER = '" + FacultyNumber + "'";
        PreparedStatement pst3 = con.prepareStatement(DuplicateChecker3);
        System.out.println(DuplicateChecker3);
        ResultSet rs3 = pst3.executeQuery();
        if (rs3.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An account already exists for this Faculty Contact Number");
            alert.showAndWait();
            return;
        }
        
         if (FacultyInitial.isEmpty() && FacultyName.isEmpty() && FacultyEmail.isEmpty() && FacultyNumber.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Faculty Member's Email Address and the field(s) you want to update. ");
            alert.showAndWait();
            return;
        }
         else if (FacultyInitial.isEmpty() && FacultyName.isEmpty() && FacultyNumber.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter the field(s) you want to update. ");
            alert.showAndWait();
            return;
        }
         else if(FacultyEmail.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Faculty Member's Email Address.");
            alert.showAndWait();
            return;
         }
        
        String DuplicateChecker2 = "SELECT * FROM SAYEEM.FACULTY WHERE EMAIL = '" + FacultyEmail + "'";
        PreparedStatement pst2 = con.prepareStatement(DuplicateChecker2);
        System.out.println(DuplicateChecker2);
        ResultSet rs2 = pst2.executeQuery();
        if (rs2.next()) {
            System.out.println("ok");
        }
        else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Existing Faculty's Email Address");
            alert.showAndWait();
            return;
        }

        if(FacultyInitial.isEmpty() && FacultyNumber.isEmpty()){
            qu = "UPDATE SAYEEM.FACULTY SET NAME = '" + FacultyName + "' WHERE EMAIL = '" + FacultyEmail + "'";
        }
        else if(FacultyName.isEmpty() && FacultyNumber.isEmpty()){
            qu = "UPDATE SAYEEM.FACULTY SET INITIAL = '" + FacultyInitial + "' WHERE EMAIL = '" + FacultyEmail + "'";
        }
        else if(FacultyName.isEmpty() && FacultyInitial.isEmpty()){
            qu = "UPDATE SAYEEM.FACULTY SET PHONENUMBER = '" + FacultyNumber + "' WHERE EMAIL = '" + FacultyEmail + "'";
        }
        else{
        qu = "UPDATE SAYEEM.FACULTY SET INITIAL = '" + FacultyInitial + "',NAME = '" + FacultyName + "',PHONENUMBER = '" + FacultyNumber + "' WHERE EMAIL = '" + FacultyEmail + "'";
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
    private void addinitial(ActionEvent event) {
    }

    @FXML
    private void addnumber(ActionEvent event) {
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
