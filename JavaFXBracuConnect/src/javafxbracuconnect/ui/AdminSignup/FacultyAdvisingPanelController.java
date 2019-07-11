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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class FacultyAdvisingPanelController implements Initializable {

    @FXML
    private TableView<?> allstudentcoursetable;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> c1;
    @FXML
    private TableColumn<?, ?> c2;
    @FXML
    private TableColumn<?, ?> c3;
    @FXML
    private TableColumn<?, ?> c4;
    @FXML
    private TableColumn<?, ?> c5;
    @FXML
    private Button advising;
    @FXML
    private Button facultydash;
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
            Logger.getLogger(FacultyAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void gotoadvising(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyStudentAdvising.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void gotofacultydash(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyDash.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
}
