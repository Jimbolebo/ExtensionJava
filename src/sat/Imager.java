package sat;

/**
 * A class describing an imager, which is a type of component
 * 
 * @author f.thomas
 */
public class Imager extends Component{

    /**
     * Constructor, with a String.
     * @param name The name of the imager.
     */
    public Imager(String name){
        super(name);
    }

    /** 
     * A method realizing the abstract method of the superclass Component.
     * @return It always return true, since the imager is obviously a
     * component of type imager.
     */
    boolean isImager(){
        return true;
    }


    /**
     * A method that take a measure with the Imager. For this first step of the project,
     * we assume that the imager just measure a double value, that we chose randomly.
     * @return A data containing the random measure, and the Date of this measurement.
     */
    Data teleMeasure(){
        return(new Bitmap(30,30));
    }
}