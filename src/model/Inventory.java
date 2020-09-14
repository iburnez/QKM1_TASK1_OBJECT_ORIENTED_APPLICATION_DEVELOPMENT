package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * _____________________________________________________________________<br>
 *                          Inventory Class<br>
 * ---------------------------------------------------------------------<br>
 * This class creates inventory ObservableList objects for parts
 * and Products <br>
 *
 * This class includes methods to facilitate the following:<br>
 *    * Add new Parts and Products to the Inventory ObservableLists<br>
 *    * Search through Inventory ObservableLists to find a Part or Product<br>
 *    * Update variables within Parts and Products to the
 *      Inventory ObservableLists<br>
 *    * Delete Parts or Products from Inventory ObservableLists<br>
 *    * Return full lists of Part or Product Inventory ObservableLists<br>
 *    * Generate unique Part and Product IDs<br>
 */

public class Inventory {

    private static int newPartID = 1000;
    private static int newProductID = 12000;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> selectedParts = FXCollections.observableArrayList();

    /**
     * _____________________________________________________________________<br>
     *                      Add Object Methods <br>
     * ---------------------------------------------------------------------<br>
     */

    /**
     * Add New PART Method<br>
     * ---------------------------------------------------------------------<br>
     * @param newPart Add new PART object to allParts Observable List<br>
     */
    public static void addNewPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Add New PRODUCT Method<br>
     * ---------------------------------------------------------------------<br>
     * @param newProduct Add new PRODUCT object to allProducts Observable List<br>
     */
    public static void addNewProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Add SELECTED PART Method<br>
     * ---------------------------------------------------------------------<br>
     * @param part add part to selectedParts ObservableList<br>
     */
    public static void addSelectedPart(Part part){
        selectedParts.add(part);
    }

    /**
     * _____________________________________________________________________<br>
     *                      LookUp Object Methods<br>
     * ---------------------------------------------------------------------<br>
     */

    /**
     * Lookup PART Method - @param: partID<br>
     * ---------------------------------------------------------------------<br>
     * Loop through allParts ObservableList and find matching partID<br>
     *<br>
     * @param partID used to find part with matching partID<br>
     * @return returns Part Object matching search partID or null if no match found<br>
     */
    public static Part lookupPart(int partID){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partID){
                return allParts.get(i);
            }
        }
        return null;
    }

    /**
     * Lookup PART Method - @param: partName<br>
     * ---------------------------------------------------------------------<br>
     * Uses filteredParts ObservableList as temp variable to hold all Parts
     * matching the search parameter <br>
     * Clear the filteredParts ObservableList @ start of every call<br>
     * Loop through allParts list and add all matching Parts to filteredParts<br>
     * If matches found return filteredList<br>
     * If no matches found return null<br>
     *<br>
     * @param partName used to find product with matching partName<br>
     * @return returns Part ObservableList matching search partName or null if no match found<br>
     */
    public static ObservableList<Part> lookupPart(String partName){

        //Clear filteredParts list before each lookup to ensure only new lookup results are returned
        if(!(Inventory.getFilteredParts().isEmpty())){
            Inventory.getFilteredParts().clear();
        }

        //Loop through allParts list and add all objects matching search criteria to filteredParts list
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())){
                filteredParts.add(allParts.get(i));
            }
        }

        //Test if filteredParts has any entries, if no entries found then return null
        if(!(Inventory.getFilteredParts().isEmpty())){
            return Inventory.getFilteredParts();
        }
        else{
            return null;
        }
    }

    /**
     * Lookup PRODUCT Method - @param: productID<br>
     * ---------------------------------------------------------------------<br>
     * Loop through allProducts ObservableList and find matching productID<br>
     *<br>
     * @param productID used to find product with matching productID<br>
     * @return returns product Object matching search productID or null if no match found<br>
     */
    public static Product lookupProduct(int productID){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productID){
                return allProducts.get(i);
            }
        }
        return null;
    }

    /**
     * Lookup PRODUCT Method - @param: productName<br>
     * ---------------------------------------------------------------------<br>
     * Uses filteredProducts ObservableList as temp variable to hold all Products
     * matching the search parameter <br>
     * Clear the filteredProducts ObservableList @ start of every call<br>
     * Loop through allProducts list and add all matching Products to filteredProducts<br>
     * If matches found return filteredProducts<br>
     * If no matches found return null<br>
     *<br>
     * @param productName used to find product with matching productName<br>
     * @return returns Product ObservableList matching search productName or null if no match found<br>
     */
    public static ObservableList<Product> lookupProduct(String productName){

        /**
         * Clear filteredProducts list before each lookup to ensure only new lookup results are returned <br>
         */
        if(!(Inventory.getFilteredProducts().isEmpty())){
            Inventory.getFilteredProducts().clear();
        }

        /**
         * Loop through allProducts list and add all objects matching search criteria to filteredProducts list <br>
         */
        for (int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())){
                filteredProducts.add(allProducts.get(i));
            }
        }

        /**
         * Test if filteredProducts has any entries, if no entries found then return allProducts List. <br>
         */
        if(!(Inventory.getFilteredProducts().isEmpty())) {
            return Inventory.getFilteredProducts();
        }
        else{
            return null;
        }
    }

    /**
     * _____________________________________________________________________<br>
     *                          Update Object Method<br>
     * ---------------------------------------------------------------------<br>
     */

    /**
     * Update Part Method<br>
     * ---------------------------------------------------------------------<br>
     * Loop through allParts to find Part Object with matching PartID, if found<br>
     * update that entry in the allParts list with new Parts Object info<br>
     *<br>
     * @param id uses partID as search parameter to find match in allParts<br>
     * @param selectedPart if a match is found this object is used to update entry in allParts list<br>
     */
    public static void updatePart(int id, Part selectedPart){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == id){
                Inventory.getAllParts().set(i, selectedPart);
            }
        }
    }

    /**
     * Update Product Method<br>
     * ---------------------------------------------------------------------<br>
     * Loop through allProducts to find Product Object with matching ProductID, if found<br>
     * update that entry in the allProduct list with new Product Object info<br><br>
     *
     * @param id uses productID as search parameter to find match in allProducts<br>
     * @param selectedProduct if a match is found this object is used to update entry in allProducts list<br>
     */
    public static void updateProduct(int id,Product selectedProduct){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == id){
                allProducts.get(i).setName(selectedProduct.getName());
                allProducts.get(i).setPrice(selectedProduct.getPrice());
                allProducts.get(i).setStock(selectedProduct.getStock());
                allProducts.get(i).setMin(selectedProduct.getMin());
                allProducts.get(i).setMax(selectedProduct.getMax());
            }
        }

    }

    /**
     * _____________________________________________________________________<br>
     *                          Delete Object Method<br>
     * ---------------------------------------------------------------------<br>
     */

    /**
     * Delete Part Method <br>
     * --------------------------------------------------------------------- <br>
     * @param selectedPart used as object to delete from allParts ObservableList <br>
     * @return return true if object is deleted, return null if not found <br>
     */
    public static boolean deletePart(Part selectedPart){
        if(allParts.remove(selectedPart)){
            return true;
        }
        return false;
    }

    /**
     * Delete Product Method <br>
     * --------------------------------------------------------------------- <br>
     * @param selectedProduct used as object to delete from allProducts ObservableList <br>
     * @return return true if object is deleted, return null if not found <br>
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.remove(selectedProduct)){
            return true;
        }
         return false;
    }

    /**
     * Delete SelectedParts Method <br>
     * --------------------------------------------------------------------- <br>
     * @param selectedPart used as object to delete from selectedParts ObservableList <br>
     * @return return true if object is deleted, return null if not found <br>
     */
    public static boolean deleteSelectedPart(Part selectedPart){
        return selectedParts.remove(selectedPart);
    }

    /**
     * _____________________________________________________________________<br>
     *                               GETTERS <br>
     * ---------------------------------------------------------------------<br>
     */

    /**
     * Get AllParts Method <br>
     * --------------------------------------------------------------------- <br>
     * @return return allParts ObservableList <br>
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Get AllProducts Method <br>
     * --------------------------------------------------------------------- <br>
     * @return return allProducts ObservableList <br>
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * Get FilteredProducts Method <br>
     * --------------------------------------------------------------------- <br>
     * @return returns filteredProducts ObservableList <br>
     */
    public static ObservableList<Product> getFilteredProducts(){
        return filteredProducts;
    }

    /**
     * Get FilteredParts Method <br>
     * --------------------------------------------------------------------- <br>
     * @return returns filteredParts ObservableList <br>
     */
    public static ObservableList<Part> getFilteredParts(){
        return filteredParts;
    }

    /**
     * Get SelectedParts Method <br>
     * --------------------------------------------------------------------- <br>
     * @return returns selectedParts ObservableList <br>
     */
    public static ObservableList<Part> getSelectedParts(){
        return selectedParts;
    }

    /**
     * _____________________________________________________________________ <br>
     *                           Generate Object Ids <br>
     * --------------------------------------------------------------------- <br>
     */

    /**
     * Generate PartID Method <br>
     * --------------------------------------------------------------------- <br>
     * Starts partID at fixed value and increments value after each call <br>
     * @return returns unique partID <br>
     */
    public static int generatePartID(){
        newPartID++;
        return newPartID;
    }

    /**
     * Generate ProductID Method <br>
     * --------------------------------------------------------------------- <br>
     * Starts productID at fixed value and increments value after each call <br>
     * @return returns unique productID <br>
     */
    public static int generateProductID(){
        newProductID++;
        return newProductID;
    }
}
