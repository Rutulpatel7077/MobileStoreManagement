/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import models.ConnectionPassword;

/**
 * FXML Controller class
 *
 * @author Rutul
 */
public class ProfitController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private CategoryAxis year;
    @FXML
    private NumberAxis amount;

    private XYChart.Series currentYearSeries, previousYearSeries;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentYearSeries = new XYChart.Series<>();
        previousYearSeries = new XYChart.Series<>();

        year.setLabel("Month");
        amount.setLabel("($) Total Sales");

        currentYearSeries.setName(Integer.toString(LocalDate.now().getYear()));
        previousYearSeries.setName(Integer.toString(LocalDate.now().getYear() - 1));

        try {
            populateSeriesFromDB();
        } catch (SQLException e) {
            System.err.println(e);
        }

        barChart.getData().addAll(previousYearSeries);
        barChart.getData().addAll(currentYearSeries);
    }

    /**
     * This will read the user data from the database and populate the series
     */
    public void populateSeriesFromDB() throws SQLException {
        //get the results from the database
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //1.  connect to the database
            conn = DriverManager.getConnection(ConnectionPassword.URL, ConnectionPassword.USERNAME, ConnectionPassword.PASSWORD);

            //2.  create the statement
            statement = conn.createStatement();

            //3.  create a string with the sql statement
            String sql = "SELECT YEAR(dateOfSelling), MONTHNAME(dateOfSelling), SUM(sellingPrice-purchasePrice) "
                    + "FROM sales "
                    + "GROUP BY YEAR(dateOfSelling), MONTH(dateOfSelling) "
                    + "ORDER BY YEAR(dateOfSelling), MONTH(dateOfSelling);";

            //4. execute the query
            resultSet = statement.executeQuery(sql);

            //5.  loop over the result set and add to our series
            while (resultSet.next()) {
                if (resultSet.getInt(1) == LocalDate.now().getYear()) {
                    currentYearSeries.getData().add(new XYChart.Data(resultSet.getString(2), resultSet.getInt(3)));
                } else if (resultSet.getInt(1) == LocalDate.now().getYear() - 1) {
                    previousYearSeries.getData().add(new XYChart.Data(resultSet.getString(2), resultSet.getInt(3)));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * This method will return the scene to the
     */
    public void backButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "AllUsers.fxml", "Mobile Store");
    }

    /**
     * This method will log the user out of the application and return them to
     * the LoginView scene
     *
     * @param user
     */
    public void logoutButtonPushed(ActionEvent event) throws IOException {
        SceneChanger.setLoggedInUser(null);
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "Login.fxml", "Login");
    }

}
