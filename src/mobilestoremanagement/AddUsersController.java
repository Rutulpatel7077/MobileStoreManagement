/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import models.Mobile;
import models.Users;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class AddUsersController implements Initializable, ControllerClass {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private DatePicker birthday;
    @FXML
    private Label errMsgLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private TextField codeTextField;

    private File imageFile;
    private boolean imageFileChanged;
    private Users user;

    //used for the passwords
    @FXML
    private PasswordField pwField;
    @FXML
    private PasswordField confirmPwField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void saveUserButtonPushed(ActionEvent event) {
        if (validPassword() || user != null) {
            try {
                if (user != null) //we need to edit/update an existing user
                {
                    updateUser();
                    user.updateUserInDB();

                    if (!pwField.getText().isEmpty()) {
                        if (validPassword()) {
                            user.changePassword(pwField.getText());
                        }
                    }

                } else //we need to create a new user
                {
                    if (imageFileChanged) //create a User with a custom image
                    {
                        user = new Users(firstNameTextField.getText(), lastNameTextField.getText(),
                                phoneTextField.getText(), birthday.getValue(), imageFile,
                                adminCheckBox.isSelected(), pwField.getText());
                    } else //create a user with a default image
                    {
                        user = new Users(firstNameTextField.getText(), lastNameTextField.getText(),
                                phoneTextField.getText(), birthday.getValue(),
                                pwField.getText(),
                                adminCheckBox.isSelected());
                    }
                    errMsgLabel.setText("");    //do not show errors if creating user was successful
                    user.insertIntoDB();
                }

                SceneChanger sc = new SceneChanger();
                sc.changeScenes(event, "Login.fxml", "Please Login now");
            } catch (Exception e) {
                errMsgLabel.setText(e.getMessage());
            }
        }
    }

    /**
     * When this button is pushed, a FileChooser object is launched to allow the
     * user to browse for a new image file. When that is complete, it will
     * update the view with a new image
     */
    public void chooseImageButtonPushed(ActionEvent event) {
        //get the Stage to open a new window (or Stage in JavaFX)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        //filter for .jpg and .png
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        //Set to the user's picture directory or user directory if not available
        String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
        File userDirectory = new File(userDirectoryString);

        //if you cannot navigate to the pictures directory, go to the user home
        if (!userDirectory.canRead()) {
            userDirectory = new File(System.getProperty("user.home"));
        }

        fileChooser.setInitialDirectory(userDirectory);

        //open the file dialog window
        File tmpImageFile = fileChooser.showOpenDialog(stage);

        if (tmpImageFile != null) {
            imageFile = tmpImageFile;

            //update the ImageView with the new image
            if (imageFile.isFile()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(img);
                    imageFileChanged = true;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

    /**
     * This method will validate that the passwords match
     *
     */
    public boolean validPassword() {
        if (pwField.getText().length() < 5) {
            errMsgLabel.setText("Passwords must be greater than 5 characters in length");
            return false;
        }

        if (pwField.getText().equals(confirmPwField.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public void backButtonPushed(ActionEvent event) throws IOException {
        if (SceneChanger.getLoggedInUser().isAdmin()) {
            SceneChanger sc = new SceneChanger();
            sc.changeScenes(event, "AllUsers.fxml", "All Users");
        }

        SceneChanger sc = new SceneChanger();
        AddUsersController npvc = new AddUsersController();
        sc.changeScenesUsers(event, "UserPanel.fxml", "Welcome to the MobileStore", user, npvc);
    }

    public void preloadDataUser(Users user) {
        this.user = user;
        this.firstNameTextField.setText(user.getFirstName());
        this.lastNameTextField.setText(user.getLastName());
        this.birthday.setValue(user.getBirthday());
        this.phoneTextField.setText(user.getPhoneNumber());
        this.headerLabel.setText("Edit user");

        adminCheckBox.setDisable(true);

        if (user.isAdmin()) {
            adminCheckBox.setSelected(true);
        }
        //load the image 
        try {
            String imgLocation = ".\\src\\images\\" + user.getImageFile().getName();
            imageFile = new File(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(img);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method will read from the GUI fields and update the user object
     */
    public void updateUser() throws IOException {
        user.setAdmin(adminCheckBox.isSelected());
        user.setFirstName(firstNameTextField.getText());
        user.setLastName(lastNameTextField.getText());
        user.setPhoneNumber(phoneTextField.getText());
        user.setBirthday(birthday.getValue());
        user.setImageFile(imageFile);

        if (imageFileChanged) {
            user.copyImageFile();
        }

    }

    @Override
    public void preloadData(Mobile mobile) {

    }

}
