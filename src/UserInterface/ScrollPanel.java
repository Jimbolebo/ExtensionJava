package UserInterface;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;

/**
 * This class introduces the ScrollPanel class, which will be used to show the historic of the requests
 */
public class ScrollPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private StyledDocument doc;
    private Style red;
    private Style green;

    /**
     * Constructor for the ScrollPanel class.
     * Initialize all the needed element to make easier the use of the class (styles).
     * A JTextPane object is used alongside a StyledDocument object to show the requested requests
     */
    public ScrollPanel() {
        JTextPane textPane = new JTextPane();
        this.doc = textPane.getStyledDocument();

        this.red = doc.addStyle("red", null);
        StyleConstants.setForeground(red, Color.RED);

        this.green = doc.addStyle("green", null);
        StyleConstants.setForeground(green, Color.GREEN);

        JScrollPane panel = new JScrollPane(textPane);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.getVerticalScrollBar().setUnitIncrement(15);
        panel.setPreferredSize(new Dimension(400, 120));
        add(panel);
    }

    /**
     * Writes the given text in the StyledDocument. The boolean result indicates if the request was a success
     * or a failure, in order to choose the color.
     * @param txt The text to print
     * @param result The result of the request: true for success and false for failure
     * @throws BadLocationException
     */
    public void write(String txt, String result) throws BadLocationException {
        if(result.equals("OK")){ 
            doc.insertString(doc.getLength(), txt +"\n",green);
        } 
        else {
            doc.insertString(doc.getLength(), txt+"\n",red);
        }
    }
    
}