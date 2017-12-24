/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionPassword;
import models.Mobile;
import models.Users;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class AllUsersController implements Initializable{

    @FXML private TableView<Users> userTable;
    @FXML private TableColumn<Users, Integer> userIdColumn;
    @FXML private TableColumn<Users, String> firstNameColumn;
    @FXML private TableColumn<Users, String> lastNameColumn;
    @FXML private TableColumn<Users, String> phoneColumn;
    @FXML private TableColumn<Users, LocalDate> birthdayColumn;
    
    @FXML private Button editButton;
   
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        editButton.disableProperty().bind(Bindings.isEmpty(userTable.getSelectionModel().getSelectedItems()));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<Users, Integer>("userId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("phoneNumber"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Users, LocalDate>("birthday"));
        
        try {
            getUsers();
        } catch (IOException ex) {
            Logger.getLogger(AllUsersController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AllUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    
     public void getUsers() throws IOException, SQLException {
        //mobileInventory = new MobileInventory();
    
        ObservableList<Users> users;
       users = FXCollections.observableArrayList();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try{
            
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);
            
            //statement object 
            statement = conn.createStatement();
            
            // create SQL query 
            resultSet = statement.executeQuery("SELECT*FROM users");
            
            // create mobile objects from each records             
            while(resultSet.next()){
   
          Users newUser = new Users(resultSet.getString("firstName"),resultSet.getString("lastName"),
                                           resultSet.getString("phoneNumber"),
                                           resultSet.getDate("birthday").toLocalDate(),
                                                       resultSet.getString("password"),
                                                       resultSet.getBoolean("admin"));
            
            newUser.setUserId(resultSet.getInt("userId"));
            newUser.setImageFile(new File(resultSet.getString("imageFile")));
            
     
             
            users.add(newUser);

            }
           userTable.getItems().addAll(users);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
         if(conn != null)
             conn.close();
        if(statement != null)
            statement.close();
        if(resultSet != null)
            resultSet.close();
        }

    }
     
     public void addButtonPushed(ActionEvent event) throws IOException{
          SceneChanger sc = new SceneChanger();
           sc.changeScenes(event, "AddUsers.fxml", "Add Users");
     }
     
     public void editButtonPushed(ActionEvent event) throws IOException{
            SceneChanger sc = new SceneChanger();
             Users user = this.userTable.getSelectionModel().getSelectedItem();
             AddUsersController npvc = new AddUsersController();
             sc.changeScenesUsers(event, "AddUsers.fxml", "Edit User", user, npvc);
     }
     
      public void GotoMobileStore(ActionEvent event) throws IOException{
         SceneChanger sc = new SceneChanger();
         sc.changeScenes(event, "MobileStoreManagement.fxml", "The Mobile Store");
    }
        
     
      public void ProfitViewButtonPushed(ActionEvent event ) throws IOException{
         SceneChanger sc = new SceneChanger();
         sc.changeScenes(event, "Profit.fxml", "Profit");
     }

    
}
