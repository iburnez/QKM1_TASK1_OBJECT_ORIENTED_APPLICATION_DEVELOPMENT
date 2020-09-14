package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * _____________________________________________________________________ <br>
 *                      MODIFY PRODUCT CONTROLLER Class <br>
 * --------------------------------------------------------------------- <br>
 * This class is responsible for the follow: <br>
 * Updating existing Product Objects in the Inventory allProduct list <br>
 * Populating Inventory list allParts to Available Parts Table <br>
 * Adding selected parts from Available parts table to associatedParts list for Product Object <br>
 * Removing parts from associatedParts list for Product Object <br>
 * Searching available parts table and displaying results matching search parameters <br>
 */

public class ModifyProductController implements Initializable {

    /**
     * Stage and Parent variables used to store and launch application GUI
     */
    Stage stage;
    Parent scene;

    /**
     * _____________________________________________________________________
     *                            FXML id selectors
     * ---------------------------------------------------------------------
     * selectors used to read or set form fields
     */
    @FXML
    private TextField productIDTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField inventoryTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField availablePartsSearchTxt;
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableColumn<Part, Integer> availablePartIDCol;
    @FXML
    private TableColumn<Part, String> availablePartNameCol;
    @FXML
    private TableColumn<Part, Integer> availableInventoryCol;
    @FXML
    private TableColumn<Part, Double> availablePriceCol;
    @FXML
    private Button availableAddBtn;
    @FXML
    private TableView<Part> selectedPartsTable;
    @FXML
    private TableColumn<Part, Integer> selectedPartIDCol;
    @FXML
    private TableColumn<Part, String> selectedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> selectedInventoryCol;
    @FXML
    private TableColumn<Part, Double> selectedPriceCol;
    @FXML
    private Button selectedRemoveBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    /**
     * _____________________________________________________________________
     *                   CONFIGURE SELECTED PRODUCT FIELDS
     * ---------------------------------------------------------------------
     */

    /**
     * Populate Form Fields with corresponding data from PRODUCT selected to modify
     * @param product the product object to modify is chosen on the main menu screen. The
     *                main menu screen then sends the product object data to the ModifyProduct
     *                controller and each form field is populated with the existing
     *                data for the selected product object
     */
    public void sendProduct(Product product){
        /**
         * Get and Set each form field to reflect corresponding data of PRODUCT Object
         */
        productIDTxt.setText(String.valueOf(product.getId()));
        productIDTxt.setEditable(false);
        productNameTxt.setText(String.valueOf(product.getName()));
        inventoryTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));

        /**
         * Get all associatedParts for PRODUCT Object and set table columns
         * to corresponding values
         */
        selectedPartsTable.setItems(product.getAllAssociatedParts());
        selectedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /**
     * _____________________________________________________________________
     *                   AVAILABLE PARTS TABLE MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * SEARCH AVAILABLE PART TABLE <br>
     * This method searches the available parts table which is populated with
     * the Inventory allParts list to find objects matching the search terms
     * either by partName or partID <br>
     * If no match is found an error message is displayed <br>
     * @param event when the user presses enter in the search field the method
     *              executes a search
     */
    @FXML
    public void onActionSearchAvailableParts(ActionEvent event){
        /**
         * Test if search string is partID or partName
         */
        boolean isPartID = true;

        /**
         * If the search string is empty assume partName
         * An empty partName search will display allParts list
         */
        if(availablePartsSearchTxt.getText().isEmpty()){
            isPartID = false;
        }

        /**
         * Loop through the search string to determine if the search string is a partID
         * If the search string is all digits assume partID
         * If the search string has any characters assume partName
         */
        for(int i = 0; i < availablePartsSearchTxt.getText().length(); i++){
            if(!(Character.isDigit(availablePartsSearchTxt.getText().charAt(i)))){
                isPartID = false;
            }
        }

        /**
         * If search string is a partID perform following search
         */
        if(isPartID){
            /**
             * searchPart variable holds string from search TextField
             */
            Part searchPart = Inventory.lookupPart(Integer.parseInt(availablePartsSearchTxt.getText()));

            /**
             * If searchPart is not null perform search on allParts list and display results in available parts table
             */
            if(searchPart != null) {
                availablePartsTable.setItems(Inventory.lookupPart(searchPart.getName()));
            }
            /**
             * If the searchPart variable is null display error message
             */
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No match for name or part ID entered");
                alert.setContentText("Please try a different name or part ID.");
                alert.show();
            }
        }

        /**
         * If search string is not a partID assume partName and perform the following search
         */
        else {
            /**
             * searchPart variable holds data from search TextField
             */
            ObservableList<Part> searchPart = Inventory.lookupPart(availablePartsSearchTxt.getText());

            /**
             * If searchPart is not null perform search on allParts list and display results in available parts table
             */
            if (searchPart != null){
                availablePartsTable.setItems(searchPart);
            }
            /**
             * If the searchPart variable is null display error message
             */
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No match for name or part ID entered");
                alert.setContentText("Please try a different name or part ID.");
                alert.show();
            }
        }

    }

    /**
     * ADD SELECTED PART METHOD <br>
     * Reads available part table and gets selected part <br>
     * Adds selected part to associatedParts list for Product Object <br>
     * If no part is selected an error message is displayed <br>
     * Because this is an existing object all parts selected by the user to associate
     * to the product are added directly to the associatedParts list
     * @param event on button click the selected part is added to the selectedPart list
     */
    @FXML
    public void onActionAddPart(ActionEvent event) {
        /**
         * activeProduct variable holds the Product Object being modified
         */
        Product activeProduct = Inventory.lookupProduct(Integer.parseInt(productIDTxt.getText()));

        /**
         * selectedPart variable reads the availableParts table and gets the part Object selected by the user
         */
        Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        /**
         * Try to add selected part to selectedPart list
         */
        try{
            if(selectedPart != null) {
                activeProduct.addAssociatedPart(selectedPart);
            }
        /**
         * Catch Clause - if no part is selected display error message
         */
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part selected:");
            alert.setContentText("Please select an item from the available product list to add.");
            alert.show();
        }
        /**
         * Reload the selectedPartsTable with associatedParts list.
         * ---------------------------------------------------------------------
         * For products without associatedProducts when originally loaded into the modifyProducts
         * screen no associatedParts list is displayed becasue none exists. So when a new associatedPart
         * is added an associatedParts list is created and must be loaded to the selectedPartsTable.
         */
        selectedPartsTable.setItems(activeProduct.getAllAssociatedParts());
    }

    /**
     * _____________________________________________________________________
     *                   SELECTED PARTS TABLE MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * REMOVE SELECTED PART METHOD  <br>
     * Prior to removal a confirmation dialog box is displayed to user <br>
     * If action is confirmed remove action is performed <br>
     * If no part is selected an error message is displayed <br>
     * @param event on button click remove selected part from selectedPart list
     */
    @FXML
    public void onActionRemovePart(ActionEvent event) {

        try {
            /**
             * activeProduct variable holds the Product Object being modified
             */
            Product activeProduct = Inventory.lookupProduct(Integer.parseInt(productIDTxt.getText()));

            /**
             * selectedPart variable holds part object selected by user
             */
            Part selectedPart = Inventory.lookupPart(selectedPartsTable.getSelectionModel().getSelectedItem().getId());
            String selectedPartName = String.valueOf(selectedPartsTable.getSelectionModel().getSelectedItem().getName());

            /**
             * Prior to removing selected item display confirmation dialog box to confirm action
             */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove " +
                                                                              selectedPartName + "from the Associated " +
                                                                              "Parts list for this product? ");
            Optional<ButtonType> result = alert.showAndWait();

            /**
             * if user confirms action perform removal of selectedPart from list
             */
            if (result.isPresent() && result.get() == ButtonType.OK) {
                activeProduct.deleteAssociatedPart(selectedPart);
            }

        /**
         * Catch Clause - if no part is selected display error message
         */
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part selected:");
            alert.setContentText("Please select an item from the available product list to add.");
            alert.show();
        }
    }

    /**
     * _____________________________________________________________________
     *                         UNIVERSAL MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * SAVE PRODUCT <br>
     * --------------------------------------------------------------------- <br>
     * This method reads the form field inputs and updates the date for an
     * existing PRODUCT Object in the allProducts Inventory Observable List
     * --------------------------------------------------------------------- <br>
     * @param event when the save button is clicked the method executes
     * @throws IOException if file main menu file is not found IOException is thrown
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {

        /**
         * Save form field input to variables
         */
        try {
            int id = Integer.parseInt(productIDTxt.getText());
            int stock = Integer.parseInt(inventoryTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            String name = productNameTxt.getText();

            /**
             * Validate form fields Min must be less than Max
             */
            if (min < max) {
                /**
                 * Validate form fields stock must be less than max but greater than min
                 */
                if (min < stock && stock < max) {
                    /**
                     * If form fields valid Product constructor, save data to Inventory allPproducts List
                     * and launch main menu screen.
                     */
                    Inventory.updateProduct(id, new Product(id, name, price, stock, min, max));

                    /**
                     * scene variable holds path to mainmenu file
                     * stage variable loads main menu screen on button click
                     */
                    stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                /**
                 * If inventory is invalid show error screen.
                 */
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Inventory Value");
                    alert.setContentText("The Inventory value must be greater than Min and less than Max" +
                                        "Please review Inventory, Min and Max fields.");
                    alert.show();
                }
            }
            /**
             * If Min or Max values are invalid show error screen
             */
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Min/Max Values");
                alert.setContentText("Max value must be greater than Min value:\n\n" +
                        "Please review Min and Max fields. " +
                        "The value of the Max field must be " +
                        "greater than the value of the minimum field.");
                alert.show();
            }
        /**
         * Catch Clause - when parsing form fields to variable if the value cannot be parsed to the correct
         * data type (int, double) show an error screen and do not save data until values have been corrected
         */
        } catch (NumberFormatException e){
            Alert invalidEntryAlert = new Alert(Alert.AlertType.ERROR);
            invalidEntryAlert.setTitle("Error");
            invalidEntryAlert.setHeaderText("Invalid Entry");
            invalidEntryAlert.setContentText("One of the fields you entered was invalid.\n\n" +
                                            "Name: Accepts Letter, Numbers, and Symbols.\n" +
                                            "Inventory: DIGITS ONLY\n" +
                                            "Price: DIGITS ONLY\n" +
                                            "Min: DIGITS ONLY\n" +
                                            "Max: DIGITS ONLY\n");
            invalidEntryAlert.show();
        }
    }

    /**
     * CANCEL MODIFY PRODUCT - LOAD MAIN MENU <br>
     * Discard all field and list data and return to main menu
     * @param event When the cancel button is clicked load the main menu screen
     * @throws IOException if main menu file is not found throw IOException
     */
    @FXML
    public void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        /**
         * scene variable holds path to mainmenu file
         * stage variable loads main menu screen on button click
         */
        stage = (Stage)((Button)(event.getSource())).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * POPULATE PRODUCT FORM FIELDS <br>
     * DEFAULT METHOD REQUIRED IN "Initializable" <br>
     * This method loads a unique partID for all new Parts <br>
     * @param url
     * @param rb
     */
    public  void initialize(URL url, ResourceBundle rb){

        /**
         * Get Product Object data and Set all form fields with appropriate data
         */
        availablePartsTable.setItems(Inventory.getAllParts());
        availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
