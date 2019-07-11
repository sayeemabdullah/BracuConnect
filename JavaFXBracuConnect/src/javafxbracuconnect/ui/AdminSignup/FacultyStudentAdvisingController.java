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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class FacultyStudentAdvisingController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private ComboBox<?> c1;
    @FXML
    private ComboBox<?> c2;
    @FXML
    private ComboBox<?> c3;
    @FXML
    private ComboBox<?> c4;
    @FXML
    private ComboBox<?> c5;
    @FXML
    private Button updateadvising;
    @FXML
    private TableView<?> studentinfo;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> dept;
    @FXML
    private TableColumn<?, ?> number;
    @FXML
    private TableColumn<?, ?> email;
    @FXML
    private TextField studentid;
    @FXML
    private Button load;
    @FXML
    private Button refresh;
    @FXML
    private TableView<?> courseinfo;
    @FXML
    private TableColumn<?, ?> co1;
    @FXML
    private TableColumn<?, ?> co2;
    @FXML
    private TableColumn<?, ?> co3;
    @FXML
    private TableColumn<?, ?> co4;
    @FXML
    private TableColumn<?, ?> co5;
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
            Logger.getLogger(FacultyStudentAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void getid(ActionEvent event) {
    }

    @FXML
    private void getc1(ActionEvent event) {
    }

    @FXML
    private void getc2(ActionEvent event) {
    }

    @FXML
    private void getc3(ActionEvent event) {
    }

    @FXML
    private void getc4(ActionEvent event) {
    }

    @FXML
    private void getc5(ActionEvent event) {
    }

    @FXML
    private void doneadvising(ActionEvent event) {
    }

    @FXML
    private void searchbyid(ActionEvent event) {
    }

    @FXML
    private void loadbystudentid(ActionEvent event) {
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
    private void gotofacultydash(ActionEvent event) throws IOException { // GO TO FACULTY ADVISING PANEL
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyAdvisingPanel.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
}
