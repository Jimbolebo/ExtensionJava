package sat;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import research.Indexation;

/**
 * A class describing an archive, which is a collection of data. It'll be used
 * to save measures from some satelittes after a request of T/M of the control
 * center.
 * 
 * @author f.thomas
 */
public class Archive {
    /** A list of the data saved in the archive. */
    private ArrayList<Data> dataList;

    /**
     * Constructor Initialise the ArrayList of data.
     */
    public Archive() {
        dataList = new ArrayList<Data>();
    }

    /**
     * A method that allows us to add a data to the archive.
     * 
     * @param data The data that will be added to te archive.
     * @throws FileNotFoundException
     */


    /**
     * A method used to add a data to the archive. It is also stored on the hardware.
     * @param data The data to add.
     * @param satName The name of the satelitte used to take the data.
     * @throws IOException
     */
    void addData(Data data, String satName) throws IOException{
        /** Add the data to the archive. */
        dataList.add(data);

        /** Read the name of the file that will be used to store the data in binary. */
        String path = "src/sat/DATA/"+satName+"/NEXTSEQNUM.txt";

        BufferedReader in = new BufferedReader(new FileReader(path));
        String seqNumString = in.readLine();
        int seqNum = Integer.parseInt(seqNumString);
        in.close();

        /** Change the name contained in the file NEXTSEQNUM. It is increased by one. For example, it will change from "000000021" to "000000022". */
        PrintWriter out = new PrintWriter(path);
        out.println(Indexation.intNewForm(seqNum+1));
        out.close();

        /** Creates a new binary file corresponding to the data and stores it. */
        String path2 = "src/sat/DATA/"+satName+"/"+seqNumString+".bin";

        File newFile = new File(path2);
        newFile.createNewFile();

        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(path2));
        out2.writeObject(data);
        out2.close();

    }
}
