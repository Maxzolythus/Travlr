package controlers;

import models.Groupable;
import models.MainModel;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    private static MainModel model = new MainModel();

    /**
     * Creates a new Entry in the model
     * @param name Name of the entry
     * @param quantity Amount of the entry
     */
    public void addEntry(String name, int quantity){
        model.addEntry(name, quantity);
    }

    /**
     * Creates a new Group in the model
     * @param name Name of the group
     */
    public void addGroup(String name){
        model.addGroup(name);
    }

    /**
     * Renames a groupable object's name in the model's hash
     * @param name The original name
     * @param newName The new name
     */
    public void rename(String name, String newName){
        model.rename(name, newName);
    }

    public void changeQuantity(String name, int quantity){
        model.changeQuantity(name, quantity);
    }

    /**
     * Deletes a groupable object from the model's hash
     * @param name The name of the object to delete
     */
    public void delete(String name){
        model.delete(name);
    }

    @Override
    public void update(Observable o, Object arg) {
       return;
    }

    public HashMap<String, Groupable> getCheckList(){
        return model.getCheckList();
    }
}
