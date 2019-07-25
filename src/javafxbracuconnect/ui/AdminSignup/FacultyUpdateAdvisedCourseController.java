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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class FacultyUpdateAdvisedCourseController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private Button updateadvising;
    @FXML
    private ComboBox<String> c1;
    @FXML
    private ComboBox<String> c2;
    @FXML
    private ComboBox<String> c3;
    @FXML
    private ComboBox<String> c4;
    @FXML
    private ComboBox<String> c5;
    Connection con;
    Statement stm;
    ResultSet re; 
    int res;
    @FXML
    private Button cancel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(FacultyUpdateAdvisedCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fillComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(FacultyUpdateAdvisedCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void getid(ActionEvent event) {
    }


    @FXML
    private void setc1(ActionEvent event) {
    }

    @FXML
    private void setc2(ActionEvent event) {
    }

    @FXML
    private void setc3(ActionEvent event) {
    }

    @FXML
    private void setc4(ActionEvent event) {
    }

    @FXML
    private void setc5(ActionEvent event) {
    }

    @FXML
    private void doneupdateadvising(ActionEvent event) throws IOException, SQLException {
        String StudentID = id.getText();
        System.out.println(StudentID);
        String DuplicateChecker = "SELECT * FROM SAYEEM.STUDENT WHERE ID = '" + StudentID + "'";
        PreparedStatement pst = con.prepareStatement(DuplicateChecker);
        System.out.println(DuplicateChecker);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {    
            
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Student ID doesn't exists.");
            alert.showAndWait();
            return;

        }

        String C01 = c1.getValue();
        if(C01==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose the courses you want to advise.");
            alert.showAndWait();
            return;
        }
        String C02 = c2.getValue();
        if((C01!=null && C02!=null) && C01.equals(C02)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
        String C03 = c3.getValue();
        if((C01!=null && C02!=null && C03!=null) && ((C01.equals(C03)) || (C02.equals(C03)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
        String C04 = c4.getValue();
         if((C01!=null && C02!=null && C03!=null && C04!=null) && ((C01.equals(C04)) || (C02.equals(C04)) || (C03.equals(C04)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
        String C05 = c5.getValue();
        if((C01!=null && C02!=null && C03!=null && C04!=null && C05!=null) && ((C01.equals(C05)) || (C02.equals(C05)) || (C03.equals(C05))|| (C04.equals(C05)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
        String qu = "UPDATE SAYEEM.ADVISEDCOURSE SET COURSE1 = '" + C01 + "',COURSE2 = '" + C02 + "',COURSE3 = '" + C03 + "',COURSE4 = '" + C04 + "',COURSE5 = '" + C05 + "' WHERE ID = '" + StudentID + "'";
        System.out.println(qu);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
         if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update Successful");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyStudentAdvising.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
        }
    }
    
     public void fillComboBox() throws SQLException{
        String qu = "SELECT CODE , SECTION FROM COURSE";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        re = stm.executeQuery(qu);
        while(re.next()){
            String courseSelect = re.getString("CODE");
            String sectionSelect = re.getString("SECTION");
            String FinalValueSelect = courseSelect +" ("+sectionSelect+")";
            System.out.println(FinalValueSelect);
            c1.getItems().add(FinalValueSelect);
            c2.getItems().add(FinalValueSelect);
            c3.getItems().add(FinalValueSelect);
            c4.getItems().add(FinalValueSelect);
            c5.getItems().add(FinalValueSelect);
        }   
    }

    @FXML
    private void goback(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyStudentAdvising.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
}
