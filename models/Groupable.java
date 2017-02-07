package models;

/**
 * An interface that categorizes all that can be grouped
 * Created by Maxzolythus on 2/5/2017.
 */

public interface Groupable {

    /**
     * Method that saves Groupable object to the database
     */
    public void save();

    /**
     * Method that loads Groupable object from database
     */
    public void load();

    /**
     * Method that returns the status of a groupable object
     * @return boolean status
     */
    public boolean getStatus();

    /**
     * Renames Groupable object
     * @param newName the new name for the object
     */
    public void rename(String newName);
}
