import java.io.IOException;

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

/**
 * The main class of the application, which uses an interactive graphical interface.
 */
public class AppMain {

    public static void main(String[] args) throws IOException {

        ControlCenter cc = new ControlCenter();
        ListofSat satlist = cc.getsatList();
        
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
