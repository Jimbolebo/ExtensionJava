package research;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import sat.Data;


/**
 * A class used to simulate a research app to search data on the hardware.
 * 
 * @author f.thomas
 */
public class ResearchAppMain {

    public static void main(String[] args) throws ClassNotFoundException, IOException {


        Indexation index = new Indexation();

        System.out.println("Welcome !");
        System.out.println("Enter a research request by typing DATE1,DATE2;SAT1,SAT2,SAT3;MEASURETYPE1,MEASURETYPE2;GEOLOC1,GEOLOC2 in the terminal");
        System.out.println("If you don't want to use one of the research parameters, type ALL instead of it. For example, ALL;ISAESAT1;Bitmap;ALL");
        System.out.println("Use ',' if you want to use several parameters for the satelittes or the data types. Use ';' to jump to the next parameter.");
        System.out.println("Enter LEAVE if you want to leave the program.");

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String[] line = sc.nextLine().split(";");

            if(line[0].contentEquals("LEAVE")){
                System.out.println("Bye !");
                break;
            }

            /** Checks if the request has the right shape, to prevent mistakes. */
            if(line.length != 4){
                System.out.println("ERROR : your request doesn't have the right form.");
                break;
            }

            /** Get the informations contained in the request. */
            String[] dates = line[0].split(",");
            String[] listOfSat = line[1].split(",");
            String[] listOfType = line[2].split(",");
            String[] listOfLoc = line[3].split(",");

    
                
            ArrayList<String> listOfFilesFound = index.searchByAll(dates, listOfSat, listOfType, listOfLoc);

            if(listOfFilesFound.size() > 25){

                System.out.println("Too much results founded ("+listOfFilesFound.size()+"), be more specific in your criterias.");

            } else if (listOfFilesFound.size() == 0){

                System.out.println("0 file founded.");
                System.out.println("Be less specific in your request, or verify that you didn't make mistakes in the spelling of the satelittes, or the types of data, etc...");

            } else {
                /** We display the information contained in the files, if their number isn't too important. */
                System.out.println(listOfFilesFound.size() + " files founded.");
                for(String path : listOfFilesFound){

                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
                    Data data = (Data) in.readObject();
                    in.close();

                    System.out.println(path + ": "+ data.getDate()+", "+path.split("/")[3]+", "+data.getType()+", "+data.getLoc());
                }
            }
            System.out.println("Enter your next request or type LEAVE to leave the program.");
        }
        sc.close();

    }
}