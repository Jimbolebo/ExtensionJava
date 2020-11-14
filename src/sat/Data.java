package sat;
import java.util.Date;

/** 
 * A class describing a data.
 * Since there can be several types of measure (a simple value, a bitmap, etc...),
 * these types will be subclass of this class.
 * 
 * @author f.thomas
 */
public class Data {
    /** The date that indicates when the measure of the data was taken. */
    Date date;

    /**
     * Constructor.
     * It assigns the time when it is called to its attribute date.
     */
    public Data(){
        date = new Date();
    }
}
