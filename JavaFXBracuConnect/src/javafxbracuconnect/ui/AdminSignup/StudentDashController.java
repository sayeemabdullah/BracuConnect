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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sayee
 */
public class StudentDashController implements Initializable {

    @FXML
    private Button studentinfo;
    @FXML
    private Button studentadvisingpanel;
    @FXML
    private Button studentclassschedule;
    @FXML
    private Button studentcoursedetails;
    @FXML
    private Button studentsettings;
    @FXML
    private Button studentlogout;
    Connection con;
    Statement stm;
    ResultSet re; 
    int res;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(StudentDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void gotostudentinfo(ActionEvent event) throws IOException {
        StudentLogin login = new StudentLogin();
        System.out.println(login.getStudentID());
        Parent adminDash = FXMLLoader.load(getClass().getResource("StudentAboutMe.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void gotostudentadvisingpanel(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("StudentAdvisingPanel.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void gotostudentclassschedule(ActionEvent event) {
    }

    @FXML
    private void gotostudentcoursedetails(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("StudentCourseDetails.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void gotostudentsettings(ActionEvent event) {
    }

    @FXML
    private void gotohomepage(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
}
