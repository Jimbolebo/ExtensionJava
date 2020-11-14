package sat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates the lost of the Satellites in the programm, along with their subsystem. The data used
 * are the one stored in the Architecture file. Read the doc to learn more about the needed sythaxe.
 */
public class ListofSat {

    ArrayList<Satelitte> listOfSat;

    /**
     * The constructor for the class, which creates the list of satellite
     */
    public ListofSat() {

        String e_name = null;

        this.listOfSat = new ArrayList<Satelitte>();
        try{
            //This part generate the list of the name of the satellites, thanks to the ARCHI.txt file
            FileReader in = new FileReader("src/Architecture/ARCHI.txt");
            BufferedReader bin = new BufferedReader(in);
        
            ArrayList<String> listOfName = new ArrayList<String>();
            while(bin.ready()) {
                String line = bin.readLine();
                listOfName.add(line);
		    }
		    bin.close();

            //For each Satellite name, we had a new Satellite object to the list
            for(int i = 0; i<listOfName.size();i++){
                e_name= listOfName.get(i);
                in = new FileReader("src/Architecture/ARCHI_"+e_name+".txt");
                bin = new BufferedReader(in);

                //Creation of the list of subsystem for a Satellite
                ArrayList<Component> listOfComp = new ArrayList<Component>();
                while(bin.ready()) {
                    String line = bin.readLine();
                    String[] data = line.split(":");
                    if(data.length != 2){
                        System.out.println("ERROR : Wrong synthaxe of file ARCHI_"+listOfName.get(i)+".txt");
                        System.exit(0);
                    } else if (data[0].equals("IMAGER")){
                        listOfComp.add(new Imager(data[1]));
                    } else {
                        System.out.println("ERROR : Component type "+data[0]+" doesn't exist");
                        System.exit(0);
                    }
                }
                this.listOfSat.add( new Satelitte(listOfComp, listOfName.get(i)));
            }
        } catch (IOException e){
            //Handle the exception due to an unexisting file
            if(e_name==null){System.out.println("ERROR : File src/Architecture/ARCHI.txt does not exists");}
            else{System.out.println("ERROR : File src/Architecture/ARCHI_"+e_name+".txt does not exists");}
            System.exit(0);
        }
    }

    /**
     * Method which return the size of the attribut listOfSat
     * @return The size of the attribut listOfSize
     */
    public int size(){
        return listOfSat.size();
    }

    public ArrayList<Satelitte> getList(){
        return listOfSat;
    }

    /**
     * 
     * @param i The index of the wanted Satellite
     * @return The wanted Satellite
     */
    public Satelitte get(int i){
        return listOfSat.get(i);
    }
}
