package sat;
import java.util.ArrayList;
/**
 * A class describing the object "Satelitte"
 * 
 * @author f.thomas
 */
public class Satelitte {
    /** The list of the components of the satelitte. */
    private ArrayList<Component> compoList;
    /** The name of the satelitte */
    private String satName;

    /** 
     * Constructor, with family.
     * It detects the number of satelittes already in the family to automatically name
     * the satelitte by using the name of the family, the word "SAT", and an integer.
     * It also increase the size of the family (since we're adding one satelitte to it).
     * 
     * It creates the list of components of the satelitte by going through the list
     * of components of the family, and adding newly created components with the
     * same characteristics to the list of the satelitte.
     * @param family The family to which the satelitte belongs.
     */
    public Satelitte(Family family){
        int i = family.getSize()+1;
        String name = family.getFamilyName()+"SAT"+String.valueOf(i);
        satName = name;
        family.sizeIncrease();

        /**
         * Fullfil the list of components of the satelitte by going through the one of the family and creating
         * new ones with the same characteristics, but not the same place in memory.
         */
        compoList = new ArrayList<Component>();
        for(int j =0; j < family.numberOfComp(); j++){
            compoList.add(family.copyCompo(j));
        }
        
    }

    
     /**
      * Allows us to obtain the name of the satelitte.
      * @return A string which is the name of the satelitte.
      */
    public String getName(){
        return satName;
    }

    /** 
     * This loop allows us to find the index of the component requested
     * in the compoList thanks to its name. 
     */

    int getSize(){
        return compoList.size();
    }

    /**
     * A method that allows us to know the index of a given component in the list of components of the satelitte,
     * thanks to the component name.
     * @param compoName The name of the component we're looking for.
     * @return The index of the component in the list. If we don't find it, it returns the size of the list.
     */
    int getCompoIndex(String compoName){
        int j = 0;
        while(j < compoList.size()){
            if (compoList.get(j).getName().contentEquals(compoName) == true) {
                break;
            }
            j++;
        }
        return j;
    }

    /**
     * A method that allows us to know if a given component is ON or OFF.
     * @param compoIndex The index of the component we should check.
     * @return A boolean, true if the component is OFF, false otherwise.
     */
    boolean isTheCompoOff(int compoIndex){
        return compoList.get(compoIndex).getState().contentEquals("OFF");
    }

    /**
     * Executes a request on this satelitte, by identifying the right component and
     * the request to execute, while preventing errors by returning a boolean.
     * @param compoName The name of the component to which the request is adressed.
     * @param request The request asked.
     * @return A boolean : false if we didn't encountered an issue with the request,
     * true otherwise (for example if we don't recognize the name of the component).
     * Returning this boolean allows us to get this information in the while loop of 
     * ControlCenter and leave the program if an error occurs.
     */
    String teleCommand(int compoIndex, String request){
        if(compoList.get(compoIndex).getState().contentEquals(request) == true){
            System.out.println("KO");
            return "KO";
        } else {
            compoList.get(compoIndex).switchMode();
            System.out.println("OK");
            return "OK";
        }
    }

    /**
     * A method that asks a component to take a measure.
     * @param compoIndex The index of the component that will take a measure.
     * @return The data collected after the T/M.
     */
    Data teleMeasure(int compoIndex){
        return compoList.get(compoIndex).teleMeasure();
    }


}
