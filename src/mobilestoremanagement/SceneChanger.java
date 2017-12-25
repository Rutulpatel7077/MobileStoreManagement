/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Mobile;
import models.Users;

/**
 *
 * @author Rutul
 */
public class SceneChanger {

    /**
     * This method will accept the title of the new scene, the .fxml file name
     * for the view and the ActionEvent that triggered the change
     *
     * @param event
     * @param viewName
     * @param title
     * @throws java.io.IOException
     */

    private static Users loggedInUser;

    public void changeScenes(ActionEvent event, String viewName, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method will change scenes and preload the next scene with a object
     *
     * @param event
     * @param viewName
     * @param title
     * @param mobile
     * @param controllerClass
     * @throws java.io.IOException
     */
    public void changeScenes(ActionEvent event, String viewName, String title, Mobile mobile, ControllerClass controllerClass) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //access the controller class and preloaded the mobile data
        controllerClass = loader.getController();
        controllerClass.preloadData(mobile);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void changeScenesUsers(ActionEvent event, String viewName, String title, Users user, ControllerClass controllerClass) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //access the controller class and preloaded the user data
        controllerClass = loader.getController();
        controllerClass.preloadDataUser(user);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static Users getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(Users loggedInUser) {
        SceneChanger.loggedInUser = loggedInUser;
    }
}
