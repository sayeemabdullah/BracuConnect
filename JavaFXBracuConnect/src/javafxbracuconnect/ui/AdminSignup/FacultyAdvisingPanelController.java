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
public class FacultyAdvisingPanelController implements Initializable {
    ObservableList <AdvisingStudent> list = FXCollections.observableArrayList();

    @FXML
    private TableView<AdvisingStudent> allstudentcoursetable;
    @FXML
    private TableColumn<AdvisingStudent,String> id;
    @FXML
    private TableColumn<AdvisingStudent,String> c1;
    @FXML
    private TableColumn<AdvisingStudent,String> c2;
    @FXML
    private TableColumn<AdvisingStudent,String> c3;
    @FXML
    private TableColumn<AdvisingStudent,String> c4;
    @FXML
    private TableColumn<AdvisingStudent,String> c5;
    @FXML
    private Button advising;
    @FXML
    private Button facultydash;
    Connection con;
    Statement stm;
    int res;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        } catch (SQLException ex) {
            Logger.getLogger(FacultyAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initCol();
        try {
            AdvisingStudentloadData();
        } catch (SQLException ex) {
            Logger.getLogger(FacultyAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
     private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("sid"));
        c1.setCellValueFactory(new PropertyValueFactory<>("sc1"));
        c2.setCellValueFactory(new PropertyValueFactory<>("sc2"));
        c3.setCellValueFactory(new PropertyValueFactory<>("sc3"));
        c4.setCellValueFactory(new PropertyValueFactory<>("sc4"));
        c5.setCellValueFactory(new PropertyValueFactory<>("sc5"));
    }
     
     private void AdvisingStudentloadData() throws SQLException {
        
        String qu = "SELECT * FROM SAYEEM.ADVISEDCOURSE ";
        System.out.println(qu);
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        ResultSet rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String stid = rest.getString("id");
                String stc1 = rest.getString("course1");
                String stc2 = rest.getString("course2");
                String stc3 = rest.getString("course3");
                String stc4 = rest.getString("course4");
                String stc5 = rest.getString("course5");
                
                
                FacultyAdvisingPanelController.AdvisingStudent advisingstudent = new FacultyAdvisingPanelController.AdvisingStudent (stid,stc1,stc2,stc3,stc4,stc5);
                list.add(advisingstudent);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyAdvisingPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        allstudentcoursetable.getItems().setAll(list);
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

    @FXML
    private void gotofacultydash(ActionEvent event) throws IOException {
    Parent adminDash = FXMLLoader.load(getClass().getResource("FacultyDash.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();
    }
    
    public class AdvisingStudent {
        private final SimpleStringProperty sid;
        private final SimpleStringProperty sc1;
        private final SimpleStringProperty sc2;
        private final SimpleStringProperty sc3;
        private final SimpleStringProperty sc4;
        private final SimpleStringProperty sc5;
        

        public AdvisingStudent(String sid , String sc1, String sc2, String sc3, String sc4, String sc5) {
            this.sid = new SimpleStringProperty(sid);
            this.sc1 = new SimpleStringProperty(sc1);
            this.sc2 = new SimpleStringProperty(sc2);
            this.sc3 = new SimpleStringProperty(sc3);
            this.sc4 = new SimpleStringProperty(sc4);
            this.sc5 = new SimpleStringProperty(sc5);
            
        }

        public String getSid() {
            return sid.get();
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
