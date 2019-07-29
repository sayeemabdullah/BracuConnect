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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class StudentUpdateAdvisingController implements Initializable {

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
    @FXML
    private Button cancel;
    Connection con;
    Statement stm;
    ResultSet re; 
    int res;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(StudentUpdateAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fillComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(StudentUpdateAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void doneupdateadvising(ActionEvent event) throws SQLException, IOException {
        StudentLogin login = new StudentLogin();
        System.out.println(login.getStudentID());
        String StudentID = login.getStudentID();
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
        //start
        String SeatChecker = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + C01 + "'";
        PreparedStatement psts = con.prepareStatement(SeatChecker);
        System.out.println(SeatChecker);
        ResultSet rss = psts.executeQuery();
        if (rss.next()) {    
            String seatCounter = rss.getString("SEATS");
            int finalSeat = Integer.parseInt(seatCounter);
            if(finalSeat<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Advise different course. There is no more seat left.");
                alert.showAndWait();
                return;
            }
            else{
                finalSeat = finalSeat - 1;
                String NewSeatValue = Integer.toString(finalSeat);
                
               String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue + "' WHERE COURSE = '" + C01 + "'";
               con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
               stm = con.createStatement();
               res = stm.executeUpdate(qula);
            }
        }
        else{

        }
        //end
        String C02 = c2.getValue();
        if((C01!=null && C02!=null) && C01.equals(C02)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
        //start
        String SeatChecker1 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + C02 + "'";
        PreparedStatement psts1 = con.prepareStatement(SeatChecker1);
        System.out.println(SeatChecker1);
        ResultSet rss1 = psts1.executeQuery();
        if (rss1.next()) {    
            String seatCounter = rss1.getString("SEATS");
            int finalSeat = Integer.parseInt(seatCounter);
            if(finalSeat<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Advise different course. There is no more seat left.");
                alert.showAndWait();
                return;
            }
            else{
                finalSeat = finalSeat - 1;
                String NewSeatValue = Integer.toString(finalSeat);
                
               String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue + "' WHERE COURSE = '" + C02 + "'";
               con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
               stm = con.createStatement();
               res = stm.executeUpdate(qula);
            }
        }
        else{

        }
        //end
        String C03 = c3.getValue();
        if((C01!=null && C02!=null && C03!=null) && ((C01.equals(C03)) || (C02.equals(C03)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
        //start
        String SeatChecker2 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + C03 + "'";
        PreparedStatement psts2 = con.prepareStatement(SeatChecker2);
        System.out.println(SeatChecker2);
        ResultSet rss2 = psts2.executeQuery();
        if (rss2.next()) {    
            String seatCounter = rss2.getString("SEATS");
            int finalSeat = Integer.parseInt(seatCounter);
            if(finalSeat<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Advise different course. There is no more seat left.");
                alert.showAndWait();
                return;
            }
            else{
                finalSeat = finalSeat - 1;
                String NewSeatValue = Integer.toString(finalSeat);
                
               String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue + "' WHERE COURSE = '" + C03 + "'";
               con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
               stm = con.createStatement();
               res = stm.executeUpdate(qula);
            }
        }
        else{

        }
        //end
        String C04 = c4.getValue();
         if((C01!=null && C02!=null && C03!=null && C04!=null) && ((C01.equals(C04)) || (C02.equals(C04)) || (C03.equals(C04)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
          //start
        String SeatChecker3 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + C04 + "'";
        PreparedStatement psts3 = con.prepareStatement(SeatChecker3);
        System.out.println(SeatChecker3);
        ResultSet rss3 = psts3.executeQuery();
        if (rss3.next()) {    
            String seatCounter = rss3.getString("SEATS");
            int finalSeat = Integer.parseInt(seatCounter);
            if(finalSeat<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Advise different course. There is no more seat left.");
                alert.showAndWait();
                return;
            }
            else{
                finalSeat = finalSeat - 1;
                String NewSeatValue = Integer.toString(finalSeat);
                
               String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue + "' WHERE COURSE = '" + C04 + "'";
               con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
               stm = con.createStatement();
               res = stm.executeUpdate(qula);
            }
        }
        else{

        }
        //end
        String C05 = c5.getValue();
        if((C01!=null && C02!=null && C03!=null && C04!=null && C05!=null) && ((C01.equals(C05)) || (C02.equals(C05)) || (C03.equals(C05))|| (C04.equals(C05)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Choose Different Courses. You Cannot advise the same course twice.");
            alert.showAndWait();
            return;
        }
         //start
        String SeatChecker4 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + C05 + "'";
        PreparedStatement psts4 = con.prepareStatement(SeatChecker4);
        System.out.println(SeatChecker4);
        ResultSet rss4 = psts4.executeQuery();
        if (rss4.next()) {    
            String seatCounter = rss4.getString("SEATS");
            int finalSeat = Integer.parseInt(seatCounter);
            if(finalSeat<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Advise different course. There is no more seat left.");
                alert.showAndWait();
                return;
            }
            else{
                finalSeat = finalSeat - 1;
                String NewSeatValue = Integer.toString(finalSeat);
                
               String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue + "' WHERE COURSE = '" + C05 + "'";
               con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
               stm = con.createStatement();
               res = stm.executeUpdate(qula);
            }
        }
        else{

        }
        //end
        String qu = "UPDATE SAYEEM.ADVISEDCOURSE SET COURSE1 = '" + C01 + "',COURSE2 = '" + C02 + "',COURSE3 = '" + C03 + "',COURSE4 = '" + C04 + "',COURSE5 = '" + C05 + "' WHERE ID = '" + StudentID + "'";
        System.out.println(qu);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
                //start
        String Checker = "SELECT * FROM SAYEEM.ADVISEDCOURSE WHERE ID = '" + StudentID + "'";
        PreparedStatement pstst = con.prepareStatement(Checker);
        System.out.println(Checker);
        ResultSet rsst = pstst.executeQuery();
        if (rsst.next()){
            String c01 = rsst.getString("COURSE1");
            String c02 = rsst.getString("COURSE2");
            String c03 = rsst.getString("COURSE3");
            String c04 = rsst.getString("COURSE4");
            String c05 = rsst.getString("COURSE5");
        if(!c01.equals(null)){
            String SeatChecker12 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + c01 + "'";
            PreparedStatement psts12 = con.prepareStatement(SeatChecker12);
            System.out.println(SeatChecker12);
            ResultSet rss12 = psts12.executeQuery();
            if (rss12.next()) {    
            String seatCounter1 = rss12.getString("SEATS");
            int finalSeat1 = Integer.parseInt(seatCounter1);
            finalSeat1 = finalSeat1 + 1;
            String NewSeatValue1 = Integer.toString(finalSeat1);
            String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue1 + "' WHERE COURSE = '" + C01 + "'";
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
            stm = con.createStatement();
            res = stm.executeUpdate(qula);      
            }
            else{
            }
        }
         else{
        
        }
        if(!c02.equals(null)){
            String SeatChecker12 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + c02 + "'";
            PreparedStatement psts12 = con.prepareStatement(SeatChecker12);
            System.out.println(SeatChecker12);
            ResultSet rss12 = psts12.executeQuery();
            if (rss12.next()) {    
            String seatCounter1 = rss12.getString("SEATS");
            int finalSeat1 = Integer.parseInt(seatCounter1);
            finalSeat1 = finalSeat1 + 1;
            String NewSeatValue1 = Integer.toString(finalSeat1);
            String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue1 + "' WHERE COURSE = '" + C02 + "'";
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
            stm = con.createStatement();
            res = stm.executeUpdate(qula);      
            }
            else{
            }
        }
         else{
        
        }
        if(!c03.equals(null)){
            String SeatChecker12 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + c03 + "'";
            PreparedStatement psts12 = con.prepareStatement(SeatChecker12);
            System.out.println(SeatChecker12);
            ResultSet rss12 = psts12.executeQuery();
            if (rss12.next()) {    
            String seatCounter1 = rss12.getString("SEATS");
            int finalSeat1 = Integer.parseInt(seatCounter1);
            finalSeat1 = finalSeat1 + 1;
            String NewSeatValue1 = Integer.toString(finalSeat1);
            String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue1 + "' WHERE COURSE = '" + C03 + "'";
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
            stm = con.createStatement();
            res = stm.executeUpdate(qula);      
            }
            else{
            }
        }
         else{
        
        }
        if(!c04.equals(null)){
            String SeatChecker12 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + c04 + "'";
            PreparedStatement psts12 = con.prepareStatement(SeatChecker12);
            System.out.println(SeatChecker12);
            ResultSet rss12 = psts12.executeQuery();
            if (rss12.next()) {    
            String seatCounter1 = rss12.getString("SEATS");
            int finalSeat1 = Integer.parseInt(seatCounter1);
            finalSeat1 = finalSeat1 + 1;
            String NewSeatValue1 = Integer.toString(finalSeat1);
            String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue1 + "' WHERE COURSE = '" + C04 + "'";
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
            stm = con.createStatement();
            res = stm.executeUpdate(qula);      
            }
            else{
            }
        }
         else{
        
        }
        if(!c05.equals(null)){
            String SeatChecker12 = "SELECT * FROM SAYEEM.COURSESEATCOUNT WHERE COURSE = '" + c05 + "'";
            PreparedStatement psts12 = con.prepareStatement(SeatChecker12);
            System.out.println(SeatChecker12);
            ResultSet rss12 = psts12.executeQuery();
            if (rss12.next()) {    
            String seatCounter1 = rss12.getString("SEATS");
            int finalSeat1 = Integer.parseInt(seatCounter1);
            finalSeat1 = finalSeat1 + 1;
            String NewSeatValue1 = Integer.toString(finalSeat1);
            String qula = "UPDATE SAYEEM.COURSESEATCOUNT SET SEATS = '" + NewSeatValue1 + "' WHERE COURSE = '" + C05 + "'";
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
            stm = con.createStatement();
            res = stm.executeUpdate(qula);      
            }
            else{
            }
        }
         else{
        
        }
        }
        else{
        
        }
        //end
        res = stm.executeUpdate(qu);
         if (res != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update Successful");
            alert.showAndWait();
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentAdvisingPanel.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
        }
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
                Parent ssignin = FXMLLoader.load(getClass().getResource("StudentAdvisingPanel.fxml"));
                Scene ssigninScene = new Scene(ssignin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(ssigninScene);
                window.show();
    }
    
}
