/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private File mobileImage;
    private double purchasePrice, sellingPrice;
    private long mobileIMEI;
    private int stockNumber, ram, storage;
    private int mobileID;
    private int salesId;
    private String sold = "false";
    private LocalDate dateOfSelling;
    private LocalDate dateInStock = LocalDate.now();

    //Mobile Constructor 
    public Mobile(String model, String color, String make, String operatingSystem, double purchasePrice) {
        setModel(model);
        setColor(color);
        setMake(make);
        setOperatingSystem(operatingSystem);
        setPurchasePrice(purchasePrice);
        setSellingPrice(sellingPrice);
        setMobileImage(new File("./src/images/defaultImage.png"));
        setDateInStock(dateInStock);

        setSold(sold);

    }

    public Mobile(String model, String color, String make, String operatingSystem, double purchasePrice, String ram, String storage, long IMEI, String website, File image, LocalDate dateOfSelling) throws IOException {

        this(model, color, make, operatingSystem, purchasePrice);
        this.ram = Integer.parseInt(ram);
        this.storage = Integer.parseInt(storage);
        this.mobileIMEI = IMEI;
        this.website = website;
        setMobileImage(image);
        copyImageFile();
        setDateOfSelling(dateOfSelling);
        setDateInStock(dateInStock);
        setSold(sold);
    }

    /**
     * This method will return model of the mobile
     *
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * This method will set the model in constructor for mobile
     *
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
     *
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * this method will set the color for mobile in constructor
     *
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * This method will return web site of mobile company
     *
     * @return
     */
    public String getWebsite() {
        return website;
    }

    /**
     * This method will set web site for mobile through constructor
     *
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * This method will return make of mobile
     *
     * @return
     */
    public String getMake() {
        return make;
    }

    /**
     * This method will set make of mobile
     *
     * @param make
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * This method will return operating system of mobile
     *
     * @return
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * This method will set Operating System of mobile
     *
     * @param operatingSystem
     */
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    /**
     * This method will return Mobile image
     *
     * @return
     */
    public File getMobileImage() {
        return mobileImage;
    }

    /**
     * This method will set mobile image
     *
     * @param mobileImage
     */
    public void setMobileImage(File mobileImage) {
        this.mobileImage = mobileImage;
    }

    /**
     * This method will copy the file specified to the images directory on this
     * server and give it a unique name
     *
     * @throws java.io.IOException
     */
    public void copyImageFile() throws IOException {
        //create a new Path to copy the image into a local directory
        Path sourcePath = mobileImage.toPath();

        String uniqueFileName = getUniqueFileName(mobileImage.getName());

        Path targetPath = Paths.get("./src/images/" + uniqueFileName);

        //copy the file to the new directory
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        //update the imageFile to point to the new File
        mobileImage = new File(targetPath.toString());
    }

    /**
     * This method will receive a String that represents a file name and return
     * a String with a random, unique set of letters prefixed to it
     */
    private String getUniqueFileName(String oldFileName) {
        String newName;

        //create a Random Number Generator
        SecureRandom rng = new SecureRandom();

        //loop until we have a unique file name
        do {
            newName = "";

            //generate 32 random characters
            for (int count = 1; count <= 32; count++) {
                int nextChar;

                do {
                    nextChar = rng.nextInt(123);
                } while (!validCharacterValue(nextChar));

                newName = String.format("%s%c", newName, nextChar);
            }
            newName += oldFileName;

        } while (!uniqueFileInDirectory(newName));

        return newName;
    }

    /**
     * This method will search the images directory and ensure that the file
     * name is unique
     */
    public boolean uniqueFileInDirectory(String fileName) {
        File directory = new File("./src/images/");

        File[] dir_contents = directory.listFiles();

        for (File file : dir_contents) {
            if (file.getName().equals(fileName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will validate if the integer given corresponds to a valid
     * ASCII character that could be used in a file name
     *
     * @param asciiValue
     * @return
     */
    public boolean validCharacterValue(int asciiValue) {

        //0-9 = ASCII range 48 to 57
        if (asciiValue >= 48 && asciiValue <= 57) {
            return true;
        }

        //A-Z = ASCII range 65 to 90
        if (asciiValue >= 65 && asciiValue <= 90) {
            return true;
        }

        //a-z = ASCII range 97 to 122
        if (asciiValue >= 97 && asciiValue <= 122) {
            return true;
        }

        return false;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    /**
     * This method will return purchase price of mobile
     *
     * @return
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * \
     * This method will set purchase price of the mobile
     *
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
     *
     * @return
     */
    public double getSellingPrice() {
        return sellingPrice;
    }

    /**
     * this method will set selling price
     *
     * @param sellingPrice
     */
    public void setSellingPrice(double sellingPrice) {
        if (sellingPrice < 0) {
            throw new IllegalArgumentException("Selling price should be greater than zero");
        } else if ((Double.toString(sellingPrice)).isEmpty()) {
            throw new IllegalArgumentException("Please insert selling price");
        } else {
            this.sellingPrice = sellingPrice;
        }
    }

    /**
     * This method will return MobileIMEI number of mobile
     *
     * @return
     */
    public long getMobileIMEI() {
        return mobileIMEI;
    }

    /**
     * This method will set IMEI number of mobile
     *
     * @param mobileIMEI
     */
    public void setMobileIMEI(long mobileIMEI) {
        if (mobileIMEI < 0) {
            throw new IllegalArgumentException("Please double check IMEI number");
        } else {
            this.mobileIMEI = mobileIMEI;
        }
    }

    /**
     * This method will return StockNumber with auto increment
     *
     * @return
     */
    public int getStockNumber() {
        return stockNumber;
    }

    /**
     * This method set StockNumber
     *
     * @param stockNumber
     */
    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    /**
     * This method will return you a RAM
     *
     * @return
     */
    public int getRam() {
        return ram;
    }

    /**
     * This method will set RAM
     *
     * @param ram
     */
    public void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * This method will return Storage Of mobile
     *
     * @return
     */
    public int getStorage() {
        return storage;
    }

    /**
     * This method set Storage Option
     *
     * @param storage
     */
    public void setStorage(int storage) {
        this.storage = storage;
    }

    public LocalDate getDateOfSelling() {
        return dateOfSelling;
    }

    public void setDateOfSelling(LocalDate dateOfSelling) {
        this.dateOfSelling = dateOfSelling;
    }

    public LocalDate getDateInStock() {
        return dateInStock;
    }

    public void setDateInStock(LocalDate dateInStock) {
        this.dateInStock = dateInStock;
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

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    /**
     * Database methods
     */
    public void insertIntoDB() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);

            // step 2             
            String sql = "INSERT INTO mobiles (make, operatingSystem, model , color, imei, ram, storageCapacity, website,  imageFile, purchasePrice , sellingPrice,dateInStock,dateOfSelling,sold)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            //query             
            preparedStatement = conn.prepareStatement(sql);

            //bind values into parameter 
            //preparedStatement.setInt(1, stockNumber);
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, operatingSystem);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, color);
            preparedStatement.setLong(5, mobileIMEI);
            preparedStatement.setInt(6, ram);
            preparedStatement.setInt(7, storage);
            preparedStatement.setString(8, website);
            preparedStatement.setString(9, mobileImage.toString());
            preparedStatement.setDouble(10, (int) purchasePrice);
            preparedStatement.setDouble(11, (int) sellingPrice);
            preparedStatement.setDate(12, Date.valueOf(dateInStock));
            preparedStatement.setDate(13, Date.valueOf(dateOfSelling));
            preparedStatement.setString(14, sold);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Update Database Method
     */
    public void updateDataBase() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);

            //step 2             
            String sql = "UPDATE mobiles SET  make=?, operatingSystem=?, model=? , color=?, imei=?, ram=?, storageCapacity=?, website=?,  imageFile=?, purchasePrice=? , sellingPrice=?, dateInStock=?, dateOfSelling=?, sold=? " + "WHERE stockNumber= ?";

            //query             
            preparedStatement = conn.prepareStatement(sql);

            //bind values into parameter 
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, operatingSystem);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, color);
            preparedStatement.setLong(5, mobileIMEI);
            preparedStatement.setInt(6, ram);
            preparedStatement.setInt(7, storage);
            preparedStatement.setString(8, website);
            preparedStatement.setString(9, mobileImage.toString());
            preparedStatement.setDouble(10, (int) purchasePrice);
            preparedStatement.setDouble(11, (int) sellingPrice);
            preparedStatement.setDate(12, Date.valueOf(dateInStock));
            preparedStatement.setDate(13, Date.valueOf(dateOfSelling));
            preparedStatement.setString(14, sold);
            preparedStatement.setInt(15, stockNumber);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}// End of Class
