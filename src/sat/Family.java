package sat;
import java.util.ArrayList;

/**
 * A class describing a family of satelittes.
 * 
 * @author f.thomas
 */
public class Family {

    /** The name of the family. */
    private String familyName;
    /** The list of component all the satelittes of the family have. */
    private ArrayList<Component> compoList;
    /** The "size" of the family, i.e the number of satelittes in the family. */
    private int familySize;

    /** 
     * Constructor, with a name and a list.
     * It gives a name to the family, the list of component that all the satelittes 
     * of the family will have. It also initiates the size of the family at zero.
     * @param name The name given to the family.
     * @param listOfComp The list of component.
     */
    public Family(String name, ArrayList<Component> listOfComp){
        familyName = name;
        compoList = listOfComp;
        familySize=0;
    }
    
    /** 
     * Allows us to get the name of the family.
     * @return A string, the name of the family. 
    */
    String getFamilyName(){
        return familyName;
    }

    /** 
     * Allows us to get the size of the family.
     * @return An integer, the number of satelittes belonging to the family.
    */
    int getSize(){
        return familySize;
    }

    /** Increase the size of the family by one (used when we add a satelitte to it). */
    void sizeIncrease(){
        familySize+=1;
    }

    /**
     * A method that allows us to get the number of components the satelittes of
     * a family have.
     * @return An integer, the number of components of the family's satelittes.
     */
    int numberOfComp(){
        return compoList.size();
    }

    /**
     * A method returning a component which has the same characteristics as the one 
     * at the index i of the list of components of the family (but not the same place in memory).
     * 
     * This method avoids the sharing of the SAME component (in memory) between two 
     * satelittes of the same family, when adding them in it (which would be the case
     * if we were to directly assign the list of components of the family to the lists
     * of components of the family's satelittes).
     * @param i The index of the component we want to "copy".
     * @return A component which has the same characteristics as the one at the
     * index i of the list, but which does not have the same place in the memory.
     */
    Component copyCompo(int index){
        if(compoList.get(index).isImager() == true){
            return(new Imager(compoList.get(index).getName())); // create a new Imager, with the same name
        }
        return null;
    }

}