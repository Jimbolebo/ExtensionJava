package sat;
import java.io.IOException;


/**
 * A class modeling the control center.
 * It'll be used to send T/M and T/C to the satelittes linked to the center.
 * 
 * @author f.thomas
 */
public class ControlCenter {
    /** The list of satelittes that the control center can command. */
    private ListofSat satList;
    /** 
     * The archive containing the data collected by the control center thanks to 
     * its satelittes.
     */
    private Archive archive;

    /**
     * The script processing engine.
     */
    private ScriptModule scriptModule;

    /** 
     * Constructor, with a list of sat.
     * @param listOfSat The list of satelittes linked to the control center that 
     * we create.
     */
    public ControlCenter(){
        satList = new ListofSat();
        archive = new Archive();
        scriptModule = new ScriptModule(this);
    }

    /**
     * A method that allows us to know the index of a given satelitte in the list of satelittes of the Control Center,
     * thanks to the satelitte name.
     * @param satName The name of the satelitte we're looking for.
     * @return The index of the satelitte in the list. If we don't find it, it returns the size of the list.
     */
    int getSatIndex(String satName){

        int i = 0;
        while(i < satList.size()){
            if (satList.get(i).getName().contentEquals(satName) == true){
                break;
            }
            i++;
        }
        return i;
        
    }

    public ListofSat getsatList(){
        return satList;
    }

    public ScriptModule gScriptModule(){
        return scriptModule;
    }

    /**
     * Processes the request sent by the user of by the ScriptModule and returns a
     * String element indicating the state of the executed operation.
     * 
     * @param line
     * @return
     * @throws IOException
     */
    public String sendRequests(String line) {
            /** Leave the loop if we enter the request LEAVE. */
            if(line.contentEquals("LEAVE")==true){
                System.out.println("Bye !");
                return("STOP");
            }

            
            if(line.split(":").length ==3){

                String satName = line.split(":")[0];
                String compoName = line.split(":")[1];
                String request = line.split(":")[2];
            
                /**
                 * If the request isn't one the three known requests, we display an
                 * error message and leave the loop.
                 */
                if(!(request.contentEquals("ON") || request.contentEquals("OFF") || request.contentEquals("DATA"))){
                    System.out.println("ERROR : This request is unknown.");
                    return("KO");
                }

                int satIndex = getSatIndex(satName);
                /** 
                 * If we don't recognize the satelitte requested in our list, we
                 * display an error message and leave the loop.
                 */
                if(satIndex==satList.size()){
                    System.out.println("ERROR : The Control Center doesn't have this satelitte.");
                    return("KO");
                }
        
                int compoIndex = satList.get(satIndex).getCompoIndex(compoName);

                /** 
                 * If we don't recognize the component requested in the list of the
                 * satelitte requested, we display an error message and leave the loop.
                 */
                if(compoIndex==satList.get(satIndex).getSize()){
                    System.out.println("ERROR : the satelitte doesn't have this component.");
                    return("KO");
                    }

                /** 
                 * After finding the satelitte and the component, we check if the request
                 * is a T/M or a T/C. Then, we execute the request.
                 */
                if(!request.contentEquals("DATA")){
        
                    return satList.get(satIndex).teleCommand(compoIndex, request);

                } else {

                    /** We check if the component requested is on, otherwise it wouldn't be able to take a measure. */
                    if(satList.get(satIndex).isTheCompoOff(compoIndex)){
                        System.out.println("ERROR : You have to activate this component before asking for a T/M.");
                        return "KO";
                    }

                    Data data = satList.get(satIndex).teleMeasure(compoIndex);
                    try {
                        archive.addData(data, satList.getList().get(satIndex).getName());
                    } catch (IOException e) {
                        System.out.println("Archive error");
                       return "STOP";
                    } // add the data collected to the archive
                    return "OK";
                }
            }
            /**
             * This condition calls the ScriptModule to read the command.
             */
            else if(line.split(":").length ==2){
                String satName = line.split(":")[0];
                String script = line.split(":")[1];

                int satIndex = getSatIndex(satName);
                /** 
                 * If we don't recognize the satelitte requested in our list, we
                 * display an error message and leave the loop.
                 */
                if(satIndex==satList.size()){
                    System.out.println("ERROR : The Control Center doesn't have this satelitte.");
                    return("KO");
                }
                /**
                 * Transfers the command to the ScriptModule.
                 */
                try{
                    return scriptModule.read(satName,script);
                }
                catch(IOException exc){
                    System.out.println("ERROR: Script Reading Issue.");
                    return "KO";
                }

            }
            else{
                System.out.println("ERROR : the center didn't understand you request.");
                return("KO");
            }
            
        }
    }