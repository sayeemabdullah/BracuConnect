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
 *
 * @author sayeem
 */
public class SignUpController implements Initializable {

    @FXML
    public JFXButton signinbutton;
    public JFXTextField adminname;
    public JFXPasswordField adminpass;
    public JFXPasswordField adminpasscheck;
    public JFXTextField adminemail;
    public JFXButton signupbutton;

    Connection con;
    Statement stm;
    int res;
    //Connection con = null;
    //Statement stm = null;
    //BracuConnect buc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //buc = new BracuConnect();
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException e) {
        }
    }

    @FXML
    private void goToSignIn(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("AdminSignin.fxml"));
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
    private void signup(ActionEvent event) throws SQLException, IOException, Exception {

        String AdminName = adminname.getText();
        String AdminEmail = adminemail.getText();
        String DuplicateChecker = "SELECT * FROM SAYEEM.ADMIN WHERE EMAIL = '" + AdminEmail + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An account already exists for this email address");
            alert.showAndWait();
            return;
        }
        //String AdminPassword = Hash.getSaltedHash(adminpass.getText());
        //String AdminRePassword = Hash.getSaltedHash(adminpasscheck.getText());
        String AdPd = adminpass.getText();
        String AdRePd =adminpasscheck.getText();
        if (AdminName.isEmpty() || AdminEmail.isEmpty() || AdPd.isEmpty() || AdRePd.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all the fields");
            alert.showAndWait();
            return;
        }
        if (AdPd.length()<4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The length of password must be more than 3 characters.");
            alert.showAndWait();
            return;
        }
        if(!AdPd.equals(AdRePd)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Password doesn't match");
            alert.showAndWait();
            return;
        }
         String AdminPassword = Hash.getSaltedHash(AdPd);
        
        String qu = "INSERT INTO ADMIN VALUES("
                + "'" + AdminName + "',"
                + "'" + AdminEmail + "',"
                + "'" + AdminPassword + "'"
                + ")";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
        
        if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Signup Successful");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("AdminSignin.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
            
        }
        ///if (res != 0) {
            //Parent adminDash = FXMLLoader.load(getClass().getResource("name.fxml"));
            //Scene adminDashScene = new Scene(adminDash);
          //  Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //}

        //stm.execute(qu);
        //int executeUpdate = stm.executeUpdate(qu)
        // buc.execute(qu);
        //if ( stm.execute(qu)) {
        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setHeaderText(null);
        //alert.setContentText("You have Signup Successfully");
        //alert.showAndWait();
    }

}
