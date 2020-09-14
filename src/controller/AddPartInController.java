package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * _____________________________________________________________________ <br>
 *                            ADD NEW PART CLASS <br>
 * --------------------------------------------------------------------- <br>
 * This class controls the Add New Part screen and controls the following: <br>
 * Reads data from form fields to save to new Part Object <br>
 * Saves new Part Object to Inventory allParts list <br>
 * Generates and sets unique partID for new Part <br>
 */

public class AddPartInController implements Initializable {

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
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
    @FXML
    private ToggleGroup manufacturerGroup;
    @FXML
    private TextField partIDTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField inventoryTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private Label manufacturerLabel;
    @FXML
    private TextField manufacturerTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    /**
     * _____________________________________________________________________
     *                     RADIO BUTTON ON-ACTION ITEMS
     * ---------------------------------------------------------------------
     * These methods set the radio buttons to selected when clicked by the user
     */

    /**
     * InHouse Radio Button
     * @param event on button click load machineID field and set label text
     */
    @FXML
    public void onActionLoadInHouseField(ActionEvent event) {
        manufacturerLabel.setText("Manufacturer ID:");
    }

    /**
     * Outsourced Radio Button
     * @param event on button click load companyName field and set label text
     */
    @FXML
    public void onActionLoadOutsourcedFields(ActionEvent event){
        manufacturerLabel.setText("Company Name:");
    }

    /**
     * _____________________________________________________________________
     *                         UNIVERSAL MENU ITEMS
     * ---------------------------------------------------------------------
     */

    /**
     * SAVE NEW PART <br>
     * --------------------------------------------------------------------- <br>
     * This method reads the form field inputs and saves the data as a new
     * Part Object in the allParts Inventory Observable List<br>
     * --------------------------------------------------------------------- <br>
     * @param event when the save button is clicked the method executes
     * @throws IOException if file main menu file is not found IOException is thrown
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException{

        /**
         * First detect if the new Part is an InHouse or Outsourced Part Object
         * by reading the selected radio buttons status
         */
        if (inHouseRBtn.isSelected()) {
            try {

                /**
                 * If the part is InHouse save form field input to variables, including MachineID
                 */
                int id = Integer.parseInt(partIDTxt.getText());
                int stock = Integer.parseInt(inventoryTxt.getText());
                int min = Integer.parseInt(minTxt.getText());
                int max = Integer.parseInt(maxTxt.getText());
                double price = Double.parseDouble(priceTxt.getText());
                String name = partNameTxt.getText();
                int manufacturerID = Integer.parseInt(manufacturerTxt.getText());

                /**
                 * Validate form fields Min must be less than Max
                 */
                if (min < max) {
                    /**
                     * Validate form fields stock must be less than max but greater than min
                     */
                    if (min < stock && stock < max) {
                        /**
                         * If form fields valid call InHouse constructor, save data to Inventory allParts List
                         * and launch main menu screen.
                         */
                        Inventory.addNewPart(new InHouse(id, name, price, stock, min, max, manufacturerID));

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
            } catch (NumberFormatException e) {
                Alert invalidEntryAlert = new Alert(Alert.AlertType.ERROR);
                invalidEntryAlert.setTitle("Error");
                invalidEntryAlert.setHeaderText("Invalid Entry");
                invalidEntryAlert.setContentText("One of the fields you entered was invalid.\n" +
                        "Name: Accepts Letter, Numbers, and Symbols.\n" +
                        "Inventory: DIGITS ONLY\n" +
                        "Price: DIGITS ONLY\n" +
                        "Min: DIGITS ONLY\n" +
                        "Max: DIGITS ONLY\n" +
                        "Machine ID: DIGITS ONLY\n");
                invalidEntryAlert.show();
            }

        }
        /**
         * Detect if the new Part is an InHouse or Outsourced Part Object
         * by reading the selected radio buttons status
         */
        else if (outsourcedRBtn.isSelected()) {
            try {
                /**
                 * If the part is InHouse save form field input to variables, including MachineID
                 */
                int id = Integer.parseInt(partIDTxt.getText());
                int stock = Integer.parseInt(inventoryTxt.getText());
                int min = Integer.parseInt(minTxt.getText());
                int max = Integer.parseInt(maxTxt.getText());
                double price = Double.parseDouble(priceTxt.getText());
                String name = partNameTxt.getText();
                String companyName = manufacturerTxt.getText();

                /**
                 * Validate form fields Min must be less than Max
                 */
                if (min < max) {
                    /**
                     * Validate form fields Min must be less than Max
                     */
                    if (min < stock && stock < max) {
                        /**
                         * If form fields valid call InHouse constructor, save data to Inventory allParts List
                         * and launch main menu screen.
                         */
                        Inventory.addNewPart(new Outsourced(id, name, price, stock, min, max, companyName));

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
            } catch (NumberFormatException e) {
                Alert invalidEntryAlert = new Alert(Alert.AlertType.ERROR);
                invalidEntryAlert.setTitle("Error");
                invalidEntryAlert.setHeaderText("Invalid Entry");
                invalidEntryAlert.setContentText("One of the fields you entered was invalid.\n" +
                        "Name: Accepts Letter, Numbers, and Symbols.\n" +
                        "Inventory: DIGITS ONLY\n" +
                        "Price: DIGITS ONLY\n" +
                        "Min: DIGITS ONLY\n" +
                        "Max: DIGITS ONLY\n" +
                        "Company Name: Accepts Letter, Numbers, and Symbols.\n");
                invalidEntryAlert.show();
            }
        }
    }

    /**
     * CANCEL NEW PART - LOAD MAIN MENU  <br>
     * Discard all data and return to main menu <br>
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
     * DEFAULT METHOD REQUIRED IN "Initializable" <br>
     * This method loads a unique partID for all new Parts <br>
     * @param url -
     * @param rb -
     */
    public  void initialize(URL url, ResourceBundle rb){

        /**
         * New partID is generated and assigned to partID text field
         * Users cannot edit part ID
         */
        partIDTxt.setText(String.valueOf(Inventory.generatePartID()));
        partIDTxt.setEditable(false);
    }

}
