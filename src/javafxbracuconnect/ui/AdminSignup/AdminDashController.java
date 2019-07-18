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
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author sayeem
 */
public class AdminDashController implements Initializable {
    
    ObservableList<Admin> list = FXCollections.observableArrayList();
    ObservableList<Student> slist = FXCollections.observableArrayList();
    ObservableList<Faculty> flist = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Admin,String> adminname;
    @FXML
    private TableColumn<Admin, String> adminemail;
    @FXML
    private TableColumn<Student, String> studentid;
    @FXML
    private TableColumn<Student, String> studentname;
    @FXML
    private TableColumn<Student, String> studentemail;
    @FXML
    private TableColumn<Student, String> studentnumber;
    @FXML
    private TableColumn<Student, String> studentdpt;
    @FXML
    private TableColumn<Faculty, String> facultyinitial;
    @FXML
    private TableColumn<Faculty, String> facultyname;
    @FXML
    private TableColumn<Faculty, String> facultyemail;
    @FXML
    private TableColumn<Faculty, String> facultynumber;
    @FXML
    private TableView<Admin> admintable;
    @FXML
    private TableView<Student> studenttable;
    @FXML
    private TableView<Faculty> facultytable;
    Connection con;
    Statement stm;
    ResultSet rest;
    int res;
    @FXML
    private Tab admininfo;
    @FXML
    private Tab studentinfo;
    @FXML
    private Tab facultyinfo;
    @FXML
    private Button courseadd;
    @FXML
    private Button studentmange;
    @FXML
    private Button facultymanage;
    @FXML
    private Button adminmanage;
    @FXML
    private Button editstudentbutton;
    @FXML
    private Button editadminbutton;
    @FXML
    private Button editfacultybutton;
    @FXML
    private Button settings;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException e) {
        }
        initCol();
        try {
            AdminloadData();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            StudentloadData();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FacultyloadData();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void initCol() {
    adminname.setCellValueFactory(new PropertyValueFactory<>("name"));
    adminemail.setCellValueFactory(new PropertyValueFactory<>("email"));
    studentid.setCellValueFactory(new PropertyValueFactory<>("sid"));
    studentname.setCellValueFactory(new PropertyValueFactory<>("sname"));
    studentemail.setCellValueFactory(new PropertyValueFactory<>("semail"));
    studentnumber.setCellValueFactory(new PropertyValueFactory<>("snumber"));
    studentdpt.setCellValueFactory(new PropertyValueFactory<>("sdept"));
    facultyinitial.setCellValueFactory(new PropertyValueFactory<>("fin"));
    facultyname.setCellValueFactory(new PropertyValueFactory<>("fname"));
    facultyemail.setCellValueFactory(new PropertyValueFactory<>("femail"));
    facultynumber.setCellValueFactory(new PropertyValueFactory<>("fnumber"));
    }
    

    private void AdminloadData() throws SQLException {
        String qu = "SELECT NAME , EMAIL FROM SAYEEM.ADMIN";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String aname = rest.getString("name");
                String aemail = rest.getString("email");
                
                Admin admin = new Admin(aname,aemail);
                list.add(admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        admintable.getItems().setAll(list);
    }
    
    private void StudentloadData() throws SQLException {
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
                
                
                Student student = new Student (stid,stname,stemail,stnumber,stdept);
                slist.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studenttable.getItems().setAll(slist);
    }
    private void FacultyloadData() throws SQLException {
        String qu = "SELECT INITIAL , NAME , EMAIL , PHONENUMBER FROM SAYEEM.FACULTY";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String fain = rest.getString("initial");
                String faname = rest.getString("name");
                String faemail = rest.getString("email");
                String fanumber = rest.getString("phonenumber");

                
                
                Faculty faculty = new Faculty (fain,faname,faemail,fanumber);
                flist.add(faculty);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        facultytable.getItems().setAll(flist);
    }


    @FXML
    private void gotofaculty(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyDelete.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    
    }


    @FXML
    private void gotocourse(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("AdminCourseManagement.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }


    @FXML
    private void gotostudent(ActionEvent event) throws IOException {
            Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDelete.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    }

    @FXML
    private void gotoadmin(ActionEvent event) throws IOException {
            Parent adminDash = FXMLLoader.load(getClass().getResource("AdminDelete.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    }

    @FXML
    private void gotoeditstudent(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("UpdateStudentAdmin.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();   
    }

    @FXML
    private void editadmin(ActionEvent event) throws IOException {
            Parent adminDash = FXMLLoader.load(getClass().getResource("EditAdmin.fxml"));
            Scene adminDashScene = new Scene(adminDash);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("BracuConnect");
            window.setScene(adminDashScene);
            window.show();
    }

    @FXML
    private void gotoeditfaculty(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("UpdateFacultyAdmin.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show(); 
    }

    @FXML
    private void gotosettings(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("AdminSettings.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }

        
    public class Admin {

        private final SimpleStringProperty name;
        private final SimpleStringProperty email;
        

        public Admin(String name, String email) {
            this.name = new SimpleStringProperty(name);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public String getEmail() {
            return email.get();
        }
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
        
    public class Faculty {

        private final SimpleStringProperty fin;
        private final SimpleStringProperty fname;
        private final SimpleStringProperty femail;
        private final SimpleStringProperty fnumber;


        public Faculty(String fin, String fname, String femail, String fnumber) {
            this.fin = new SimpleStringProperty(fin);
            this.fname = new SimpleStringProperty(fname);
            this.femail = new SimpleStringProperty(femail);
            this.fnumber = new SimpleStringProperty(fnumber);
        }

        public String getFin() {
            return fin.get();
        }

        public String getFname() {
            return fname.get();
        }

        public String getFemail() {
            return femail.get();
        }
  
        public String getFnumber() {
            return fnumber.get();
        }
        }        
}       
