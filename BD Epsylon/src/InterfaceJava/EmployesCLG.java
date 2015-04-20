package InterfaceJava;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 201335700 on 2015-04-17.
 */
public class EmployesCLG {
    private JButton alloLesAmisButton;
    private JLabel maLabelTest;
    private JPanel Panel1;


    public static void main(String[] args) {
        // on crée une fenêtre dont le titre est "Bonjour"
        JFrame frame = new JFrame("Bonjour");
        // on ajoute le contenu du Panne1
        frame.setContentPane(new EmployesCLG().Panel1);
        //la fenêtre se ferme quand on clique sur la croix rouge
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //on attribue la taille minimale au frame
        frame.pack();
        // on rend le frame visible
        frame.setVisible(true);
    }


    public EmployesCLG() {
        alloLesAmisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maLabelTest.setText("Salut!!!");
            }
        });
    }
}
