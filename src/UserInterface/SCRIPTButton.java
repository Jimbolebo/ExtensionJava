package UserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * This class introduces the button which will be used to execute DATA typed requests
 */
class SCRIPTButton implements ActionListener {

    private View view;
    private JTextArea textarea;
    private JLabel label;

    public SCRIPTButton(View view, JTextArea textarea,JLabel label){
        this.view = view;
        this.textarea = textarea;
        this.label = label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String request = view.getSat().getName()+":"+textarea.getText();
        String result = view.getControlCenter().sendRequests(request);
        try {
            view.getScrollPanel().write(request, result);
            if(result.equals("OK")) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
        } catch (BadLocationException e1) {
            System.out.println("Error System");
            System.exit(0);
        }
    }
}