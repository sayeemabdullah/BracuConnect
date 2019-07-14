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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class AdminSigninController implements Initializable {

    @FXML
    private JFXButton signupbutton;
    @FXML
    private JFXPasswordField adminpass;
    @FXML
    private JFXTextField adminemail;
    @FXML
    private JFXButton signinbutton;
    @FXML
    private Text main;
    
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
            Logger.getLogger(AdminSigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void gotosignup(ActionEvent event) throws IOException {
            Parent adminDash = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void signin(ActionEvent event) throws SQLException, Exception {
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        String AdminEmail = adminemail.getText();
        AdminLogin login = new AdminLogin();
        //String duplicateCheck = "SELECT EMAIL ='"+ AdminEmail + "' GROUP BY EMAIL HAVING ( COUNT(EMAIL) = 1 )";
        String AdminPass = adminpass.getText();
        if (AdminEmail.isEmpty() || AdminPass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all the fields");
            alert.showAndWait();
            return;
        }
        String query = "SELECT * FROM SAYEEM.ADMIN WHERE EMAIL='" + AdminEmail + "'";
        PreparedStatement pst = con.prepareStatement(query);
        System.out.println(query);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            String passo = rs.getString(3);
            if (Hash.check(AdminPass,passo)) {
                Parent signIn = FXMLLoader.load(getClass().getResource("AdminDash.fxml"));
                Scene signInScene = new Scene(signIn);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(signInScene);
                window.show();
                System.out.println("Done");
                System.out.println(adminemail.getText() + " " + adminpass.getText() + " " + Hash.getSaltedHash(adminpass.getText()));
            }
            else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Correct Password");
            alert.showAndWait();
            return;
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Correct Email Address");
            alert.showAndWait();
            return;
            }
        login.setAdminEmail(AdminEmail);
        System.out.println(login.getAdminEmail());
    }

    @FXML
    private void gotomain(MouseEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
}
