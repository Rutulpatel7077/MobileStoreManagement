/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import models.Mobile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.scene.control.TextField;
import models.ConnectionPassword;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class NewMobileController implements Initializable {

    int r;
    MobileInventory mobileInventory;
    Mobile mobile;
    
    private File imageFile;

// Adding Comboboxes and textfield
    @FXML
    private ComboBox makeComboBox;
    @FXML
    private ComboBox osComboBox;
    @FXML
    private TextField modelTextField;
    @FXML
    private ComboBox colorComboBox;
    @FXML
    private ComboBox storageComboBox;
    @FXML
    private ComboBox ramComboBox;

    @FXML
    private TextField purchasePriceTextField;
    @FXML
    private TextField imeiTextField;
    @FXML
    private TextField websiteTextField;

// Adding Buttons
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button addMobileButton;
    @FXML
    private Button cancelButton;

//Adding labels and ImageView
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView mobileImage;

    /**
     * Initial data from Observable List
     *
     * @param mobileInventory
     */
    public void initData(MobileInventory mobileInventory) {
        this.mobileInventory = mobileInventory;
    }

    /**
     * This method will validate inputs and add mobile into observable list
     *
     * @param event
     * @throws IOException
     */
    public void addMobileButtonPushed(ActionEvent event) throws IOException, SQLException {

        if (makeComboBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please set your phone make company");
        } else if (osComboBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please set your phone operating System");
        } else if (modelTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter your phone model");
        } else if (colorComboBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please set your phone Color");
        } else if (purchasePriceTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter Purchase price of your phone");
        } else if (ramComboBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please set your phone RAM");
        } else if (storageComboBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please set your phone storage capacity");
        } else if (imeiTextField.getText().isEmpty()) {
            errorLabel.setText("Please type IMEI number");
        } else if (websiteTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter your mobile website");
        } else if (!purchasePriceTextField.getText().matches("[0-9]+([,.][0-9]{1,2})?")) {
            errorLabel.setText("Please enter only numbers in price field");
        } else if (!imeiTextField.getText().matches("[0-9]*")) {
            errorLabel.setText("Please enter only numbers in IMEI number");
        } else if (!websiteTextField.getText().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
            errorLabel.setText("Please enter valid URL of Website");
        } else {

            try {

                Mobile newMobile = new Mobile((String) modelTextField.getText(), (String) colorComboBox.getValue(),
                        (String) makeComboBox.getValue(), (String) osComboBox.getValue(),
                        Double.parseDouble(purchasePriceTextField.getText()), (String) ramComboBox.getValue(), (String) storageComboBox.getValue(),
                        Long.parseLong(imeiTextField.getText()), (String) websiteTextField.getText(),imageFile,LocalDate.now() );
              
                    newMobile.insertIntoDB();
                   
               
                SceneChanger sc = new SceneChanger();
                sc.changeScenes(event, "MobileStoreManagement.fxml", "Mobile Store");

            } catch (IllegalArgumentException e) {
                errorLabel.setText(e.getMessage());
            }
        }

    } // end of addMobile Class

    /**
     * When this button is pushed, a FileChooser object is launched to allow the user
     * to browse for a new image file.  When that is complete, it will update the 
     * view with a new image
     */
    public void chooseImageButtonPushed(ActionEvent event)
    {
        //get the Stage to open a new window (or Stage in JavaFX)
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        
        //filter for .jpg and .png
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
        
        //Set to the user's picture directory or user directory if not available
        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        
        //if you cannot navigate to the pictures directory, go to the user home
        if (!userDirectory.canRead())
            userDirectory = new File(System.getProperty("user.home"));
        
        fileChooser.setInitialDirectory(userDirectory);
        
        //open the file dialog window
        File tmpImageFile = fileChooser.showOpenDialog(stage);
        
        if (tmpImageFile != null)
        {
            imageFile = tmpImageFile;
        
            //update the ImageView with the new image
            if (imageFile.isFile())
            {
                try
                {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    mobileImage.setImage(img);
                   
                }
                catch (IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }
        
    }

    /**
     * This method will cancel everything and change scene return to
     * MobileStoreManagement.fxml
     *
     * @param event
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException, SQLException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "MobileStoreManagement.fxml", "Mobile");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        makeComboBox.getItems().addAll(Mobile.getValidMobileMakers());
        osComboBox.getItems().addAll(Mobile.getValidOperatingSystems());
        colorComboBox.getItems().addAll(Mobile.getValidColors());

        storageComboBox.getItems().addAll(Mobile.getValidStorageOptions());
        ramComboBox.getItems().addAll(Mobile.getValidRamOptions());
        errorLabel.setText("");

         //load the defautl image for the avatar
        try{
            imageFile = new File("./src/images/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            mobileImage.setImage(image);
            
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

    }
      

    
    
} // End of class
