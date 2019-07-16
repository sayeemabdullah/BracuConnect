/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxbracuconnect.ui.AdminSignup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
public class FacultySignUpController implements Initializable {

    @FXML
    private JFXButton signinbutton;
    @FXML
    private JFXTextField facultyname;
    @FXML
    private JFXPasswordField facultypass;
    @FXML
    private JFXPasswordField facultypasscheck;
    @FXML
    private JFXTextField facultyemail;
    @FXML
    private JFXButton signupbutton;
    @FXML
    private JFXTextField facultyinitial;
    @FXML
    private JFXTextField facultynumber;
    
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
    private void goToSignIn(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultySignIn.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void addname(ActionEvent event) {
    }

    @FXML
    private void addpass(ActionEvent event) {
    }

    @FXML
    private void checkpass(ActionEvent event) {
    }

    @FXML
    private void addemail(ActionEvent event) {
    }

    @FXML
    private void signup(ActionEvent event) throws SQLException, Exception {
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
        String DuplicateChecker2 = "SELECT * FROM SAYEEM.FACULTY WHERE EMAIL = '" + FacultyEmail + "'";
        PreparedStatement pst2 = con.prepareStatement(DuplicateChecker2);
        System.out.println(DuplicateChecker2);
        ResultSet rs2 = pst2.executeQuery();
        if (rs2.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An account already exists for this Faculty Email");
            alert.showAndWait();
            return;
        }
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
        String AdPd = facultypass.getText();
        if (AdPd.length()<4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The length of password must be more than 3 characters.");
            alert.showAndWait();
            return;
        }
        String AdRePd =facultypasscheck.getText();
        if(!AdPd.equals(AdRePd)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Password doesn't match");
            alert.showAndWait();
            return;
        }
        if (FacultyInitial.isEmpty() || FacultyName.isEmpty() || FacultyEmail.isEmpty() || FacultyNumber.isEmpty() || AdPd.isEmpty() || AdRePd.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        String FacultyPassword = Hash.getSaltedHash(AdPd);
        String qu = "INSERT INTO FACULTY VALUES("
                + "'" + FacultyInitial + "',"
                + "'" + FacultyName + "',"
                + "'" + FacultyEmail + "',"
                + "'" + FacultyNumber + "',"
                + "'" + FacultyPassword + "'"
                + ")";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
    
    if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Signup Successful");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("FacultySignIn.fxml"));
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
    
}
