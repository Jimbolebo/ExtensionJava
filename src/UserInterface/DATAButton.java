package UserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * This class introduces the button which will be used to execute DATA typed requests
 */
class DATAButton implements ActionListener {

    private View view;
    private String equipement;
    private JLabel label;

    /**
     * Constructor for a DATAButton object
     * @param view The view which contains the button
     * @param equipement The equipement linked to the button
     * @param label The label of the equipement linked to the button
     */
    public DATAButton(View view,String equipement,JLabel label){
        this.view = view;
        this.equipement = equipement;
        this.label = label;
    }

    /**
     * This method handle a press of the button.
     * It executes the request using the ControlCenter, which generates and saves the date. The historic of requests
     * is then updated alongside the color of the equipement, depending of the result of the request.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String request = view.getSat().getName()+":"+equipement+":DATA";
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