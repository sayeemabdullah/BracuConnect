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
public class AdminCourseWaitingListController implements Initializable {
   
    ObservableList <Waiting> wlist = FXCollections.observableArrayList();

    @FXML
    private TableView<Waiting> waitinglist;
    @FXML
    private TableColumn<Waiting,String> studentid;
    @FXML
    private TableColumn<Waiting,String> course;
    @FXML
    private Button back;
    private Button admindash;
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
            Logger.getLogger(AdminCourseWaitingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initCol();
        try {
            WaitingoadData();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCourseWaitingListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void initCol() {
    studentid.setCellValueFactory(new PropertyValueFactory<>("wid"));
    course.setCellValueFactory(new PropertyValueFactory<>("wcode"));
    }
    private void WaitingoadData() throws SQLException {
        
        String qu = "SELECT * FROM SAYEEM.COURSEWAITINGLIST";
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/BracuConnect", "sayeem", "17101009");
        stm = con.createStatement();
        rest = stm.executeQuery(qu);
        
        try {
            while (rest.next()) {
                String wwid = rest.getString("id");
                String wwcode = rest.getString("code");
                
                
                AdminCourseWaitingListController.Waiting waiting = new AdminCourseWaitingListController.Waiting (wwid,wwcode);
                wlist.add(waiting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacultyDashController.class.getName()).log(Level.SEVERE, null, ex);
        }

        waitinglist.getItems().setAll(wlist);
    }

    @FXML
    private void goback(ActionEvent event) throws IOException {
        Parent adminDash = FXMLLoader.load(getClass().getResource("AdminCourseManagement.fxml"));
        Scene adminDashScene = new Scene(adminDash);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("BracuConnect");
        window.setScene(adminDashScene);
        window.show();   
    }
    
    public class Waiting {

        private final SimpleStringProperty wid;
        private final SimpleStringProperty wcode;


        public Waiting(String wid, String wcode) {
            this.wid = new SimpleStringProperty(wid);
            this.wcode = new SimpleStringProperty(wcode);
        }

        public String getWid() {
            return wid.get();
        }

        public String getWcode() {
            return wcode.get();
        }
        
    }
}
