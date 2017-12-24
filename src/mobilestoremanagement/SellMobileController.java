/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.awt.image.BufferedImage;
import java.io.File;
import models.Mobile;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import models.ConnectionPassword;
import models.Users;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class SellMobileController implements Initializable, ControllerClass {
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
    @FXML DatePicker dateOfSelling;
    @FXML DatePicker dateInStock;

    @FXML
    private Button sellButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;
    private File imageFile;

    private Mobile mobile;
    private Users user;
    private MobileInventory mobileInventory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
        public void initial(Mobile mobile, Users user) throws IOException{
       this.mobile = mobile;
       this.user = user;
       
       this.mobile = mobile;
       
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
        itemNumberTextField.setText(Integer.toString(mobile.getStockNumber()));
        dateInStock.setValue(mobile.getDateInStock());
        dateInStock.setDisable(true);
        dateOfSelling.setValue(LocalDate.now());
         
        try{
            String imgLocation = ".\\src\\images\\"+mobile.getMobileImage().getName();
            imageFile = new File(imgLocation);
            System.out.println(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            mobileImage.setImage(image);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

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
    public void mobileSoldButtonPushed(ActionEvent event) throws IOException, SQLException {
        
        try {
      
            mobile.setSellingPrice(Double.parseDouble(sellingPriceTextField.getText()));
            mobile.setDateOfSelling(dateOfSelling.getValue());
            mobile.setSold("true");
            mobile.updateDataBase();
            
            
            System.out.println(mobile.getSold());
            insertSales(dateOfSelling.getValue(), mobile);
            
            SceneChanger sc = new SceneChanger();
            sc.changeScenes(event, "MobileStoreManagement.fxml", "Mobile Store");

        } catch (IllegalArgumentException e) {
            errorLabel.setText("Please enter valid price and positive numbers only");
        }
    }
   

    /**
     * This method will avoid all things and close the scene and change it to
     * mobile store controller
     * @param event
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException, SQLException {
         SceneChanger sc = new SceneChanger();
         sc.changeScenes(event, "MobileStoreManagement.fxml", "Mobile Store");
    }

    public void preloadData(Mobile mobile) {
        this.mobile = mobile;
       
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
        itemNumberTextField.setText(Integer.toString(mobile.getStockNumber()));
        dateInStock.setValue(mobile.getDateInStock());
        dateInStock.setDisable(true);
        dateOfSelling.setValue(LocalDate.now());
         
        try{
            String imgLocation = ".\\src\\images\\"+mobile.getMobileImage().getName();
            imageFile = new File(imgLocation);
            System.out.println(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            mobileImage.setImage(image);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

        sellingPriceTextField.selectAll();
        sellingPriceTextField.requestFocus();
    }

    @Override
    public void preloadDataUser(Users user) {
        this.user = user;
    }
    
 public void insertSales(LocalDate dateOfSelling, Mobile mobile) throws SQLException {
        //validate the date is today or earlier
        if (dateOfSelling.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of selling cannot be in the future");
        }

        if (dateOfSelling.isBefore(LocalDate.now().minusYears(1))) {
            throw new IllegalArgumentException("Date of selling must be within the last 12 months");
        }

        //ready to store in the database
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //1. connect to the database
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);

            //2. create a preparedStatement
            String sql = "INSERT INTO sales(purchasePrice,sellingPrice, dateOfSelling) VALUES (?,?,?);";

            //3.  prepare the query
            ps = conn.prepareStatement(sql);

            //4.  convert the localdate to sql date
            Date dw = Date.valueOf(dateOfSelling);

            //5.  bind the parameters
            
            ps.setDouble(1, mobile.getPurchasePrice());
          
            ps.setDouble(2, mobile.getSellingPrice());
            ps.setDate(3, dw);

            //6.  execute the update  
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }
    
    
    
     

   
    
  

} // End of Class
