package sat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.lang.InterruptedException;

public class ScriptModule {
    private ControlCenter controlcenter;
    private String last_statut;

    /**
     * Basic ScriptModule constructor.
     * @param controlcenter
     */
    public ScriptModule(ControlCenter controlcenter){
        this.controlcenter = controlcenter;
        this.last_statut="OK";
    }

    /**
     * Gets the command transmitted by the command center and
     * processes it to send the commands involved in the concerned script
     * to the command center respecting the indicated logical operations.
     * @param satName
     * @param filename
     * @return
     * @throws IOException
     */
    public String read(String satName,String filename) throws IOException{
        BufferedReader lecteurAvecBuffer = null;
        String ligne;
        System.out.println(filename+".txt");

        try{
	        lecteurAvecBuffer = new BufferedReader(new FileReader("SCRIPTS/"+filename+".txt"));
        }
        catch(FileNotFoundException exc){
            System.out.println("Error: Script File not found");
            return "KO";
        }
        // Reads all the lines until the script is finished or until an error occurs in he execution.
        while ((ligne=lecteurAvecBuffer.readLine()) != null && !this.last_statut.equals("STOP")){
            System.out.println(ligne);
            //Verifies if the current line is not a commentary.
            if(ligne.length()!=0){
                if(ligne.substring(0,1)!=";"){
                    // Implements the REPEAT operation.
                    if(ligne.split(" ")[0].equals("REPEAT")){
                        int n=Integer.parseInt(ligne.split(" ")[1]);
                        ligne=lecteurAvecBuffer.readLine();
                        for(int i=0;i<n;i++){
                            this.last_statut=controlcenter.sendRequests(satName+":"+ligne);
                        }
                    }
                    //Implements the ANTHEN condition.
                    else if(ligne.equals("ANDTHEN")){
                        ligne=lecteurAvecBuffer.readLine();
                        if (this.last_statut=="OK"){
                            this.last_statut=controlcenter.sendRequests(satName+":"+ligne);
                        }
                    }
                    //Implements the ORELSE condition.
                    else if(ligne.equals("ORELSE")){
                        ligne=lecteurAvecBuffer.readLine();
                        if(this.last_statut.equals("KO")){
                            this.last_statut=controlcenter.sendRequests(satName+":"+ligne);
                        }
                    }
                    //Implements the AT condition.
                    else if(ligne.split(" ")[0].equals("AT")){
                        OffsetDateTime dateTime = OffsetDateTime.now();
                        String date = dateTime.toString();
                        while(ligne.split(" ")[1]!=date){
                            dateTime = OffsetDateTime.now();
                            date = dateTime.toString();
                        }
                    }
                    //Implements the WAIT condition.
                    else if(ligne.split(" ")[0].equals("WAIT")){
                        try{
                            Thread.sleep(Long.parseLong(ligne.split(" ")[1]));
                        }
                        catch(InterruptedException e){
                            System.out.println("The WAIT operation was cut by another operation");
                        }                    
                    }
                    /**
                     * If the line is not concerned by all the conditions and operations above
                     * we can consider it is a basic command and send it to the controlcenter.
                     */
                    else{
                        this.last_statut=controlcenter.sendRequests(satName+":"+ligne);
                    }
                }
            }
            else{
                continue;
            }
        }
        lecteurAvecBuffer.close();
        return this.last_statut;
    }
}
