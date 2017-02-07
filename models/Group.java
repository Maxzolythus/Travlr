package models;

import java.util.ArrayList;

/**
 * A group of items or other groups
 * Created by Maxzolythus on 2/5/2017.
 */
public class Group implements Groupable {
    private ArrayList<Groupable> list = new ArrayList<>();
    private boolean hasChanged = false;
    private String name;

    public Group(String name){
        this.name = name;
    }

    /**
     * Renames group
     * @param newName the new name for the object
     */
    public void rename(String newName){
        name = newName;
        hasChanged = true;
    }

    /**
     * Gets if all objects in the group are accounted for
     * @return boolean status
     */
    public boolean getStatus(){
        boolean result = false;

        if(list.isEmpty()){
            return result;
        }
        else{
            for(Groupable i : list){
                if(!i.getStatus()){
                    return false;
                }
                else{
                    result = true;
                }
            }
            return result;
        }
    }

    /**
     * Saves a group to the database
     */
    public void save(){

    }

    /**
     * Loads a group to from database
     */
    public void load(){

    }

}
