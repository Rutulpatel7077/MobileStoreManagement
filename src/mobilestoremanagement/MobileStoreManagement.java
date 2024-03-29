/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Rutul
 */
public class MobileStoreManagement extends Application {

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        /**
         * On your first start we need to add user into user table 
         * so comment 22 line and uncomment 25 add an user and then you can use that.
         */
      //  Parent root = FXMLLoader.load(getClass().getResource("AllUsers.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("The Mobile Store");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
