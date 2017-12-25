/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.PasswordGenerator;
import models.Users;
import models.ConnectionPassword;
import models.Mobile;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class LoginController implements Initializable, ControllerClass {

    @FXML
    private TextField userIDTextField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Label errMsgLabel;

    private Users user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loginButtonPushed(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        //query the database with the Users provided, get the salt
        //and encrypted password stored in the database
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        int userId = Integer.parseInt(userIDTextField.getText());

        try {
            //1.  connect to the DB
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);

            //2.  create a query string with ? used instead of the values given by the user
            String sql = "SELECT * FROM users WHERE userId = ?";

            //3.  prepare the statement
            ps = conn.prepareStatement(sql);

            //4.  bind the ID to the ?
            ps.setInt(1, userId);

            //5. execute the query
            resultSet = ps.executeQuery();

            //6.  extract the password and salt from the resultSet
            String dbPassword = null;
            byte[] salt = null;
            boolean admin = false;
            Users user = null;

            while (resultSet.next()) {
                dbPassword = resultSet.getString("password");

                Blob blob = resultSet.getBlob("salt");

                //convert into a byte array
                int blobLength = (int) blob.length();
                salt = blob.getBytes(1, blobLength);

                admin = resultSet.getBoolean("admin");

                user = new Users(resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getDate("birthday").toLocalDate(),
                        resultSet.getString("password"),
                        resultSet.getBoolean("admin"));
                user.setUserId((resultSet.getInt("userId")));
                user.setImageFile(new File(resultSet.getString("imageFile")));
                this.user = user;
            }

            //convert the password given by the user into an encryted password
            //using the salt from the database
            String userPW = PasswordGenerator.getSHA512Password(pwField.getText(), salt);

            SceneChanger sc = new SceneChanger();

            if (userPW.equals(dbPassword)) {
                SceneChanger.setLoggedInUser(user);
            }

            //if the passwords match - change to the users table
            if (userPW.equals(dbPassword) && admin) {
                sc.changeScenes(event, "AllUsers.fxml", "All Employees");
            } else if (userPW.equals(dbPassword)) {

                //create an instance of the controller class for log hours view
//                LogHoursViewController controllerClass = new LogHoursViewController();
                //  sc.changeScenesUsers(event, "MobileStoreManagement.fxml", "Welcome to Mobile Store");
                AddUsersController npvc = new AddUsersController();
                // sc.changeScenesUsers(event, "AddUsers.fxml", "Edit User", user, npvc);
                sc.changeScenesUsers(event, "UserPanel.fxml", "Welcome to the MobileStore", user, npvc);
            } else //if the do not match, update the error message
            {
                errMsgLabel.setText("The User and password do not match");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void editbuttonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        AddUsersController npvc = new AddUsersController();
        sc.changeScenesUsers(event, "AddUsers.fxml", "Welcome to the MobileStore", user, npvc);
    }

    public void GotoMobileStore(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "MobileStoreManagement.fxml", "The Mobile Store");
    }

    public void LogOutButtonPushed(ActionEvent event) throws IOException {
        SceneChanger.setLoggedInUser(null);
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "Login.fxml", "Login to the Mobile Store");
    }

    @Override
    public void preloadData(Mobile mobile) {

    }

    @Override
    public void preloadDataUser(Users user) {
        this.user = user;
    }

}
