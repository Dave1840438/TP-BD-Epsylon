import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dave on 4/21/2015.
 */
public class MainMenu {
    private JButton BTNModifierAdherent;
    private JButton BTNLivres;
    private JButton BTNPrets;
    private JButton BTNRechercheLivres;
    private JButton BTNEmprunts;
    private JPanel lePanel;
    private Connection conn;

    public MainMenu() {

        String user = "sylvestr";
        String password = "ORACLE2";
        String connectionString = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";

        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setURL(connectionString);
            conn = dataSource.getConnection();
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
            System.exit(-1);
        }

        BTNModifierAdherent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("MainMenu");
                frame.setContentPane(new GestionAdherents(conn).lePanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        BTNLivres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("MainMenu");
                frame.setContentPane(new ConsulterLivres(conn).lePanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().lePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
