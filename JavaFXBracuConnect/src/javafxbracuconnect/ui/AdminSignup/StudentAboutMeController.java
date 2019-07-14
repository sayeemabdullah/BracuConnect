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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class StudentAboutMeController implements Initializable {

    Connection con;
    Statement stm;
    ResultSet re; 
    int res;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label dept;
    @FXML
    private Label email;
    @FXML
    private Label number;
    @FXML
    private Button editinfo;
    @FXML
    private Button studentdash;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(StudentAboutMeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            SetLabelStudent();
        } catch (SQLException ex) {
            Logger.getLogger(StudentAboutMeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
               
    
    private void SetLabelStudent() throws SQLException{
        StudentLogin login = new StudentLogin();
        String SID = login.getStudentID();
        System.out.println(SID);
        String SNAME = null;
        String SDEP = null;
        String SEMAIL = null;
        String SNUMBER = null;
        id.setText(SID);
        String qu = "SELECT NAME , EMAIL ,PHONENUMBER , DEPARTMENT FROM SAYEEM.STUDENT WHERE ID = '" + SID + "'";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
         re = stm.executeQuery(qu);
         if (re.next()) {
                    SNAME= re.getString("name");
                    SDEP= re.getString("department");
                    SEMAIL = re.getString("email");
                    SNUMBER = re.getString("phonenumber");
                    //System.out.println("name = "+SNAME);
         }
         name.setText(SNAME);
         dept.setText(SDEP);
         email.setText(SEMAIL);
         number.setText(SNUMBER);
}

    @FXML
    private void gotoeditinfo(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("StudentEditInformation.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void gotostudentdash(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
}
