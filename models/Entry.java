package models;

/**
 * A single item
 * Created by Maxzolythus on 2/5/2017.
 */
public class Entry implements Groupable {
    private boolean hasChanged = false;
    private String name;
    private int quantity;
    private boolean status = false;

    public Entry(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Renames entry
     * @param newName the new name for the object
     */
    public void rename(String newName){
        name = newName;
        hasChanged = true;
    }

    /**
     * Gets if the object's status is accounted for or not
     * @return the boolean status
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * Switches the current status of the object
     */
    public void toggleStatus(){
        status = !status;
        hasChanged = true;
    }

    /**
     * Saves an entry to the database
     */
    public void save(){

    }

    /**
     * Loads an entry from the database
     */
    public void load(){

    }
}
