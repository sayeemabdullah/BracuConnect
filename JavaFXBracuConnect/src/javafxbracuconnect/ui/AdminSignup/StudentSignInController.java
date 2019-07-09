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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sayee
 */
public class StudentSignInController implements Initializable {

    @FXML
    private JFXButton signupbutton;
    @FXML
    private JFXPasswordField studentpass;
    @FXML
    private JFXTextField studentid;
    @FXML
    private JFXButton signinbutton;
    Connection con;
    Statement stm;
    int res;
    @FXML
    private Text main;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException e) {
        }
    }    

    @FXML
    private void gotosignup(ActionEvent event) throws IOException {
        Parent ssignup = FXMLLoader.load(getClass().getResource("StudentSignUp.fxml"));
        Scene ssignupScene = new Scene(ssignup);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(ssignupScene);
        window.show();
    }

    @FXML
    private void passpw(ActionEvent event) {
    }

    @FXML
    private void passid(ActionEvent event) {
    }

    @FXML
    private void signin(ActionEvent event) throws SQLException, Exception {
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        String StudentID = studentid.getText();
        //String duplicateCheck = "SELECT EMAIL ='"+ AdminEmail + "' GROUP BY EMAIL HAVING ( COUNT(EMAIL) = 1 )";
        String StudentPass = studentpass.getText();
        if (StudentID.isEmpty() || StudentPass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all the fields");
            alert.showAndWait();
            return;
        }
        String query = "SELECT * FROM SAYEEM.STUDENT WHERE ID='" + StudentID + "'";
        PreparedStatement pst = con.prepareStatement(query);
        System.out.println(query);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            String passo = rs.getString(6);
            if (Hash.check(StudentPass,passo)) {
                Parent signIn = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
                Scene signInScene = new Scene(signIn);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(signInScene);
                window.show();
                System.out.println("Done");
                System.out.println(studentid.getText() + " " + studentpass.getText() + " " + Hash.getSaltedHash(studentpass.getText()));
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
            alert.setContentText("Please Enter Correct Student ID");
            alert.showAndWait();
            return;
            }
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
