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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class FacultyStudentAdvisingController implements Initializable {
    ObservableList <Student> slist = FXCollections.observableArrayList();
    ObservableList <AdvisingStudent> alist = FXCollections.observableArrayList();
    @FXML
    private TextField id;
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
    private Button updateadvising;
    @FXML
    private TableView<Student> studentinfo;
    @FXML
    private TableColumn<Student,String> name;
    @FXML
    private TableColumn<Student,String> dept;
    @FXML
    private TableColumn<Student,String> number;
    @FXML
    private TableColumn<Student,String> email;
    @FXML
    private TextField studentid;
    @FXML
    private Button load;
    @FXML
    private Button refresh;
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
    private Button facultydash;
    Connection con;
    Statement stm;
    ResultSet re; 
    int res;
    String SID;
    ResultSet rest;
    ResultSet rest2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(FacultyStudentAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initCol();
        try {
            fillComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(FacultyStudentAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    private void initCol() {
        name.setCellValueFactory(new PropertyValueFactory<>("sname"));
        dept.setCellValueFactory(new PropertyValueFactory<>("sdept"));
        email.setCellValueFactory(new PropertyValueFactory<>("semail"));
        number.setCellValueFactory(new PropertyValueFactory<>("snumber"));
        co1.setCellValueFactory(new PropertyValueFactory<>("sc1"));
        co2.setCellValueFactory(new PropertyValueFactory<>("sc2"));
        co3.setCellValueFactory(new PropertyValueFactory<>("sc3"));
        co4.setCellValueFactory(new PropertyValueFactory<>("sc4"));
        co5.setCellValueFactory(new PropertyValueFactory<>("sc5"));
    }
    
    private void StudentloadData() throws SQLException {
        
        String qu = "SELECT NAME , DEPARTMENT , EMAIL , PHONENUMBER  FROM SAYEEM.STUDENT WHERE ID = '" + SID + "'";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String stname = rest.getString("name");
                String stdept = rest.getString("department");
                String stemail = rest.getString("email");
                String stnumber = rest.getString("phonenumber");

                
                
                FacultyStudentAdvisingController.Student student = new FacultyStudentAdvisingController.Student (stname,stdept,stemail,stnumber);
                slist.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyStudentAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studentinfo.getItems().setAll(slist);
    }
    
    private void AdvisingStudentloadData() throws SQLException {
        
        String qu = "SELECT COURSE1 , COURSE2 , COURSE3 , COURSE4 , COURSE5  FROM SAYEEM.ADVISEDCOURSE WHERE ID = '" + SID + "'";
        System.out.println(qu);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest2 = stm.executeQuery(qu);
        
        try {
            while (rest2.next()) {
                String stc1 = rest2.getString("course1");
                String stc2 = rest2.getString("course2");
                String stc3 = rest2.getString("course3");
                String stc4 = rest2.getString("course4");
                String stc5 = rest2.getString("course5");
                
                
                FacultyStudentAdvisingController.AdvisingStudent advisingstudent = new FacultyStudentAdvisingController.AdvisingStudent (stc1,stc2,stc3,stc4,stc5);
                alist.add(advisingstudent);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyStudentAdvisingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        courseinfo.getItems().setAll(alist);
    }
    
    @FXML
    private void getid(ActionEvent event) {
    }


    @FXML
    private void doneadvising(ActionEvent event) throws SQLException, IOException {
        String StudentID = id.getText();
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
                Parent ssignin = FXMLLoader.load(getClass().getResource("FacultyStudentAdvising.fxml"));
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
                Parent ssignin = FXMLLoader.load(getClass().getResource("FacultyStudentAdvising.fxml"));
                Scene ssigninScene = new Scene(ssignin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("BracuConnect");
                window.setScene(ssigninScene);
                window.show();
         }
    }

    @FXML
    private void searchbyid(ActionEvent event) {
    }

    @FXML
    private void loadbystudentid(ActionEvent event) throws SQLException {
        SID = studentid.getText();
        if ( SID.isEmpty()) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Please Enter Student ID");
               alert.showAndWait();
               return;
           }
        String DuplicateChecker = "SELECT * FROM SAYEEM.STUDENT WHERE ID = '" + SID + "'";
           PreparedStatement pst = con.prepareStatement(DuplicateChecker);
           System.out.println(DuplicateChecker);
           ResultSet rs = pst.executeQuery();
           if (rs.next()) {
               System.out.println("ok");
           }
           else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Please Enter Existing Student's ID");
               alert.showAndWait();
               return;
           }
           
           AdvisingStudentloadData();
           StudentloadData();
    }

    @FXML
    private void gotoadvising(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyStudentAdvising.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML // GO TO FACULTY ADVISING PANEL
    private void gotofacultydash(ActionEvent event) throws IOException { 
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyAdvisingPanel.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
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
    private void gotoupdateadvising(ActionEvent event) throws IOException {
    
            Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyUpdateAdvisedCourse.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
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
    
     public class Student {

        private final SimpleStringProperty sname;
        private final SimpleStringProperty sdept;
        private final SimpleStringProperty semail;
        private final SimpleStringProperty snumber;
        

        public Student(String sname, String sdept, String semail, String snumber) {
            this.sname = new SimpleStringProperty(sname);
            this.sdept = new SimpleStringProperty(sdept);
            this.semail = new SimpleStringProperty(semail);
            this.snumber = new SimpleStringProperty(snumber);
            
        }


        public String getSname() {
            return sname.get();
        }
        public String getSdept() {
            return sdept.get();
        }
        public String getSemail() {
            return semail.get();
        }
        public String getSnumber() {
            return snumber.get();
        }
        
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
