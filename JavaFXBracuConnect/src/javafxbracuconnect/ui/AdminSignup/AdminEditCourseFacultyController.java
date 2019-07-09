/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxbracuconnect.ui.AdminSignup;

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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class AdminEditCourseFacultyController implements Initializable {

    @FXML
    private JFXTextField code;
    @FXML
    private JFXTextField section;
    @FXML
    private JFXTextField faculty;
    @FXML
    private Button cancel;
    @FXML
    private Button update;
    Connection con;
    Statement stm;
    int res;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(AdminEditCourseFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void getcode(ActionEvent event) {
    }

    @FXML
    private void getsection(ActionEvent event) {
    }

    @FXML
    private void getfaculty(ActionEvent event) {
    }

    @FXML
    private void gotocoursedash(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("AdminCourseManagement.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void updatefaculty(ActionEvent event) throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
         String Code = code.getText();
         String Section = section.getText();
         String DuplicateChecker = "SELECT * FROM SAYEEM.COURSE WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        String Name = faculty.getText();
         if (Code.isEmpty() || Name.isEmpty() || Section.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all the fields");
            alert.showAndWait();
            return;
        }
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
         
        String qu = "UPDATE SAYEEM.COURSE SET FACULTY = '" + Name + "' WHERE CODE = '" + Code + "' AND SECTION = '" + Section + "'";
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
    
}
