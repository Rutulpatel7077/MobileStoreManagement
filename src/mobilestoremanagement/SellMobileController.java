/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class SellMobileController implements Initializable {
//Adding TextField and Imageview

    @FXML
    private TextField purchasePriceTextField;
    @FXML
    private TextField imeiTextField;
    @FXML
    private TextField websiteTextField;
    @FXML
    private TextField makeTextField;
    @FXML
    private TextField operatingSystemTextField;
    @FXML
    private TextField modelTextField;
    @FXML
    private TextField colorTextField;
    @FXML
    private TextField ramTextField;
    @FXML
    private TextField storageTextField;
    @FXML
    private TextField sellingPriceTextField;
    @FXML
    private ImageView mobileImage;
    @FXML
    private TextField itemNumberTextField;

    @FXML
    private Button sellButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

    private Mobile mobile;
    private MobileInventory mobileInventory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * This method will take all data from the mobileStoreTableview and preview
     * on new Form and put focus on Selling Price to change it from 0.0 to which
     * user want.
     *
     * @param mobile
     * @param mobileInventory
     */
    public void initData(Mobile mobile, MobileInventory mobileInventory) {
        this.mobile = mobile;
        this.mobileInventory = mobileInventory;

        purchasePriceTextField.setText(Double.toString(mobile.getPurchasePrice()));
        makeTextField.setText(mobile.getMake());
        operatingSystemTextField.setText(mobile.getOperatingSystem());
        modelTextField.setText(mobile.getModel());
        colorTextField.setText(mobile.getColor());
        sellingPriceTextField.setText(Double.toString(mobile.getSellingPrice()));
        ramTextField.setText(Integer.toString(mobile.getRam()));
        storageTextField.setText(Integer.toString(mobile.getStorage()));
        imeiTextField.setText(Long.toString(mobile.getMobileIMEI()));
        websiteTextField.setText(mobile.getWebsite());
        mobileImage.setImage(mobile.getMobileImage());
        itemNumberTextField.setText(Integer.toString(mobile.getStockNumber()));

        sellingPriceTextField.selectAll();
        sellingPriceTextField.requestFocus();

    }

    /**
     * This method will sell the mobile This method will validate selling price
     * and remove mobile from the main observable list as well as add in sold
     * mobile list and change the screen
     *
     * @param event
     * @throws IOException
     */
    public void mobileSoldButtonPushed(ActionEvent event) throws IOException {
        
        try {
      
            mobile.setSellingPrice(Double.parseDouble(sellingPriceTextField.getText()));
            mobileInventory.addMobile(mobile);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MobileStoreManagement.fxml"));
            Parent tableViewParent = loader.load();

            MobileStoreManagementController controller = loader.getController();
            controller.initialData(mobileInventory);

            Scene scene = new Scene(tableViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IllegalArgumentException e) {
            errorLabel.setText("Please enter valid price and positive numbers only");
        }
    }
   

    /**
     * This method will avoid all things and close the scene and change it to
     * mobile store controller
     *
     * @param event
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        mobileInventory.addMobile(mobile);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MobileStoreManagement.fxml"));
        Parent tableViewParent = loader.load();

        MobileStoreManagementController controller = loader.getController();
        controller.initialData(mobileInventory);

        Scene scene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

} // End of Class
