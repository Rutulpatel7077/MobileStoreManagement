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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class MobileStoreManagementController implements Initializable {

    private MobileInventory mobileInventory;
    private Mobile mobile;

// adding Buttons
    @FXML
    private Button addMobile;
    @FXML
    private Button sellMobile;
    @FXML
    private Button exitButton;
    @FXML
    private Button viewButton;

//Adding tableview and table columns
    @FXML
    private TableView<Mobile> mobileStoreView;
    @FXML
    private TableColumn<Mobile, String> makeColumn;
    @FXML
    private TableColumn<Mobile, String> modelColumn;
    @FXML
    private TableColumn<Mobile, String> osColumn;
    @FXML
    private TableColumn<Mobile, String> colorColumn;
    @FXML
    private TableColumn<Mobile, Double> purchasePriceColumn;
    @FXML
    private TableColumn<Mobile, Double> sellingPriceColumn;

//adding lables
    @FXML
    private Label totalInvestment;
    @FXML
    private Label totalMobile;
    @FXML
    private Label profit;
    @FXML
    private Label soldMobile;
    @FXML
    private Label totalSales;
    @FXML
    private Label errorLabel;

    /**
     * This method contains all initial data which we needs every time when we
     * refresh table view
     *
     * @param mobileInventory
     */
    public void initialData(MobileInventory mobileInventory) {
        this.mobileInventory = mobileInventory;
        mobileStoreView.setItems(mobileInventory.getInventory());
        updateInventoryLabels();
    }

    /**
     * This method has all the dummy data for our store. And this method will
     * return dummy data
     *
     * @return
     * @throws IOException
     */
    public ObservableList<Mobile> getMobiles() throws IOException {
        mobileInventory = new MobileInventory();
        mobileInventory.addMobile(new Mobile("S8", "Black", "Samsung", "Android", 1200, "8", "32", 12456, "www.samsung.ca", getImageS8()));
        mobileInventory.addMobile(new Mobile("G6", "Yellow", "LG", "Android", 1100, "16", "25", 545895, "www.lg.ca", getImageg6()));
        updateInventoryLabels();

        return mobileInventory.getInventory();

    }

    /**
     * This Action Event will add new mobile in our observable list this method
     * will change the scene to NewMobile.fxml This method will also have
     * controllers of New Mobile Controller class
     *
     * @param event
     * @throws IOException
     */
    public void newMobileButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewMobile.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);

        NewMobileController newMobileController = loader.getController();
        newMobileController.initData(mobileInventory);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * This method will get image of S8 for dummy data
     *
     * @return
     * @throws IOException
     */
    public Image getImageS8() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("./src/images/s8.png"));
        Image s8 = SwingFXUtils.toFXImage(bufferedImage, null);
        return s8;
    }

    /**
     * This method will get image of G6 for dummy data
     *
     * @return
     * @throws IOException
     */
    public Image getImageg6() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("./src/images/g6.png"));
        Image g6 = SwingFXUtils.toFXImage(bufferedImage, null);
        return g6;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable sell mobile button
        sellMobile.disableProperty().bind(Bindings.isEmpty(mobileStoreView.getSelectionModel().getSelectedItems()));
        try {
            makeColumn.setCellValueFactory(new PropertyValueFactory<Mobile, String>("make"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<Mobile, String>("model"));
            colorColumn.setCellValueFactory(new PropertyValueFactory<Mobile, String>("color"));
            osColumn.setCellValueFactory(new PropertyValueFactory<Mobile, String>("operatingSystem"));;
            purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<Mobile, Double>("purchasePrice"));
            sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<Mobile, Double>("sellingPrice"));
            mobileStoreView.setItems(getMobiles());

        } catch (IOException ex) {
            Logger.getLogger(MobileStoreManagementController.class.getName()).log(Level.SEVERE, null, ex);
            errorLabel.setText(ex.getMessage());
        }
    }

    /**
     * This method will update all the labels and inventory information
     */
    public void updateInventoryLabels() {
        totalInvestment.setText(String.format("Total Investment in Store: " + java.text.NumberFormat.getCurrencyInstance().format(mobileInventory.getTotalInvestment())));
        profit.setText(String.format("Total Profit from Sales: " + java.text.NumberFormat.getCurrencyInstance().format(mobileInventory.getProfit())));
        totalMobile.setText(String.format("Number of Mobiles in Store: %d", mobileInventory.getNumberOfMobileInStock()));
        totalSales.setText(String.format("Total Sales: " + java.text.NumberFormat.getCurrencyInstance().format(mobileInventory.getTotalSales())));
        soldMobile.setText(String.format("Number of Mobiles Sold: %d", mobileInventory.getNumberOfMobileSold()));
    }

    /**
     * This method will change scene to selling mobile this method will change
     * the scene to sellMobile.fxml
     *
     * @param event
     * @throws IOException
     */
    public void sellMobileButtonPushed(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SellMobile.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            SellMobileController controller = loader.getController();
            Mobile sellingMobile = mobileStoreView.getSelectionModel().getSelectedItem();

            mobileInventory.remove(sellingMobile);
            controller.initData(sellingMobile, mobileInventory);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException e) {
            errorLabel.setText("Please select mobile");
        }
    }

    /**
     * This method will close and exit the program.
     *
     * @param event
     */
    public void closeButtonAction(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

}// End of Class
