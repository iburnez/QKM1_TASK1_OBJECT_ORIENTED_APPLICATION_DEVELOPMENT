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
 *                      ADD PART CONTROLLER Class <br>
 * --------------------------------------------------------------------- <br>
 * This class is responsible for the follow: <br>
 * Creating new Product Object <br>
 * Saving new Product Object to Inventory allProduct list <br>
 * Populating Inventory list allParts to Available Parts Table <br>
 * Adding selected parts from Available parts table to associatedParts list for Product Object <br>
 * Removing parts from associatedParts list for Product Object <br>
 * Searching available parts table and displaying results matching search parameters <br>
 */
public class AddProductController implements Initializable {

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
    private TextField availableSearchTxt;
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableColumn<Part, Integer> availablePartIDCol;
    @FXML
    private TableColumn<Part, String> availablePartNameCol;
    @FXML
    private TableColumn<Part, Integer> availableInvetoryCol;
    @FXML
    private TableColumn<Part, Double> availablePriceCol;
    @FXML
    private Button availabeAddBtn;
    @FXML
    private TableView<Part> selectedTable;
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
     *                   AVAILABLE PARTS TABLE MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * SEARCH AVAILABLE PART TABLE <br>
     * This method searches the available parts table which is populated with
     * the Invenotry allParts list to find objects matching the search terms
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
        if(availableSearchTxt.getText().isEmpty()){
            isPartID = false;
        }

        /**
         * Loop through the search string to determine if the search string is a partID
         * If the search string is all digits assume partID
         * If the search string has any characters assume partName
         */
        for(int i = 0; i < availableSearchTxt.getText().length(); i++){
            if(!(Character.isDigit(availableSearchTxt.getText().charAt(i)))){
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
            Part searchPart = Inventory.lookupPart(Integer.parseInt(availableSearchTxt.getText()));

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
             * searchPart variable holds string from search TextField
             */
            ObservableList<Part> searchPart = Inventory.lookupPart(availableSearchTxt.getText());

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
     * This method saves a part selected from the available parts table which
     * is populated from the Inventory allParts list to the Inventory selectedParts
     * list. When the new product is saved the selected parts list is saved the
     * selected parts list is used to populate an associatedParts list for
     * the new Product
     * @param event on button click the selected part is added to the selectedPart list
     */
    @FXML
    public void onActionAddPart(ActionEvent event) {
        /**
         * Try to add selected part to selectedPart list
         *
         * Because this is a new Product Object an associatedParts list does not
         * exist yet. The static selectedParts list is used as a temporary holder
         * for associated parts. When saved the new Product Object is created
         * all parts from the static selectedParts list are saved to the
         * Product object's associatedParts list.
         */
        try {
            Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();
                Inventory.addSelectedPart(selectedPart);

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
    }


    /**
     * _____________________________________________________________________
     *                   SELECTED PARTS TABLE MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * REMOVE SELECTED PART METHOD <br>
     * Prior to remove action a confirmation dialog box is displayed to the user <br>
     * If the user confirms the remove action is performed <br>
     * if no Part is selected an error message is displayed <br>
     * @param event on button click remove selected part from selectedPart list
     */
    @FXML
    public void OnActionRemovePart(ActionEvent event) {


        try {
            /**
             * selectedPart variable holds part object selected by user
             */
            Part selectedPart = selectedTable.getSelectionModel().getSelectedItem();
            String selectedPartName = selectedPart.getName();

            /**
             * Prior to removing selected item display confirmation dialog box to confirm action
             */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove " +
                                                                              selectedPartName + "from the Associated " +
                                                                              "Parts list for this product? ");
            Optional<ButtonType> result = alert.showAndWait();

            /**
             * if user confirms action perform removale of selectedPart from list
             */
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteSelectedPart(selectedPart);

                }

            /**
             * Catch Clause - if no part is selected display error message
             */
            }catch(NullPointerException e ){
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
     * SAVE NEW PRODUCT <br>
     * --------------------------------------------------------------------- <br>
     * This method reads the form field inputs and saves the data as a new
     * PRODUCT Object in the allProducts Inventory Observable List
     * --------------------------------------------------------------------- <br>
     * @param event when the save button is clicked the method executes
     * @throws IOException if file main menu file is not found IOException is thrown
     */
    @FXML
    public void OnActionSaveProduct(ActionEvent event) throws IOException {

        /**
         * Save form field input to variables
         */
        try{
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
                    Inventory.addNewProduct(new Product(id, name, price, stock, min, max));

                    /**
                     * If product has selectedParts loop through all parts and save
                     * to associatedParts list for Product object
                     *
                     * Because this is a new Product Object an associatedParts list does not
                     * exist yet. The static selectedParts list is used as a temporary holder
                     * for associated parts. When saved the new Product Object is created
                     * all parts from the static selectedParts list are saved to the
                     * Product object's associatedParts list.
                     *
                     * As the selectedParts list is used to create new Product Objects it must
                     * be cleared after each save so new Products start with an empty list.
                     */
                    if(!(Inventory.getSelectedParts().isEmpty())){
                        for(int i = 0; i < Inventory.getSelectedParts().size(); i++){
                            Inventory.lookupProduct(id).addAssociatedPart(Inventory.getSelectedParts().get(i));
                        }
                        /**
                         * Clear selectedParts list once saved to associatedParts list
                         */
                        Inventory.getSelectedParts().clear();
                    }

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
        }catch (NumberFormatException e){
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
     * CANCEL NEW PRODUCT - LOAD MAIN MENU <br>
     * @param event When the cancel button is clicked load the main menu screen
     * @throws IOException if main menu file is not found throw IOException
     */
    @FXML
    public void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        /**
         * scene variable holds path to mainmenu file
         * stage variable loads main menu screen on button click
         */
        stage = (Stage) ((Button)(event.getSource())).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * POPULATE AVAILABLE/SELECTED PARTS TABLE <br>
     * DEFAULT METHOD REQUIRED IN "Initializable" <br>
     * This method loads a unique partID for all new Parts <br>
     * @param url
     * @param rb
     */
    public  void initialize(URL url, ResourceBundle rb){

        /**
         * Generate new ProductID and set value in field - make field uneditable
         */
        productIDTxt.setText(String.valueOf(Inventory.generateProductID()));
        productIDTxt.setEditable(false);

        /**
         * Get all Part Objects from Inventory allParts list and set Table Columns to appropriate fields
         */
        availablePartsTable.setItems(Inventory.getAllParts());
        availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableInvetoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * Get all Part Objects from Inventory selectedParts list and set Table Columns to appropriate fields
         */
        selectedTable.setItems(Inventory.getSelectedParts());
        selectedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
