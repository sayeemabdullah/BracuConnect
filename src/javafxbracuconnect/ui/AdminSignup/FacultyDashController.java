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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sayee
 */
public class FacultyDashController implements Initializable {
    
    //ObservableList<Student> slist = FXCollections.observableArrayList();
    ObservableList <Student> slist = FXCollections.observableArrayList();
    @FXML
    private Button logout;
    @FXML
    private Button advisingpanel;
    @FXML
    private TextField Searchid;
    @FXML
    private Button searchbtn;

    Connection con;
    Statement stm;
    ResultSet rest;
    int res;
    @FXML
    private TableColumn<Student,String> studentid;
    @FXML
    private TableColumn<Student,String> studentname;
    @FXML
    private TableColumn<Student,String> studentemail;
    @FXML
    private TableColumn<Student,String> studentnumber;
    @FXML
    private TableColumn<Student,String> studentdpt;
    @FXML
    private TableView<Student> studenttable;
    String SID;
    @FXML
    private Button refreshbtn;
    @FXML
    private Button showbtn;
    @FXML
    private Button changepassword;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(FacultyDashController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initCol();
        try {
            StudentloadData();
        } catch (SQLException ex) {
            Logger.getLogger(FacultyDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    private void initCol() {
    studentid.setCellValueFactory(new PropertyValueFactory<>("sid"));
    studentname.setCellValueFactory(new PropertyValueFactory<>("sname"));
    studentemail.setCellValueFactory(new PropertyValueFactory<>("semail"));
    studentnumber.setCellValueFactory(new PropertyValueFactory<>("snumber"));
    studentdpt.setCellValueFactory(new PropertyValueFactory<>("sdept"));
    }
    private void Student2loadData() throws SQLException {
        
        String qu = "SELECT ID , NAME , EMAIL , PHONENUMBER , DEPARTMENT FROM SAYEEM.STUDENT";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String stid = rest.getString("id");
                String stname = rest.getString("name");
                String stemail = rest.getString("email");
                String stnumber = rest.getString("phonenumber");
                String stdept = rest.getString("department");
                
                
                FacultyDashController.Student student = new FacultyDashController.Student (stid,stname,stemail,stnumber,stdept);
                slist.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studenttable.getItems().setAll(slist);
    }
    private void StudentloadData() throws SQLException {
        
        String qu = "SELECT ID , NAME , EMAIL , PHONENUMBER , DEPARTMENT FROM SAYEEM.STUDENT WHERE ID = '" + SID + "'";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String stid = rest.getString("id");
                String stname = rest.getString("name");
                String stemail = rest.getString("email");
                String stnumber = rest.getString("phonenumber");
                String stdept = rest.getString("department");
                
                
                FacultyDashController.Student student = new FacultyDashController.Student (stid,stname,stemail,stnumber,stdept);
                slist.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studenttable.getItems().setAll(slist);
    }
    @FXML
    private void gotoroot(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }


    @FXML
    private void gotostudentadvisingpanel(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyAdvisingPanel.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void getid(ActionEvent event) {
    }

    @FXML
    private void searchid(ActionEvent event) throws SQLException {
     SID = Searchid.getText();
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
     StudentloadData();
    }

    @FXML
    private void restart(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyDash.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

    @FXML
    private void showstudent(ActionEvent event) throws SQLException {
    Student2loadData();
    }

    @FXML
    private void gotochangepassword(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyChangePassword.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show(); 
    }
    
    public class Student {

        private final SimpleStringProperty sid;
        private final SimpleStringProperty sname;
        private final SimpleStringProperty semail;
        private final SimpleStringProperty snumber;
        private final SimpleStringProperty sdept;

        public Student(String sid, String sname, String semail, String snumber, String sdept) {
            this.sid = new SimpleStringProperty(sid);
            this.sname = new SimpleStringProperty(sname);
            this.semail = new SimpleStringProperty(semail);
            this.snumber = new SimpleStringProperty(snumber);
            this.sdept = new SimpleStringProperty(sdept);
        }

        public String getSid() {
            return sid.get();
        }

        public String getSname() {
            return sname.get();
        }

        public String getSemail() {
            return semail.get();
        }

        public String getSnumber() {
            return snumber.get();
        }

        public String getSdept() {
            return sdept.get();
        }
        
        }
}
