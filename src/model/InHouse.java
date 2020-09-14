package model;

/**
 * _____________________________________________________________________ <br>
 *                             InHouse Class <br>
 * --------------------------------------------------------------------- <br>
 * This is a subclass of the Part class <br>
 * This class contains constructors for the class and getter and setters <br>
 * for class specific variables <br>
 */

public class InHouse extends Part {

    int machineID;

    /**
     * InHouse Constructor <br>
     * --------------------------------------------------------------------- <br>
     * Frist calls the superclass constructor and sets machineID variable <br>
     * @param id set the id variable
     * @param name set the name variable
     * @param price set the price variable
     * @param stock set the stock/inventory variable
     * @param min set the min stock/inventory variable
     * @param max set the max stock/inventory variable
     * @param machineID set the machineID variable which is specific to the
     *                  InHouse subclass.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Setter for machineID
     * @param machineID sets the machineID variable
     */
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    /**
     * Getter for machineID
     * @return returns the machineID variable
     */
    public int getMachineID(){
        return machineID;
    }
}
