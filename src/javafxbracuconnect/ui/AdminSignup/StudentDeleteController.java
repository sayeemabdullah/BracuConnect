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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sayee
 */
public class StudentDeleteController implements Initializable {

    @FXML
    private TextField studentID;
    @FXML
    private Button deletebutton;
    @FXML
    private Button cancelbutton;
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
            Logger.getLogger(StudentDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void getstudentID(ActionEvent event) {
    }

    @FXML
    private void StudentDelete(ActionEvent event) throws IOException, SQLException {
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        String StudentID = studentID.getText();
         if (StudentID.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Student ID");
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
        
        String qu = "DELETE FROM SAYEEM.STUDENT WHERE ID = '" + StudentID + "'";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
        
        if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Deleted Successful");
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
    private void gotoAdminDash(ActionEvent event) throws IOException {
            Parent adminDash = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    }
    
}
