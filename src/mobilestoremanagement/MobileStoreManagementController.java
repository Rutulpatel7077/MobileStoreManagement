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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import models.ConnectionPassword;
import models.Users;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class MobileStoreManagementController implements Initializable , ControllerClass {

   private MobileInventory mobileInventory;
    private Mobile mobile;
     ObservableList<Mobile> mobiles = FXCollections.observableArrayList();
    

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
    private Users user;

 

    /**
     * This method has all the dummy data for our store. And this method will
     * return dummy data
     *
     * @return
     * @throws IOException
     */
    public void getMobiles() throws IOException, SQLException {
        //mobileInventory = new MobileInventory();
   
       
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try{
            
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);
            
            //statement object 
            statement = conn.createStatement();
            
            // create SQL query 
            resultSet = statement.executeQuery("SELECT*FROM mobiles");
            
            // create mobile objects from each records             
            while(resultSet.next()){
   
            Mobile newMobile = new Mobile(resultSet.getString("model"),resultSet.getString("color"),
                                           resultSet.getString("make"),
                                           resultSet.getString("operatingSystem"),
                                           resultSet.getDouble("purchasePrice"));
            
            
            newMobile.setRam(resultSet.getInt("ram"));
            newMobile.setStorage(resultSet.getInt("storageCapacity"));
            newMobile.setMobileIMEI(resultSet.getLong("imei"));
            newMobile.setWebsite(resultSet.getString("website"));
            newMobile.setStockNumber(resultSet.getInt("stockNumber"));
            newMobile.setSold(resultSet.getString("sold"));
            newMobile.setSellingPrice(resultSet.getDouble("sellingPrice"));
            newMobile.setMobileImage( new File(resultSet.getString("imageFile")));
            
            mobiles.add(newMobile);
           
            //mobileInventory.addMobile(newMobile);
            }
            mobileStoreView.getItems().addAll(mobiles);
            
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
        updateInventoryLabels();

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
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    

        
        //sellMobile.disableProperty().bind(Bindings.isEmpty(mobileStoreView.getSelectionModel().getSelectedItems()));
        sellMobile.setDisable(true);
      
        
        try {
            makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
            colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
            osColumn.setCellValueFactory(new PropertyValueFactory<>("operatingSystem"));
            purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
            sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
            
             getMobiles();
          
           
        } catch (IOException ex) {
            Logger.getLogger(MobileStoreManagementController.class.getName()).log(Level.SEVERE, null, ex);
            errorLabel.setText(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(MobileStoreManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectedMobile(){
       
        if(mobileStoreView.getSelectionModel().getSelectedItem().getSold().equals("false")){
         System.out.println(mobileStoreView.getSelectionModel().getSelectedItem().getSold()); 
        sellMobile.setDisable(false);
        }
        else if(mobileStoreView.getSelectionModel().getSelectedItem().getSold().equals("true"))
         sellMobile.setDisable(true);
    }

    public double getTotalSales() {
        double totalSales = 0;
        for (Mobile mobile : mobiles) {
            totalSales += mobile.getSellingPrice();
        }
        return totalSales;
    }

    public double getTotalInvestment() {
        double totalInvestment = 0;
        for (Mobile mobile : mobiles) {
            totalInvestment += mobile.getPurchasePrice();
        }
        return totalInvestment;
    }
    
     /**
     * This method will return total number of IN stock mobiles
     *
     * @return
     */
    public int getNumberOfMobileInStock() {
        int totalMobiles = 0;
        for (Mobile mobile : mobiles) {
            if (mobile.getSellingPrice()==0.0) {
                totalMobiles++;
            }
        }
        return totalMobiles;
    }

    /**
     * This method will return you Number of Sold mobile
     *
     * @return
     */
    public int getNumberOfMobileSold() {
        int numOfMobileSold = 0;
        for (Mobile mobile : mobiles) {
            if (mobile.getSellingPrice() != 0.0) {
                numOfMobileSold++;
            }
        }
        return numOfMobileSold;
    }

    /**
     * This method will return you total profit
     *
     * @return
     */
    public double getProfit() {
        double profit = 0;
        for (Mobile mobile : mobiles) {
            if (mobile.getSellingPrice() != 0.0) {
                profit += mobile.getSellingPrice() - mobile.getPurchasePrice();
            }
        }
        return profit;
    }
    
    
    /**
     * This method will update all the labels and inventory information
     */
    public void updateInventoryLabels() {
        totalMobile.setText(String.format("Number of Mobiles in Store: %d", getNumberOfMobileInStock()));
        totalSales.setText(String.format("Total Sales: " + java.text.NumberFormat.getCurrencyInstance().format(getTotalSales())));
        soldMobile.setText(String.format("Number of Mobiles Sold: %d", getNumberOfMobileSold()));
    }

    /**
     * This method will change scene to selling mobile this method will change
     * the scene to sellMobile.fxml
     *
     * @param event
     * @throws IOException
     */
    public void sellMobileButtonPushed(ActionEvent event) throws IOException {        
        SceneChanger sc = new SceneChanger();
        Mobile mobile = this.mobileStoreView.getSelectionModel().getSelectedItem();
        SellMobileController smc = new SellMobileController();
        sc.changeScenes(event, "sellMobile.fxml", "Sell this mobile", mobile, smc);
    }
    
     public void salesViewButtonPushed(ActionEvent event ) throws IOException{
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "Sales.fxml", "Sales");
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

    @Override
    public void preloadData(Mobile mobile) {
       this.mobile = mobile;
    }

    @Override
    public void preloadDataUser(Users user) {
        this.user = user;
    }
    
   

}// End of Class
