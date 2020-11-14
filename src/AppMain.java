import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;

import UserInterface.ScrollPanel;
import UserInterface.View;
import sat.ControlCenter;
import sat.ListofSat;
import sat.Satelitte;

/**
 * The main class of the application, which uses an interactive graphical interface.
 */
public class AppMain {

    public static void main(String[] args) throws IOException {
        ControlCenter cc = new ControlCenter();
        ListofSat satlist = cc.getsatList();
        
        
        /** Writes in a file the name of the satelittes of the control center. It will be used for the indexation of the data stored on the hardware. */
        PrintWriter out = new PrintWriter(new FileWriter("src/sat/DATA/satOfTheControlCenter.txt"));
        for(Satelitte sat : satlist.getList()){
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


        //The scrollpanel used for the historic
        ScrollPanel scrollpanel = new ScrollPanel();

        //The menu which contains the button for each satellite
        JTabbedPane tabs = new JTabbedPane();
        for(int i = 0;i<satlist.size();i++){
            View v = new View(satlist.get(i),cc,scrollpanel);
            tabs.addTab(satlist.get(i).getName(), v.getPane());
        }

        //The label for the historic
        JLabel labelTC = new JLabel("T/C History");
        labelTC.setHorizontalAlignment(SwingConstants.CENTER);
        labelTC.setFont(new Font(Font.SERIF,Font.BOLD,25));
        labelTC.setPreferredSize(new Dimension(400,40));;

        //Creation of the interface
        JFrame frame = new JFrame("Interface User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy=0;
        panel.add(tabs,gc);
        gc.gridy=1;
        panel.add(labelTC,gc);
        gc.gridy=2;
        panel.add(scrollpanel,gc);

        // show it in the main window
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
