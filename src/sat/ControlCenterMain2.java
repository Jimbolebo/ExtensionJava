package sat;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class ControlCenterMain {

    public static void main(String[] args) throws IOException {

        ArrayList<Component> listOfComp = new ArrayList<Component>();
        listOfComp.add(new Imager("IMAGER1"));
        listOfComp.add(new Imager("IMAGER2"));

        Family isae = new Family("ISAE", listOfComp);
        Satelitte sat1 = new Satelitte(isae);
        Satelitte sat2 = new Satelitte(isae);
        Satelitte sat3 = new Satelitte(isae);

        listOfComp.add(new Imager("IMAGER3"));

        Family x = new Family("X", listOfComp);
        Satelitte sat4 = new Satelitte(x);
        Satelitte sat5 = new Satelitte(x);

        ArrayList<Satelitte> listOfSat = new ArrayList<Satelitte>();
        listOfSat.add(sat1);
        listOfSat.add(sat2);
        listOfSat.add(sat3);
        listOfSat.add(sat4);
        listOfSat.add(sat5);

        ControlCenter centre = new ControlCenter(listOfSat);


        /** Writes in a file the name of the satelittes of the control center. It will be used for the indexation of the data stored on the hardware. */
        PrintWriter out = new PrintWriter(new FileWriter("src/sat/DATA/satOfTheControlCenter.txt"));
        for(Satelitte sat : listOfSat){
            out.print(sat.getName()+":");

            /** It also creates a folder to store the data for each satelitte. It also creates the file NEXTSEQNUM.txt used to know the name of memory file we should create. */
            String path1 = "src/sat/DATA/"+sat.getName();
            File file = new File(path1);
            file.mkdir();

            String path2 = path1 + "/NEXTSEQNUM.txt";
            File file2 = new File(path2);
            if(!file2.exists()){
                PrintWriter out2 = new PrintWriter(new FileWriter(path2));
                out2.println("000000000");
                out2.close();
            }
        }
        out.close();


        System.out.println("Welcome !");
        System.out.println("Enter a request by typing SATNAME:COMPONENTNAME:REQUEST in the terminal");
        System.out.println("Enter LEAVE if you want to leave the program.");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String retour = centre.sendRequests(line);
            if(retour.equals("STOP")){break;}
            System.out.println(retour);
        }
        sc.close();
        
    }
}
