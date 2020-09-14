package model;

/**
 * _____________________________________________________________________ <br>
 *                             Outsourced Class <br>
 * --------------------------------------------------------------------- <br>
 * Outsourced Class is a subclass of the Part class <br>
 * This class constructs OutSourced Part objects <br>
 * This class contains getters and setters for class specific variables <br>
 */

public class Outsourced extends Part{

    String companyName;

    /**
     * Outsourced Constructor <br>
     * --------------------------------------------------------------------- <br>
     * @param id sets the id variable
     * @param name sets the name variable
     * @param price sets the price variable
     * @param stock sets the stock/inventory variable
     * @param min sets the minimum stock/inventory variable
     * @param max sets the maximum stock/inventory variable
     * @param companyName sets the companyName variable
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for Company Name
     * @param companyName sets the company name variable
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Getter for Company Name
     * @return returns the companyName variable
     */
    public String getCompanyName(){
        return companyName;
    }
}
