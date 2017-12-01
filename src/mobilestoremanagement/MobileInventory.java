/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import models.Mobile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rutul
 */
public class MobileInventory {

    // Observable list to store Mobile Inventory 
    private final ObservableList<Mobile> mobileInventory = FXCollections.observableArrayList();

    /**
     * This method will return you MobileInventory Observable list.
     *
     * @return
     */
    public ObservableList<Mobile> getInventory() {
        return mobileInventory;
    }

    /**
     * This method will calculate total sales of Store and return total
     *
     * @return
     */
    public double getTotalSales() {
        double totalSales = 0;
        for (Mobile mobile : mobileInventory) {
            totalSales += mobile.getSellingPrice();
        }
        return totalSales;
    }

    /**
     * This method will return total investment
     *
     * @return
     */
    public double getTotalInvestment() {
        double totalInvestment = 0;
        for (Mobile mobile : mobileInventory) {
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
        for (Mobile mobile : mobileInventory) {
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
        for (Mobile mobile : mobileInventory) {
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
        for (Mobile mobile : mobileInventory) {
            if (mobile.getSellingPrice() != 0.0) {
                profit += mobile.getSellingPrice() - mobile.getPurchasePrice();
            }
        }
        return profit;
    }

    /**
     * This method set selling price of mobile
     *
     * @param soldMobile
     * @param price
     */
    public void sellMobile(Mobile soldMobile, double price) {
        soldMobile.setSellingPrice(price);
    }

    /**
     * This method will remove mobile object from the observable list
     *
     * @param mobile
     */
    public void remove(Mobile mobile) {
        mobileInventory.remove(mobile);
    }

    /**
     * This method will add mobile object to the observable list
     *
     * @param mobile
     */
    public void addMobile(Mobile mobile) {
        mobileInventory.add(mobile);
    }

} // End of Class
