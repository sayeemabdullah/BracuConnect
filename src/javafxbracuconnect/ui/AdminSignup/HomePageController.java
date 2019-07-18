/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package javafxbracuconnect.ui.AdminSignup;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sayee
 */
public class HomePageController implements Initializable {

    @FXML
    private JFXButton adminbtn;
    @FXML
    private JFXButton facultybtn;
    @FXML
    private JFXButton studentbtn;
    Connection con;
    Statement stm;
    int res;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException e) {
        }
    }    

    @FXML
    private void adminsignin(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("AdminSignin.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void facultysignin(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultySignIn.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void studentsignin(ActionEvent event) throws IOException {
        Parent ssignin = FXMLLoader.load(getClass().getResource("StudentSignIn.fxml"));
        Scene ssigninScene = new Scene(ssignin);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(ssigninScene);
        window.show();
    }
    
}
