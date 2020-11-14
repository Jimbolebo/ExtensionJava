package sat;
import java.util.ArrayList;
/**
 * A class describing an archive, which is a collection of data. It'll be used to 
 * save measures from some satelittes after a request of T/M of the control center.
 * 
 * @author f.thomas
 */
public class Archive {
    /** A list of the data saved in the archive. */
    private ArrayList<Data> dataList;

    /**
     * Constructor
     * Initialise the ArrayList of data.
     */
    public Archive(){
         dataList = new ArrayList<Data>();
    }


    /**
     * A method that allows us to add a data to the archive.
     * @param data The data that will be added to te archive.
     */
    void addData(Data data){
        dataList.add(data);
    }
}
