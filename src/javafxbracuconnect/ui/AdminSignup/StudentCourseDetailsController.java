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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sayeem Abdullah
 */
public class StudentCourseDetailsController implements Initializable {
    ObservableList <Course> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Course> coursetable;
    @FXML
    private TableColumn<Course,String> code;
    @FXML
    private TableColumn<Course,String> dept;
    @FXML
    private TableColumn<Course,String> sec;
    @FXML
    private TableColumn<Course,String> faculty;
    @FXML
    private TableColumn<Course,String> days;
    @FXML
    private TableColumn<Course,String> time;
    @FXML
    private Button back;
    Connection con;
    Statement stm;
    ResultSet rest; 
    int res;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(StudentCourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initCol();
        try {
            CourseloadData();
        } catch (SQLException ex) {
            Logger.getLogger(StudentCourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCol() {
    code.setCellValueFactory(new PropertyValueFactory<>("ccode"));
    dept.setCellValueFactory(new PropertyValueFactory<>("cdept"));
    sec.setCellValueFactory(new PropertyValueFactory<>("csec"));
    faculty.setCellValueFactory(new PropertyValueFactory<>("cfac"));
    days.setCellValueFactory(new PropertyValueFactory<>("cdays"));
    time.setCellValueFactory(new PropertyValueFactory<>("ctime"));
    }
    
    private void CourseloadData() throws SQLException {
        
        String qu = "SELECT CODE , DEPARTMENT , SECTION , FACULTY , DAYS , TIME FROM SAYEEM.COURSE";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String cccode = rest.getString("code");
                String ccdept = rest.getString("department");
                String ccsec = rest.getString("section");
                String ccfaculty = rest.getString("faculty");
                String ccdays = rest.getString("days");
                String cctime = rest.getString("time");
                
                
                StudentCourseDetailsController.Course course = new StudentCourseDetailsController.Course (cccode,ccdept,ccsec,ccfaculty,ccdays,cctime);
                list.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        coursetable.getItems().setAll(list);
    }

    @FXML
    private void goback(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("StudentDash.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
    public class Course{
    private final SimpleStringProperty ccode;
    private final SimpleStringProperty cdept;
    private final SimpleStringProperty csec;
    private final SimpleStringProperty cfac;
    private final SimpleStringProperty cdays;
    private final SimpleStringProperty ctime;
    
    public Course(String ccode, String cdept, String csec, String cfac, String cdays, String ctime){
        this.ccode = new SimpleStringProperty(ccode);
        this.cdept = new SimpleStringProperty(cdept);
        this.csec = new SimpleStringProperty(csec);
        this.cfac = new SimpleStringProperty(cfac);
        this.cdays = new SimpleStringProperty(cdays);
        this.ctime = new SimpleStringProperty(ctime);
    }

        public String getCcode() {
            return ccode.get();
        }

        public String getCdept() {
            return cdept.get();
        }

        public String getCsec() {
            return csec.get();
        }

        public String getCfac() {
            return cfac.get();
        }

        public String getCdays() {
            return cdays.get();
        }

        public String getCtime() {
            return ctime.get();
        }
    
    
    }
    
}
