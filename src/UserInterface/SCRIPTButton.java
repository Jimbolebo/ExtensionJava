package UserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * This class introduces the button which will be used to execute DATA typed
 * requests
 */
class SCRIPTButton implements ActionListener {

    private View view;
    private JTextArea textarea;

    public SCRIPTButton(View view, JTextArea textarea) {
        this.view = view;
        this.textarea = textarea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String request = view.getSat().getName()+":"+textarea.getText();
        view.getControlCenter().sendRequests(request);
        ArrayList<String> historic = view.getControlCenter().gScriptModule().getHistoric();
        try {
            for(int i = 0; i<historic.size();i++){
                String[] result = historic.get(i).split(",");
                view.getScrollPanel().write(result[1], result[0]);

                String[] request_i = result[1].split(":");
                int j = view.getSat().getCompoIndex(request_i[1]);

                if(result[0].equals("OK")) {
                    view.getListOfLabels().get(j).setForeground(Color.GREEN);
                } else {
                    view.getListOfLabels().get(j).setForeground(Color.RED);
                } 
            }
        } catch (BadLocationException e1) {
            System.out.println("Error System");
            System.exit(0);
        }
    }
}