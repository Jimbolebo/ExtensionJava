package sat;
/**
 * A class describing a component. Since there can be several types of component,
 * the class Component is a superclass of the different class modeling the different
 * types of components (for example, Imager is a subclass of Component).
 * 
 * @author f.thomas
 */
abstract public class Component {
    /** The name of the component. */
    private String compoName;
    /** The state of the component : ON or OFF. */
    private String state;
    
    /** 
     * Constructor, with the name of the component.
     * It also initiates the state of the component to OFF.
     * @param name The name of the component.
    */
    public Component(String name){
        compoName = name;
        state = "OFF";
    }

    /**
     * This method gives us the current state of the component.
     * @return A string, "ON" or "OFF" depending on the state of the component.
     */
    String getState(){
        return state;
    }
    
    /** 
     * Switch on or switch off the component, depending on its 
     * current state. */
    void switchMode(){
        if (state == "OFF"){
            state = "ON";
        } else {
            state = "OFF";
        }
    }

    /**
     * Allows us to get the name of the component.
     * @return A string, the name of the component.
     */
    public String getName(){
        return compoName;
    }

    /**
     * This method is a way of checking what is the type of the component.
     * The definition of this method will therefore change according to the
     * differents subclass of Component.
     * @return A boolean, true if the component is an Imager, false otherwise.
     */
    abstract boolean isImager();

    /**
     * This method returns a measure when the control center asked a T/M to the 
     * component.
     * Since two components of different types will not work the same way, and 
     * therefore not take the same measure, the definition will change according
     * to the subclass of Component.
     * @return A data, which will be the measure the control center asked for.
     */
    abstract Data teleMeasure();
}