package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * _____________________________________________________________________
 *                          Product Class
 * ---------------------------------------------------------------------
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private int stock;
    private int min;
    private int max;
    private double price;
    private String name;

    /**
     * _____________________________________________________________________ <br>
     *                          Product Constructor <br>
     * --------------------------------------------------------------------- <br>
     * Constructor for Product Class <br>
     * @param id sets id variable
     * @param name sets name variable
     * @param price sets price variable
     * @param stock sets stock/inventory variable
     * @param min sets minimum stock/inventory variable
     * @param max sets maximum stock/inventory variable
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * _____________________________________________________________________
     *                               SETTERS
     * ---------------------------------------------------------------------
     */

    /**
     * Set Product ID
     * @param id id set the id variable
     */
    public void setId(int id){ this.id = id; }

    /**
     * Set Name
     * @param name name set the name variable
     */
    public void setName(String name){ this.name = name; }

    /**
     * Set Price
     * @param price set the price variable
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Set Stock
     * @param stock set the stock/inventory variable
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Set Min
     * @param min set the min variable
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Set Max
     * @param max set the max variable
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * _____________________________________________________________________
     *                               GETTERS
     * ---------------------------------------------------------------------
     */

    /**
     * Get ID
     * @return return the id variable
     */
    public int getId(){
        return  id;
    }

    /**
     * Get Name
     * @return return the name variable
     */
    public String getName(){
        return name;
    }

    /**
     * Get Price
     * @return return the price variable
     */
    public double getPrice(){
        return price;
    }

    /**
     * Get Stock
     * @return return the stock variable
     */
    public int getStock(){
        return stock;
    }

    /**
     * Get Min
     * @return returns the min variable
     */
    public int getMin(){
        return min;
    }

    /**
     * Get Max
     * @return return the max variable
     */
    public int getMax(){
        return max;
    }

    /**
     * __________________________________________________________
     *                associatedParts List Controls
     * ----------------------------------------------------------
     */

    /**
     * Add Part to Associated Parts List <br>
     * ---------------------------------------------------------- <br>
     * Add new part to associated parts array <br>
     * @param part new Part object added to the associated parts observable list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Delete Part from Associated Parts List <br>
     * @param selectedAssociatedPart remove parameter from Associated Parts List
     * @return return true if object is deleted, return null if not found
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.remove(selectedAssociatedPart)){
            return true;
        }
        return false;
    }

    /**
     * Get All Products from Associated Products List <br>
     * @return return associatedParts Observable List
     */
    public ObservableList<Part> getAllAssociatedParts(){
        if(!(associatedParts.isEmpty())) {
            return associatedParts;
        }
        else {
            return null;
        }
    }
}
