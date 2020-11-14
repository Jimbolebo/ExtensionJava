package sat;
import java.io.Serializable;
import java.time.Instant;

/** 
 * A class describing a data.
 * Since there can be several types of measure (a simple value, a bitmap, etc...),
 * these types will be subclass of this class.
 * 
 * @author f.thomas
 */
public class Data implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /** The date that indicates when the measure of the data was taken. */
    Instant date;
    String dataType;
    String geoLoc;


    /**
     * A method to get the date.
     * @return An instant corresponding to the date.
     */
    public Instant getDate(){
        return date;
    }

    /**
     * A method to get the type of data.
     * @return A string corresponding to the data type.
     */
    public String getType(){
        return dataType;
    }


    /**
     * A method to get the location.
     * @return A string corresponding to the location.
     */
    public String getLoc(){
        return geoLoc;
    }

    /**
     * Constructor.
     * It assigns the time when it is called to its attribute date.
     */
    public Data(String typeOfData){
        date = Instant.now();
        dataType = typeOfData;
        double lat = 180*Math.random()-90;
        double lon = 360*Math.random()-180;

        geoLoc = new String();
        if(lat>=0){
            if(lon>=0){
                geoLoc = Double.toString(lat)+"N"+Double.toString(lon)+"E";
            } else {
                geoLoc = Double.toString(lat)+"N"+Double.toString(-lon)+"W";
            }
        } else {
            if(lon>=0){
                geoLoc = Double.toString(-lat)+"S"+Double.toString(lon)+"E";
            } else {
                geoLoc = Double.toString(-lat)+"S"+Double.toString(-lon)+"W";
            }
        }
    }
}
