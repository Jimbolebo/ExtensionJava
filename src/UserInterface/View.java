package UserInterface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import sat.Component;
import sat.ControlCenter;
import sat.Satelitte;

import java.awt.GridLayout;
import java.util.ArrayList;

/**
 * This class introduces the graphical view of the interface for one of the satellite
 */
public class View {

    private ControlCenter cc;
    private Satelitte sat;
    private ScrollPanel scrollpanel;

    private JPanel pane;
    private JFrame mainWindow;

    /**
     * Create the visual view of the interface for one satellite
     * @param sat The satellite connected to this view
     * @param cc The controlCenter which handle the requests
     * @param scrollpanel The common scrollpanel used to show the historic
     */
    public View(Satelitte sat,ControlCenter cc, ScrollPanel scrollpanel) {
        this.cc = cc;
        this.sat = sat;
        this.scrollpanel = scrollpanel;

        ArrayList<Component> listOfComp = sat.getCompoList();

        this.pane = new JPanel(new GridLayout(listOfComp.size()+1,1,0,5));

        //Each iteration handle the creation of a row for one subsystem
        for(int i = 0;i<listOfComp.size();i++){
            JButton buttonON = new JButton("ON");
            JButton buttonOFF = new JButton("OFF");
            JButton buttonDATA = new JButton("DATA");
            JLabel label = new JLabel(listOfComp.get(i).getName());

            buttonON.addActionListener(new ONButton(this,listOfComp.get(i).getName(),label));
            buttonOFF.addActionListener(new OFFButton(this,listOfComp.get(i).getName(),label));
            buttonDATA.addActionListener(new DATAButton(this,listOfComp.get(i).getName(),label));

            JPanel panel_buttons = new JPanel(new GridLayout(1,3));
            JPanel panel_row = new JPanel(new GridLayout(1,2));

            panel_buttons.add(buttonON);
            panel_buttons.add(buttonOFF);
            panel_buttons.add(buttonDATA);

            panel_row.add(label);
            panel_row.add(panel_buttons);
            
            pane.add(panel_row);

        } 

        JTextArea textarea = new JTextArea();
        JLabel scriptlabel = new JLabel("SCRIPT");
        JButton buttonSCRIPT = new JButton("EXECUTE SCRIPT");
        buttonSCRIPT.addActionListener(new SCRIPTButton(this,textarea,scriptlabel));

        JPanel scriptpanel = new JPanel(new GridLayout(1,3));
        scriptpanel.add(scriptlabel);
        scriptpanel.add(textarea);
        scriptpanel.add(buttonSCRIPT);

        pane.add(scriptpanel);

        // set up a main window; do not forget the close operation
        mainWindow = new JFrame(sat.getName());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closing the window will end the program
        mainWindow.setContentPane(pane);
    }


    /**
     * Getter for the pane attribut
     * @return the pane attribut
     */
    public JPanel getPane(){
        return pane;
    }

    /**
     * Getter for the ControlCenter attribut
     * @return The control center attribut
     */
    public ControlCenter getControlCenter(){
        return cc;
    }

    /**
     * Getter for the scrollpanel attribut
     * @return The scrollpanel attribut
     */
    public ScrollPanel getScrollPanel(){
        return scrollpanel;
    }

    /**
     * Getter for the sat attribut
     * @return The sat attribut
     */
    public Satelitte getSat(){
        return sat;
    }
}