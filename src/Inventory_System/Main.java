package Inventory_System;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * _____________________________________________________________________ <br>
 *                             Main Class <br>
 * --------------------------------------------------------------------- <br>
 * The Main class launches the Inventory Systems Management Application <br>
 * The application will allow users to do the following: <br>
 * View current inventory of parts and products <br>
 * Add/modify Parts to the inventory system <br>
 * Add/modify Products to the inventory system <br>
 * Search through list of current Parts and Products <br>
 * Delete selected parts and products <br>
 * ---------------------------------------------------------------------
 */
public class Main extends Application {


    /**
     * _____________________________________________________________________ <br>
     *                              Start Method <br>
     * --------------------------------------------------------------------- <br>
     * This method loads the main stage of the application <br>
     * @param primaryStage gets MainMenu FXML file at launch of program
     * @throws Exception if MainMenu.fxml file is not found throw exemption
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        /**
         * root variable stores path to MainMenu.fxml file
         */
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));

        /**
         * Set scene and stage with MainMenu file
         */
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * _____________________________________________________________________ <br>
     *                              Main Method <br>
     * --------------------------------------------------------------------- <br>
     * Main method calls the start method to launch MainMenu upon application launch <br>
     * This method adds sample data to demonstrate application functionality
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Create sample data for application
         */
        InHouse wheel = new InHouse(1, "Wheel", 12.99, 11, 1, 1000, 123);
        InHouse tire = new InHouse(2, "Tire", 19.99, 29, 1, 1000, 124);
        InHouse handleBar = new InHouse(3, "Handle Bar", 9.99, 123, 1, 1000, 125);
        InHouse brakes = new  InHouse(4, "Brakes", 5.99, 49, 1, 1000, 126);
        Outsourced frame = new Outsourced(5, "Frame", 99.99, 81, 1, 1000, "Hurley");
        Outsourced bikeRack = new Outsourced(6, "bikeRack", 99.99, 20, 1, 1000, "Dexter");
        Product bike = new Product(10, "Bike", 199.99, 51, 1, 100);
        Product tricycle = new Product(11, "Tri-Cycle", 129.99, 61, 1, 100);
        Product unicycle = new Product(12, "Uni-Cycle", 129.99, 71, 1, 100);
        Product waterBottle = new Product(13, "Water Bottle", 9.99, 31, 1, 100);
        Product bikeChain = new Product(14, "Bike Chain", 19.99, 23, 1, 100);
        bike.addAssociatedPart(brakes);

        /**
         * Load sample data into application
         */
        Inventory.addNewPart(wheel);
        Inventory.addNewPart(tire);
        Inventory.addNewPart(handleBar);
        Inventory.addNewPart(brakes);
        Inventory.addNewPart(frame);
        Inventory.addNewPart(bikeRack);
        Inventory.addNewProduct(bike);
        Inventory.addNewProduct(tricycle);
        Inventory.addNewProduct(unicycle);
        Inventory.addNewProduct(waterBottle);
        Inventory.addNewProduct(bikeChain);

        launch(args);
    }
}
