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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class StudentAdvisingPanelController implements Initializable {
    
    ObservableList <AdvisingStudent> alist = FXCollections.observableArrayList();
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
    private TableView<AdvisingStudent> courseinfo;
    @FXML
    private TableColumn<AdvisingStudent,String> co1;
    @FXML
    private TableColumn<AdvisingStudent,String> co2;
    @FXML
    private TableColumn<AdvisingStudent,String> co3;
    @FXML
    private TableColumn<AdvisingStudent,String> co4;
    @FXML
    private TableColumn<AdvisingStudent,String> co5;
    @FXML
    private Button studentdash;
    Connection con;
    Statement stm;
    ResultSet re,rest; 
    int res;
    String SID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(StudentAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initCol();
        try {
            fillComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(StudentAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            AdvisingStudentloadData();
        } catch (SQLException ex) {
            Logger.getLogger(StudentAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        private void initCol() {
        co1.setCellValueFactory(new PropertyValueFactory<>("sc1"));
        co2.setCellValueFactory(new PropertyValueFactory<>("sc2"));
        co3.setCellValueFactory(new PropertyValueFactory<>("sc3"));
        co4.setCellValueFactory(new PropertyValueFactory<>("sc4"));
        co5.setCellValueFactory(new PropertyValueFactory<>("sc5"));
    }
        
    private void AdvisingStudentloadData() throws SQLException {
        StudentLogin login = new StudentLogin();
        System.out.println(login.getStudentID());
        SID = login.getStudentID();
        
        String qu = "SELECT COURSE1 , COURSE2 , COURSE3 , COURSE4 , COURSE5  FROM SAYEEM.ADVISEDCOURSE WHERE ID = '" + SID + "'";
        System.out.println(qu);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        re = stm.executeQuery(qu);
        
        try {
            while (re.next()) {
                String stc1 = re.getString("course1");
                String stc2 = re.getString("course2");
                String stc3 = re.getString("course3");
                String stc4 = re.getString("course4");
                String stc5 = re.getString("course5");
                
                
                StudentAdvisingPanelController.AdvisingStudent advisingstudent = new StudentAdvisingPanelController.AdvisingStudent (stc1,stc2,stc3,stc4,stc5);
                alist.add(advisingstudent);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        courseinfo.getItems().setAll(alist);
    }

    @FXML
    private void doneadvising(ActionEvent event) throws IOException, SQLException {
        StudentLogin login = new StudentLogin();
        System.out.println(login.getStudentID());
        String StudentID = login.getStudentID();
        if ( StudentID.isEmpty()) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Please Enter Student ID");
               alert.showAndWait();
               return;
        }
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
        String DuplicateChecker1 = "SELECT * FROM SAYEEM.ADVISEDCOURSE WHERE ID = '" + StudentID + "'";
        PreparedStatement pst1 = con.prepareStatement(DuplicateChecker1);
        System.out.println(DuplicateChecker1);
        rest = pst1.executeQuery();
        if (rest.next()) {    
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The Student has already been advised. Update if you want to advise again.");
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
        
        String qu = "INSERT INTO ADVISEDCOURSE VALUES("
                + "'" + StudentID + "',"
                + "'" + C01 + "',"
                + "'" + C02 + "',"
                + "'" + C03 + "',"
                + "'" + C04 + "',"
                + "'" + C05 + "'"
                + ")";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        res = stm.executeUpdate(qu);
         if (res != 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Advising Successful");
                alert.showAndWait();
                Parent ssignin = FXMLLoader.load(getClass().getResource("StudentAdvisingPanel.fxml"));
                Scene ssigninScene = new Scene(ssignin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(ssigninScene);
                window.show();
        }
         else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Advising Unsuccessful.Please update advising as advising was done once.");
                alert.showAndWait();
                Parent ssignin = FXMLLoader.load(getClass().getResource("StudentAdvisingPanel.fxml"));
                Scene ssigninScene = new Scene(ssignin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(ssigninScene);
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
    private void gotoupdateadvising(ActionEvent event) throws IOException {
                Parent ssignin = FXMLLoader.load(getClass().getResource("StudentUpdateAdvising.fxml"));
                Scene ssigninScene = new Scene(ssignin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(ssigninScene);
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

    
     public class AdvisingStudent {

        private final SimpleStringProperty sc1;
        private final SimpleStringProperty sc2;
        private final SimpleStringProperty sc3;
        private final SimpleStringProperty sc4;
        private final SimpleStringProperty sc5;
        

        public AdvisingStudent(String sc1, String sc2, String sc3, String sc4, String sc5) {
            this.sc1 = new SimpleStringProperty(sc1);
            this.sc2 = new SimpleStringProperty(sc2);
            this.sc3 = new SimpleStringProperty(sc3);
            this.sc4 = new SimpleStringProperty(sc4);
            this.sc5 = new SimpleStringProperty(sc5);
            
        }

        public String getSc1() {
            return sc1.get();
        }

        public String getSc2() {
            return sc2.get();
        }

        public String getSc3() {
            return sc3.get();
        }

        public String getSc4() {
            return sc4.get();
        }

        public String getSc5() {
            return sc5.get();
        }


        }
}
