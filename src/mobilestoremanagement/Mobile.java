/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Rutul
 */
public final class Mobile {
    // Static variables
    private static int nextStockNum = 1001;
    private static String[] mobileMakers = {"Samsung", "Apple", "Nokia", "Sony", "LG", "HTC", "Motorola", "Huawei"};
    private static String[] vaildOperatingSystems = {"Android", "IOS", "Windows X Paltform"};
    private static String[] validColor = {"Black", "Grey", "Purple", "Red", "Orange", "Yellow", "Green", "Cyan", "Blue", "Indigo", "Violet"};
    private static String[] storageOptions = {"4", "8", "16", "32", "64", "128", "164"};
    private static String[] ramOptions = {"2", "3", "4", "5", "6", "7", "8", "9"};

    //Mobile model variables 
    
    private String model, color, website, make, operatingSystem;
    private Image mobileImage;
    private double purchasePrice, sellingPrice;
    private long mobileIMEI;
    private int stockNumber, ram, storage;

    //Mobile Constructor 
    public Mobile(String model, String color, String make, String operatingSystem, double purchasePrice) {
        setModel(model);
        setColor(color);
        setMake(make);
        setOperatingSystem(operatingSystem);
        setPurchasePrice(purchasePrice);
        setSellingPrice(sellingPrice);
        stockNumber = nextStockNum;
        nextStockNum++;
        
        // validating image location and file 
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("./src/images/defaultImage.png"));
            mobileImage = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // another second constructor to add adiitional feature
    public Mobile(String model, String color, String make, String operatingSystem, double purchasePrice, String ram, String storage, long IMEI, String website, Image image) {
       
        this(model, color, make, operatingSystem, purchasePrice);
        this.mobileImage = image;
        this.ram = Integer.parseInt(ram);
        this.storage = Integer.parseInt(storage);
        this.mobileIMEI = IMEI;
        this.website = website;
    }
/**
 * This method will return model of the mobile 
 * @return 
 */
    public String getModel() {
        return model;
    }
/**
 * This method will set the model in constructor for mobile
 * @param model 
 */
    public void setModel(String model) {
        if ("".equals(model)) {
            throw new IllegalArgumentException("Please set your Model name");
        }
        this.model = model;
    }
/**
 * this method will return color of the mobile 
 * @return 
 */
    public String getColor() {
        return color;
    }
/**
 * this method will set the color for mobile in constructor
 * @param color 
 */
    public void setColor(String color) {
        this.color = color;
    }
/**
 * This method will return web site of mobile company
 * @return 
 */
    public String getWebsite() {
        return website;
    }
/**
 * This method will set web site for mobile through constructor
 * @param website 
 */
    public void setWebsite(String website) {
        this.website = website;
    }
/**
 * This method will return make of mobile
 * @return 
 */
    public String getMake() {
        return make;
    }
/**
 * This method will set make of mobile
 * @param make 
 */
    public void setMake(String make) {
        this.make = make;
    }
/**
 * This method will return operating system of mobile
 * @return 
 */
    public String getOperatingSystem() {
        return operatingSystem;
    }
/**
 * This method will set Operating System of mobile
 * @param operatingSystem 
 */
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
/**
 * This method will return Mobile image 
 * @return 
 */
    public Image getMobileImage() {
        return mobileImage;
    }
/**
 * This method will  set mobile image
 * @param mobileImage 
 */
    public void setMobileImage(Image mobileImage) {
        this.mobileImage = mobileImage;
    }
/**
 * This method will return purchase price of mobile     
 * @return 
 */
    public double getPurchasePrice() {
        return purchasePrice;
    }
/**\
 * This method will set purchase price of the mobile
 * @param purchasePrice 
 */
    public void setPurchasePrice(double purchasePrice) {
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Please correct your purchase Price");
        }
        this.purchasePrice = purchasePrice;
    }
/**
 * this method will return selling price 
 * @return 
 */
    public double getSellingPrice() {
        return sellingPrice;
    }
/**
 * this method will set selling price 
 * @param sellingPrice 
 */
    public void setSellingPrice(double sellingPrice) {
        if (sellingPrice < 0) {
            throw new IllegalArgumentException("Selling price should be greater than zero");
        } else if (" ".equals(Double.toString(sellingPrice))) {
            throw new IllegalArgumentException("Please insert selling price");
        } else {
            this.sellingPrice = sellingPrice;
        }
    }
/**
 * This method will return MobileIMEI number of mobile
 * @return 
 */
    public long getMobileIMEI() {
        return mobileIMEI;
    }
/**
 * This method will set IMEI number of mobile
 * @param mobileIMEI 
 */
    public void setMobileIMEI(long mobileIMEI) {
        if (mobileIMEI > 1000000000) {
            this.mobileIMEI = mobileIMEI;
        } else if (mobileIMEI == Long.parseLong("")) {
            throw new IllegalArgumentException("IMEI should not empty");
        } else {
            throw new IllegalArgumentException("Please double check IMEI number");
        }
    }
/**
 * This method will return StockNumber with auto increment
 * @return 
 */
    public int getStockNumber() {
        return stockNumber;
    }
/**
 * This method set StockNumber
 * @param stockNumber 
 */
    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }
/**
 * This method will return you a RAM 
 * @return 
 */
    public int getRam() {
        return ram;
    }
/**
 * This method will set RAM 
 * @param ram 
 */
    public void setRam(int ram) {
        this.ram = ram;
    }
/**
 * This method will return Storage Of mobile 
 * @return 
 */
    public int getStorage() {
        return storage;
    }
/**
 * This method set Storage Option
 * @param storage 
 */
    public void setStorage(int storage) {
        this.storage = storage;
    }

    
    // These all methods are for the Drop Down Menus( ComboBox) to get all the details
    public static List<String> getValidMobileMakers() {
        List<String> vaildMobilesMakers = Arrays.asList(mobileMakers);
        Collections.sort(vaildMobilesMakers);
        return vaildMobilesMakers;
    }

    public static List<String> getValidOperatingSystems() {
        List<String> vaildOperatingSystem = Arrays.asList(vaildOperatingSystems);
        Collections.sort(vaildOperatingSystem);
        return vaildOperatingSystem;
    }

    public static List<String> getValidColors() {
        List<String> validColors = Arrays.asList(validColor);
        Collections.sort(validColors);
        return validColors;
    }

    public static List<String> getValidStorageOptions() {
        List<String> validStorageOption = Arrays.asList(storageOptions);
        Collections.sort(validStorageOption);
        return validStorageOption;
    }

    public static List<String> getValidRamOptions() {
        List<String> validRamOption = Arrays.asList(ramOptions);
        Collections.sort(validRamOption);
        return validRamOption;
    }
}// End of Class
