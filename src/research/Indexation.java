package sat;

import java.util.TreeMap;
import java.util.Map.Entry;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
/**
 * A class describing a structure of an index.
 * 
 * @author f.thomas
 */
public class Indexation {

    /** A map containing lists of data, according to the date it were taken. I used a TreeMap instaed of a HashMap in order to have access to the method subMap.   */
    private TreeMap<Instant, ArrayList<String>> dataByDate;
    /** A map containing lists of data, according to the satelitte which took them. */
    private HashMap<String, ArrayList<String>> dataBySat;
    /** A map containing lists of data, according to their type. */
    private HashMap<String, ArrayList<String>> dataByType;
    /** A map containing lists of data, according to their geolocation. */
    private HashMap<String, ArrayList<String>> dataByLoc;

    /** 
     * Constructor
     * It creates the four map used to index the data.
     */
    public Indexation() throws IOException, ClassNotFoundException {
        dataByDate = new TreeMap<Instant, ArrayList<String>>();
        dataBySat = new HashMap<String, ArrayList<String>>();
        dataByType = new HashMap<String, ArrayList<String>>();
        dataByLoc = new HashMap<String, ArrayList<String>>();


        /** 
         * It reads a txt file where all the names of the satelittes are written. This file is created after an execution of the ControlCenterMain according to the satelitte
         * used to create it.
         */
        BufferedReader in = new BufferedReader(new FileReader("src/sat/DATA/satOfTheControlCenter.txt"));
        String allSatNames = in.readLine();
        in.close();

        String[] listOfSatName = allSatNames.split(":");


        for(int i =0; i < listOfSatName.length; i++){
            String satName = listOfSatName[i];
            String path = "src/sat/DATA/"+satName;
            
            /** For each satelitte, it reads the number of data taken, in order to read each one then. */
            BufferedReader in2 = new BufferedReader(new FileReader(path+"/NEXTSEQNUM.txt"));
            int numberOfData = Integer.parseInt(in2.readLine());
            in2.close();

            /** Creates the list that will be used to add an element to the map whose key is the satelitte name. */
            ArrayList<String> listOfFiles = new ArrayList<String>();
        

            for(int j = 0; j < numberOfData; j++){

                String path2 = path+"/"+intNewForm(j)+".bin";
                ObjectInputStream in3 = new ObjectInputStream(new FileInputStream(path2));
                Data dataRead = (Data) in3.readObject();
                in3.close();

                Instant date = dataRead.date;
                
                /** Checks if the date already exists in the map : the process used to add the data would change according to it. */
                if(dataByDate.containsKey(date)){
                    dataByDate.get(date).add(path2);
                } else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add(path2);
                    dataByDate.put(date, newList);
                }

                listOfFiles.add(path2);
                
                /** Same approach with the type of data. */
                String dataType = dataRead.dataType;
                if(dataByType.containsKey(dataType)){
                    dataByType.get(dataType).add(path2);
                } else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add(path2);
                    dataByType.put(dataType, newList);
                }

                /** Same approach with the geolocation. */
                String dataLoc = dataRead.geoLoc;
                if(dataByLoc.containsKey(dataLoc)){
                    dataByLoc.get(dataLoc).add(path2);
                } else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add(path2);
                    dataByLoc.put(dataLoc, newList);
                }
                

            }

            dataBySat.put(satName, listOfFiles);
        }
    }

    /** 
     * A method used to convert a integer to a string BUT with the following form : "nnnnnnnnn". For example, intNewForm(104) would return "000000104".
     * @param n The integer to convert.
     * @return The string corresponding to the integer, with the right form. 
     */
    public static String intNewForm(int n){
        String intString = new String();
        int a = n;
        int b = 0;
        while((a/10)!=0){
            b+=1;
            a = a/10;
        }

        for(int i=0; i < 8-b; i++){
            intString += "0";
        }
        intString += Integer.toString(n);
        return intString;

    }

    /**
     * A method allowing us to search all the data taken between two dates.
     * @param dates An array of size 2, where the two elements are the two dates. The array can be of size 1 and contain "ALL". Therefore, it means we doesn't use the date criteria.
     * @return An ArrayList of String, where each element is a data path corresponding to the criterias used.
     */
    public ArrayList<String> searchByDate(String[] dates){
        ArrayList<String> listOfFilesFound = new ArrayList<String>();

        if(dates[0].contentEquals("ALL")){
            for(Entry<Instant, ArrayList<String>> data : dataByDate.entrySet()){
                listOfFilesFound.addAll(data.getValue());
            }
        } else {
            try{
                Instant.parse(dates[0]);
            } catch(DateTimeParseException dtpe) {
                System.out.println("Wrong date format.");
                return listOfFilesFound;
            }
            try {
                Instant.parse(dates[1]);
            } catch(DateTimeParseException dtpe) {
                System.out.println("Wrong date format.");
                return listOfFilesFound;
            }
            Instant date1 = Instant.parse(dates[0]);
            Instant date2 = Instant.parse(dates[1]);
            try{
                dataByDate.subMap(date1,date2);
            } catch(IllegalArgumentException iae){
                System.out.println("ERROR : Date 2 must be > date 1");
                return listOfFilesFound;
            }
            SortedMap<Instant, ArrayList<String>> newMap = dataByDate.subMap(date1,date2);
            for(Entry<Instant, ArrayList<String>> data : newMap.entrySet()){
                listOfFilesFound.addAll(data.getValue());
            }
        }
        return listOfFilesFound;
    }


    /**
     * A method allowing us to search all the data taken by a list of satelittes.
     * @param listOfSatName An array containing a list of satelittes names used to select the right datas. If the first element is "ALL", the name criteria isn't used.
     * @return An ArrayList of String, where each element is a data path corresponding to the criterias used.
     */
    public ArrayList<String> searchByName(String[] listOfSatName){
        ArrayList<String> listOfFilesFound = new ArrayList<String>();
        
        if(listOfSatName[0].contentEquals("ALL")){
            for(Entry<String, ArrayList<String>> data : dataBySat.entrySet()){
                listOfFilesFound.addAll(data.getValue());
            }
        } else {
            for(String satName : listOfSatName){
                if(dataBySat.containsKey(satName)){
                    listOfFilesFound.addAll(dataBySat.get(satName));
                }
            }
        }
        return listOfFilesFound;
    }

    /**
     * A method allowing us to search all the data taken according to a list of data types.
     * @param listOfDataType An array containinga a list of the data types, corresponding to the only type we want to search. If the first element is "ALL", we don't use this criteria.
     * @return An ArrayList of String, where each element is a data path corresponding to the criterias used.
     */
    public ArrayList<String> searchByType(String[] listOfDataType){
        ArrayList<String> listOfFilesFound = new ArrayList<String>();

        if(listOfDataType[0].contentEquals("ALL")){
            for(Entry<String, ArrayList<String>> data : dataBySat.entrySet()){
                listOfFilesFound.addAll(data.getValue());
            }
        } else {
            for(String dataType : listOfDataType){
                if(dataByType.containsKey(dataType)){
                    listOfFilesFound.addAll(dataByType.get(dataType));
                }
            }
        }
        return listOfFilesFound;
    }

    public ArrayList<String> searchByLoc(String[] listOfLoc){
        ArrayList<String> listOfFilesFound = new ArrayList<String>();
        if(listOfLoc[0].contentEquals("ALL")){
            for(Entry<String, ArrayList<String>> data : dataByLoc.entrySet()){
                listOfFilesFound.addAll(data.getValue());
            }
        } else {
            for(String dataLoc : listOfLoc){
                if(dataByLoc.containsKey(dataLoc)){
                    listOfFilesFound.addAll(dataByLoc.get(dataLoc));
                }
            }
        }
        return listOfFilesFound;
    }

    /**
     * A method that realizes the intersection between two lists.
     * @param list1 The first list to intersect.
     * @param list2 The second list to intersect.
     * @return The list obtained with the intersection, i.e whose elements are the elements contained in the first list AND the second list.
     */
    public ArrayList<String> intersection(ArrayList<String> list1, ArrayList<String> list2){
        ArrayList<String> list3 = new ArrayList<String>();

        for(String s1 : list1){
            for(String s2 : list2){
                if(s1.contentEquals(s2)){
                    list3.add(s1);
                }
            }
        }
        return list3;

    }


    /**
     * A method allowing us to search data according to the four types of criterias.
     * @param dates
     * @param listOfSat
     * @param listOfDataType
     * @return
     */
    public ArrayList<String> searchByAll(String[] dates, String[] listOfSat, String[] listOfDataType, String[] listOfLoc){

        ArrayList<String> filesByDate = searchByDate(dates);
        ArrayList<String> filesByName = searchByName(listOfSat);
        ArrayList<String> filesByType = searchByType(listOfDataType);
        ArrayList<String> filesByLoc = searchByLoc(listOfLoc);

        /** We use the intersection method to keep only the data corresponding to the four criterias. */
        ArrayList<String> listOfFilesFound = intersection(intersection(intersection(filesByDate, filesByName), filesByType),filesByLoc);

        return listOfFilesFound;


    }

}

