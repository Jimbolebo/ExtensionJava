package sat;
import java.util.ArrayList;
import java.util.Scanner;

public class ControlCenterMain {
    
    public static void main(String[] args){

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

        System.out.println("Welcome !");
        System.out.println("Enter a request by typing SATNAME:COMPONENTNAME:REQUEST in the terminal");
        System.out.println("Or ask for a script execution by typing SATNAME:SCRIPFILE in the terminal");
        System.out.println("Enter LEAVE if you want to leave the program.");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String retour = centre.sendRequests(line);
            if(retour.equals("STOP")){break;}
        }
        sc.close();
        
    }
}
