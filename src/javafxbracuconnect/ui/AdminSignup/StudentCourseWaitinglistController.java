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
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class StudentCourseWaitinglistController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private ChoiceBox<String> courseEnrolled;
    @FXML
    private ChoiceBox<String> cgpacheck;
    @FXML
    private Button submit;
    @FXML
    private Button back;
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
            Logger.getLogger(StudentCourseWaitinglistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        courseEnrolled.setItems(observableArrayList("0","1","2","3","4","5"));
        cgpacheck.setItems(observableArrayList("<3","<3.5",">=3.5"));
    }    

    @FXML
    private void getcode(ActionEvent event) throws IOException {
    }

    @FXML
    private void donesubmitting(ActionEvent event) throws IOException, SQLException {
        StudentLogin login = new StudentLogin();
        String SID = login.getStudentID();
        String Code = code.getText().toUpperCase();
        System.out.println(Code);
        String DuplicateChecker = "SELECT * FROM SAYEEM.COURSEWAITINGLIST WHERE ID = '" + SID + "' AND CODE = '" + Code + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The course was already added in the waiting list by you.");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
            return;
        }
        String Enrolled = courseEnrolled.getValue();
        String CGPA = cgpacheck.getValue();
        if(Code.isEmpty() || Enrolled==null || CGPA==null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all the fields");
            alert.showAndWait();
            return;
        }
        if(Enrolled.equals("5")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You have already enrolled yourself in 5 courses.");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
            return;
        } else if((Enrolled.equals("4")) && (CGPA.equals("<3.5"))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You cannot enrolled yourself in 5 courses as your CGPA is less than 3.5 .");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
            return;
        } else if((Enrolled.equals("4")) && (CGPA.equals("<3"))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You cannot enrolled yourself in 5 courses as your CGPA is less than 3 .");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show(); 
            return;
    }
          //COURSEWAITINGLIST
          String qu = "INSERT INTO COURSEWAITINGLIST VALUES("
                + "'" + SID + "',"
                + "'" + Code + "'"
                + ")";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
        
        if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Addesd Successful to the waiting list. Admin will manage the rest and enroll you in the course if new section opens. Thank you.");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
            
        }

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
