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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class NewMobileController implements Initializable {
 int r;
    MobileInventory mobileInventory;
    Mobile mobile;
    private Image image;
    private FileChooser fileChooser;
    private File filePath;
    
    
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
    public void addMobileButtonPushed(ActionEvent event) throws IOException {

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
        } else if(!purchasePriceTextField.getText().matches( "[0-9]+([,.][0-9]{1,2})?")){
            errorLabel.setText("Please enter only numbers in price field");
        }else if(!imeiTextField.getText().matches("[0-9]*")){
            errorLabel.setText("Please enter only numbers in IMEI number");       
        }else if(!websiteTextField.getText().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))
            errorLabel.setText("Please enter valid URL of Website");
        else {

            try {

                Mobile newMobile = new Mobile((String) modelTextField.getText(), (String) colorComboBox.getValue(),
                        (String) makeComboBox.getValue(), (String) osComboBox.getValue(),
                        Double.parseDouble(purchasePriceTextField.getText()), (String) ramComboBox.getValue(), (String) storageComboBox.getValue(),
                        Long.parseLong(imeiTextField.getText()), (String) websiteTextField.getText(), mobileImage.getImage());

                mobileInventory.addMobile(newMobile);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("MobileStoreManagement.fxml"));
                Parent parent = loader.load();

                Scene scene = new Scene(parent);

                MobileStoreManagementController controller = loader.getController();
                controller.initialData(mobileInventory);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                 
            } catch (IllegalArgumentException e) {
                errorLabel.setText(e.getMessage());
            }
        }

    } // end of addMobile Class

    /**
     * This method will allow users to upload image from the external storage
     * Got some idea from Jaret Wright Code to make this method
     *
     * @param event
     */
    public void uploadImageButtonPushed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter extentionFilterJPG = new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extentionFilterPNG = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");

        FileChooser.ExtensionFilter extentionFilterAll = new FileChooser.ExtensionFilter("Image files (*.ALL)", "*.png", "*jpeg", "*jpg");

        fileChooser.getExtensionFilters().addAll(extentionFilterPNG, extentionFilterJPG, extentionFilterAll);

        String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
        File userDirectory = new File(userDirectoryString);
        if (!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);
        this.filePath = fileChooser.showOpenDialog(stage);
        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            mobileImage.setImage(image);
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    /**
     * This method will cancel everything and change scene return to
     * MobileStoreManagement.fxml
     *
     * @param event
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MobileStoreManagement.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        MobileStoreManagementController controller = loader.getController();
        controller.initialData(mobileInventory);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
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

        try {
            BufferedImage bufferedImage = ImageIO.read(new File("./src/images/defaultImage.png"));
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            mobileImage.setImage(image);
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }
    }

} // End of class
