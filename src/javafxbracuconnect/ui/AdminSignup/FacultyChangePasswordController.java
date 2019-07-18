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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class FacultyChangePasswordController implements Initializable {

    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private PasswordField curpass;
    @FXML
    private PasswordField newpass;
    @FXML
    private PasswordField confpass;
    Connection con;
    Statement stm;
    ResultSet re1,re2,re3; 
    int res,res1,res2,res3;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(FacultyChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void doneupdate(ActionEvent event) throws SQLException, Exception {
        FacultyLogin login = new FacultyLogin();
        String Fmail = login.getFacultyEmail();
        String FacultyPass = curpass.getText();
        String FacultyNewPass = newpass.getText();
        String FacultyReNewPass = confpass.getText();
        String passo = null;
        String passo2 = null;
        String passo3 = null;
        if (FacultyPass.isEmpty() || FacultyNewPass.isEmpty() || FacultyReNewPass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        System.out.println(Fmail);
        String query = "SELECT * FROM SAYEEM.FACULTY WHERE EMAIL='" + Fmail + "'";
        PreparedStatement pst = con.prepareStatement(query);
        System.out.println(query);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            passo = rs.getString(5); //Using Password
            System.out.println("Using Password :"+passo);
            if (Hash.check(FacultyPass,passo)) {
                System.out.println("Done");
            }
            else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Correct Password");
            alert.showAndWait();
            return;
            }
        }

        if (FacultyNewPass.length()<4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The length of password must be more than 3 characters.");
            alert.showAndWait();
            return;
        }
        if(!FacultyNewPass.equals(FacultyReNewPass)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The New Password doesn't match.");
            alert.showAndWait();
        }
        if (Hash.check(FacultyNewPass,passo)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please use different password as you have already using this password.");
                alert.showAndWait();
                return;
            }
        
        String hnewpass = Hash.getSaltedHash(FacultyNewPass);
        String query3 = "SELECT * FROM SAYEEM.FACULTYPASSWORD WHERE EMAIL='" + Fmail + "'";
        PreparedStatement pst3 = con.prepareStatement(query3);
        System.out.println(query3);
        ResultSet rs2 = pst3.executeQuery();
        if (rs2.next()) {
            passo2 = rs2.getString("currentpassword"); //"Using Password :"+
            passo3 = rs2.getString("previouspassword"); //Previous Password
            System.out.println("Using Password :"+passo2);
            System.out.println("Previous Password :"+passo3);
            }
        if((Hash.check(FacultyNewPass,passo2))|| (Hash.check(FacultyNewPass,passo3))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please use different password as you have already used this password in the past.");
                alert.showAndWait();
                Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyChangePassword.fxml"));
                Scene adminDashScene = new Scene(adminDash);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(adminDashScene);
                window.show();
                return;
        }
        String qu1 = "UPDATE SAYEEM.FACULTY SET PASSWORD = '" + hnewpass + "' WHERE EMAIL = '" + Fmail + "'";
        String qu2 = "UPDATE SAYEEM.FACULTYPASSWORD SET CURRENTPASSWORD = '" + hnewpass + "' WHERE EMAIL = '" + Fmail + "'";
        String qu3 = "UPDATE SAYEEM.FACULTYPASSWORD SET PREVIOUSPASSWORD = '" + passo2 + "' WHERE EMAIL = '" + Fmail + "'";
        System.out.println(qu1);
        System.out.println(qu2);
        System.out.println(qu3);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res1 = stm.executeUpdate(qu1);
        res2 = stm.executeUpdate(qu2);
        res3 = stm.executeUpdate(qu3);
        if ((res1 != 0) && (res2 != 0) && (res3 != 0)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Update Successful");
                alert.showAndWait();
                Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyDash.fxml"));
                Scene adminDashScene = new Scene(adminDash);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(adminDashScene);
                window.show();
                }
                else{
                System.out.println("Unsucessful");
                }
    }

    @FXML
    private void goback(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyDash.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show(); 
    }

    @FXML
    private void getcurpass(ActionEvent event) {
    }

    @FXML
    private void getnewpass(ActionEvent event) {
    }

    @FXML
    private void getconfpass(ActionEvent event) {
    }
    
}
