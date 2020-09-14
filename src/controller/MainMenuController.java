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
 *                      MAIN MENU CONTROLLER Class <br>
 * --------------------------------------------------------------------- <br>
 * This class is responsible for the follow: <br>
 * Launches the Add/Modify Part GUI <br>
 * Launches the Add/Modify Product GUI <br>
 * Deletes Pats/Products from inventory <br>
 * Closes the program <br>
 */
public class MainMenuController implements Initializable {

    /**
     * Stage and Parent variables used to store and launch application GUI
     */
    Stage stage;
    Parent scene;

    /**
     * _____________________________________________________________________ <br>
     *                            FXML id selectors <br>
     * --------------------------------------------------------------------- <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private TextField searchPartsTxt;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private Button partsAddBtn;
    @FXML
    private Button partsModifyBtn;
    @FXML
    private Button partsDeleteBtn;
    @FXML
    private TextField productSearchTxt;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private Button productAddBtn;
    @FXML
    private Button productModifyBtn;
    @FXML
    private Button productDeleteBtn;
    @FXML
    private Button exitBtn;

    /**
     * _____________________________________________________________________  <br>
     *                      PART TABLE MENU ITEMS <br>
     * --------------------------------------------------------------------- <br>
     */

    /**
     * SEARCH AVAILABLE PART TABLE <br>
     * This method searches the available parts table which is populated with
     * the Inventory allParts list to find objects matching the search terms
     * either by partName or partID <br>
     * If no match is found an error message is displayed. <br>
     * @param event when the user presses enter in the search field the method
     *              executes a search <br>
     */
    @FXML
    public void onActionSearchParts(ActionEvent event) {

        /**
         * Test if search string is partID or partName
         */
        boolean isPartID = true;

        /**
         * If the search string is empty assume partName
         * An empty partName search will display allParts list
         */
        if(searchPartsTxt.getText().isEmpty()){
            isPartID = false;
        }

        /**
         * Loop through the search string to determine if the search string is a partID
         * If the search string is all digits assume partID
         * If the search string has any characters assume partName
         */
        for(int i = 0; i < searchPartsTxt.getText().length(); i++){
            if(!(Character.isDigit(searchPartsTxt.getText().charAt(i)))){
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
            Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartsTxt.getText()));

            /**
             * If searchPart is not null perform search on allParts list and display results in available parts table
             */
            if(searchPart != null) {
                partsTable.setItems(Inventory.lookupPart(searchPart.getName()));
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
            ObservableList<Part> searchPart = Inventory.lookupPart(searchPartsTxt.getText());

            /**
             * If searchPart is not null perform search on allParts list and display results in available parts table
             */
            if (searchPart != null) {
                partsTable.setItems(searchPart);
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
     * LAUNCH NEW PART MENU/CLASS <br>
     * @param event on button click launch the NEW part GUI
     * @throws IOException if GUI file is not found throw an exception
     */
    @FXML
    public void onActionDisplayAddPartMenu(ActionEvent event) throws IOException {

        /**
         * scene variable holds path to mainmenu file
         * stage variable loads main menu screen on button click
         */
        stage = (Stage) ((Button)(event.getSource())).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart_In.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * LAUNCH MODIFY PART MENU/CLASS <br>
     * If no part is selected to modify display error message <br>
     * Call sendPart method from ModifyPart controller to pass selected Part Object <br>
     * @param event on button click launch the MODIFY part GUI
     * @throws IOException if GUI file is not found throw an exception
     */
    @FXML
    public void onActionDisplayModifyPartMenu(ActionEvent event) throws IOException {

        try {
            /**
             * Create new FXML loader to load ModifyPart GUI
             */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart_In.fxml"));
            loader.load();

            /**
             * Assign ModifyPart Controller to new FXML Loader
             */
            ModifyPartInController MPIController = loader.getController();

            /**
             *call sendPart method from ModifyPart controller to pass selected Part Object
             */
            MPIController.sendPart(partsTable.getSelectionModel().getSelectedItem());

            /**
             * scene variable holds path to mainmenu file
             * stage variable loads main menu screen on button click
             */
            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        /**
         * Catch Clause - if no part is selected display error message
         */
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected to Modify.");
            alert.setContentText("Please select an item from the Parts Table to modify");
            alert.show();
        }
    }

    /**
     * DELETE PART FROM PART TABLE <br>
     * Prior to delete display confirmation dialog <br>
     * If user confirms delete perform delete action <br>
     * If no part is selected display error message <br>
     * @param event on button click display confirmation dialog and if confirmed perform delete action
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) {

        try {
            /**
             * selectedPartName variable reads available part table and gets part selected by user
             */
            String selectedPartName = partsTable.getSelectionModel().getSelectedItem().getName();

            /**
             * Prior to removing selected item display confirmation dialog box to confirm action
             */
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedPartName + "?");

            Optional<ButtonType> confirmResult = confirmAlert.showAndWait();

            /**
             * if user confirms action perform removal of selectedPart from list
             */
            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                if (!(Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No Part Selected");
                    alert.setContentText("Please select a part from the table to delete.");
                    alert.show();
                }
            }
        /**
         * Catch Clause - if no part is selected display error message
         */
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part from the table to delete.");
            alert.show();
        }

    }

    /**
     * _____________________________________________________________________
     *                      PRODUCT TABLE MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * SEARCH PRODUCT TABLE  <br>
     * This method searches the products table which is populated with
     * the Inventory allProducts list to find objects matching the search terms
     * either by productsName or productsID <br>
     * If no match is found an error message is displayed  <br>
     * @param event when the user presses enter in the search field the method
     *              executes a search
     */
    @FXML
    public void onActionSearchProduct(ActionEvent event) {
        /**
         *Test if search string is productID or productName
         */
        boolean isProductID = true;

        /**
         * If the search string is empty assume productName
         * An empty productName search will display allProducts list
         */
        if(productSearchTxt.getText().isEmpty()){
            isProductID = false;
        }

        /**
        * Loop through the search string to determine if the search string is a productID
        * If the search string is all digits assume productID
        * If the search string has any characters assume productName
        */
        for(int i = 0; i < productSearchTxt.getText().length(); i++){
            if(!(Character.isDigit(productSearchTxt.getText().charAt(i)))){
                isProductID = false;
            }
        }

        /**
         * If search string is a productID perform following search
         */
        if(isProductID){
            /**
             * searchProduct variable holds string from search TextField
             */
            Product searchProduct = Inventory.lookupProduct(Integer.parseInt(productSearchTxt.getText()));

            /**
             * If searchProduct is not null perform search on allProducts list and display results in products table
             */
            if(searchProduct != null) {
                productTable.setItems(Inventory.lookupProduct(searchProduct.getName()));
            }
            /**
             * If the searchProduct variable is null display error message
             */
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No match for name or product ID entered");
                alert.setContentText("Please try a different name or product ID.");
                alert.show();
            }
        }

        /**
         * If search string is not a productName assume productName and perform the following search
         */
        else {
            /**
             * searchProduct variable holds data from search TextField
             */
            ObservableList<Product> searchProduct= Inventory.lookupProduct(productSearchTxt.getText());

            /**
             * If searchProduct is not null perform search on allProducts list and display results in products table
             */
            if (searchProduct != null){
                productTable.setItems(searchProduct);
            }
            /**
             * If the searchProduct variable is null display error message
             */
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No match for name or product ID entered");
                alert.setContentText("Please try a different name or product ID.");
                alert.show();
            }
        }

    }

    /**
     * LAUNCH NEW PRODUCT MENU/CLASS  <br>
     * @param event on button click launch the NEW PRODUCT GUI
     * @throws IOException if GUI file is not found throw an exception
     */
    @FXML
    public void OnActionDisplayAddProductMenu(ActionEvent event) throws IOException {

        /**
         * scene variable holds path to mainmenu file
         * stage variable loads main menu screen on button click
         */
        stage = (Stage) ((Button)(event.getSource())).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * LAUNCH MODIFY PRODUCT MENU/CLASS  <br>
     * If no product is select an error message is displayed  <br>
     * Call sendProduct method from ModifyProduct controller to pass selected Product Object
     * to display data in appropriate fields   <br>
     * @param event on button click launch the MODIFY PRODUCT GUI
     * @throws IOException if GUI file is not found throw an exception
     */
    @FXML
    public void onActionDisplayModifyProductMenu(ActionEvent event) throws IOException {

        try {
            /**
             * Create new FXML loader to load ModifyProduct GUI
             */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            /**
             * Assign ModifyProduct Controller to new FXML Loader
             */
            ModifyProductController MPController = loader.getController();

            /**
             *call sendProduct method from ModifyProduct controller to pass selected Product Object
             */
            MPController.sendProduct(productTable.getSelectionModel().getSelectedItem());

            /**
             * scene variable holds path to mainmenu file
             * stage variable loads main menu screen on button click
             */
            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        /**
         * Catch Clause - if no part is selected display error message
         */
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product from the table to modify.");
            alert.show();
        }

    }

    /**
     * DELETE PRODUCT FROM PRODUCT TABLE  <br>
     * Prior to delete this method displays a confirmation dialog to the user   <br>
     * If the user confirms the delete action is performed   <br>
     * If no Product is selected an error message is displayed   <br>
     * @param event on button click display confirmation dialog and if confirmed perform delete action
     */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) {
        try{
            /**
             * selectedItem variable reads productTable and gets Product selected by user
             */
            Product selectedItem = productTable.getSelectionModel().getSelectedItem();
            String selectedItemName = productTable.getSelectionModel().getSelectedItem().getName();

            /**
             * Prior to removing selected item display confirmation dialog box to confirm action
             */
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedItemName + "?");
            Optional<ButtonType> confirmResult = confirmAlert.showAndWait();

            /**
             * if user confirms action perform removal of selected Product from list
             */
            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {


                try {
                    /**
                     * Test if product has associated parts, if associatedParts list is empty perform delete action.
                     */
                    if (selectedItem.getAllAssociatedParts().isEmpty()) {
                        Inventory.deleteProduct(selectedItem);
                    }
                    /**
                     * If Product has associated parts display error message.
                     * Cannot delete products with associated parts, must have empty associatedParts list.
                     */
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Cannot delete product:");
                        alert.setContentText("THIS PRODUCT HAS A/AN ASSOCIATED PART.\n\nProducts with associated " +
                                            "parts cannot be deleted until all associated parts have been removed. " +
                                            "Please modify the selected part to remove all ASSOCIATED PARTS first.");
                        alert.show();
                        }
                /**
                 * Catch Clause - if no asociatedParts list exists perform delete action
                 */
                }catch (NullPointerException e){
                    Inventory.deleteProduct(selectedItem);
                }
            }
        /**
         * Catch Clause - if no product is selected display error message
         */
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product from the table to delete.");
            alert.show();
        }
    }

    /**
     * _____________________________________________________________________
     *                      UNIVERSAL MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * EXIT PROGRAM   <br>
     * This method closes the program
     * @param event on button click end program.
     */
    @FXML
    public void onActionExit(ActionEvent event) {

        /**
         * End program
         */
        stage = (Stage) ((Button)(event.getSource())).getScene().getWindow();
        stage.close();

    }

    /**
     * POPULATE PART/PRODUCT TABLES   <br>
     * DEFAULT METHOD REQUIRED IN "Initializable"   <br>
     * This method loads a unique partID for all new Parts   <br>
     * @param url -
     * @param rb -
     */
    public  void initialize(URL url, ResourceBundle rb){

        /**
         * Get Parts from allParts list and Set table columns to appropriate values
         */
        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * Get Products from allProducts list and Set table columns to appropriate values
         */
        productTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
