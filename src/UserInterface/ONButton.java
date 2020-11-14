package UserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.text.BadLocationException;

/**
 * This class introduces the button which will be used to execute ON typed requests
 */
class ONButton implements ActionListener {

    private View view;
    private String equipement;
    private JLabel label;

    /**
     * Constructor for a ONButton object
     * @param view The view which contains the button
     * @param equipement The equipement linked to the button
     * @param label The label of the equipement linked to the button
     */
    public ONButton(View view,String equipement, JLabel label){
        this.view = view;
        this.equipement = equipement;
        this.label = label;
    }

    /**
     * This method handle a press of the button.
     * It executes the request using the ControlCenter, which changes the state of the equipement. The historic of requests
     * is then updated alongside the color of the equipement depending of the result of the request.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String request = view.getSat().getName()+":"+equipement+":ON";
        boolean result = view.getControlCenter().Request(request);
        try {
            view.getScrollPanel().write(request, result);

            if(result) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }

        } catch (BadLocationException e1) {
            System.out.println("Error System");
            System.exit(1);
        }
    }
}