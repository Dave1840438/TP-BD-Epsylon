import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

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
    private JButton BTN_Popularity;
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

        BTNPrets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("SaisirUnPret");
                frame.setContentPane(new SaisirUnPret(conn).lePanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        BTNRechercheLivres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("RechercheLivre");
                frame.setContentPane(new RechercheLivre(conn).lePanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        BTNEmprunts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = "SELECT L.TITRE, G.DESCRIPTION, E.DATEDEBUT, E.DATERETOUR, AD.NOM, AD.PRENOM FROM EMPRUNTS E INNER JOIN LIVRES L ON L.NUMERO = E.NUMEROEXEMPLAIRE INNER JOIN GENRES G ON G.NUMERO = L.GENRE INNER JOIN ADHERENTS AD ON AD.NUMERO = E.NUMEROADHERENT WHERE SYSDATE < E.DATERETOUR";
                ResultSet res;
                try{
                    res = (conn.prepareStatement(request, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)).executeQuery();
                    ResultSetMetaData meta = res.getMetaData();

                    Vector<String> columnNames = new Vector<String>();

                    for (int i = 0; i < meta.getColumnCount(); i++)
                        columnNames.add(meta.getColumnName(i + 1));

                    int numberOfColumns = meta.getColumnCount();
                    int numberOfRows = 0;
                    while(res.next()) numberOfRows++;
                    res.beforeFirst();
                    Vector data = new Vector();
                    for (int j = 0; j < numberOfRows; j++)
                    {
                        res.next();
                        Vector range = new Vector();
                        for (int i = 0; i < numberOfColumns; ++i)
                        {
                            range.add(res.getObject(i + 1));
                        }
                        data.add(range);
                    }
                    //////////////////////////
                    JFrame tableFrame = new JFrame("Allo test");

                    JTable laTable = new JTable(data, columnNames);
                    JScrollPane scrollPane = new JScrollPane(laTable);
                    tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    tableFrame.getContentPane().add(scrollPane);
                    tableFrame.setSize(1200, 500);
                    tableFrame.setVisible(true);
                }
                catch(Exception ex){
                    System.err.println(ex);
                    ex.printStackTrace();
                }
            }
        });

        BTN_Popularity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = "\n" +
                        "SELECT COUNT(L.TITRE) AS NOMBREEMPRUNTS, L.TITRE, L.AUTEUR, G.DESCRIPTION, M.NOM, L.DATEPARUTION  FROM EMPRUNTS EM " +
                        "INNER JOIN EXEMPLAIRES EX ON EX.NUMERO = EM.NUMEROEXEMPLAIRE " +
                        "INNER JOIN LIVRES L ON L.NUMERO = EX.LIVRE " +
                        "INNER JOIN GENRES G ON G.NUMERO = L.GENRE " +
                        "INNER JOIN MAISONSEDITION M ON M.NUMERO = L.MAISONEDITION " +
                        "GROUP BY L.TITRE, L.AUTEUR, G.DESCRIPTION, M.NOM, L.DATEPARUTION ORDER BY NOMBREEMPRUNTS DESC";
                ResultSet res;
                try{
                    res = (conn.prepareStatement(request, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)).executeQuery();
                    ResultSetMetaData meta = res.getMetaData();

                    Vector<String> columnNames = new Vector<String>();

                    for (int i = 0; i < meta.getColumnCount(); i++)
                        columnNames.add(meta.getColumnName(i + 1));

                    int numberOfColumns = meta.getColumnCount();
                    int numberOfRows = 0;
                    while(res.next()) numberOfRows++;
                    res.beforeFirst();
                    Vector data = new Vector();
                    for (int j = 0; j < numberOfRows; j++)
                    {
                        res.next();
                        Vector range = new Vector();
                        for (int i = 0; i < numberOfColumns; ++i)
                        {
                            range.add(res.getObject(i + 1));
                        }
                        data.add(range);
                    }
                    //////////////////////////
                    JFrame tableFrame = new JFrame("Allo test");

                    JTable laTable = new JTable(data, columnNames);
                    JScrollPane scrollPane = new JScrollPane(laTable);
                    tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    tableFrame.getContentPane().add(scrollPane);
                    tableFrame.setSize(1200, 500);
                    tableFrame.setVisible(true);
                }
                catch(Exception ex){
                    System.err.println(ex);
                    ex.printStackTrace();
                }
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
