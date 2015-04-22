import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * Created by Dave on 2015-04-22.
 */
public class RechercheLivre {
    public JPanel lePanel;
    private JButton BTN_Titre;
    private JButton BTN_Auteur;
    private JTextField TBX_Recherche;
    private Connection conn;

    public RechercheLivre(Connection conn)
    {
        this.conn = conn;
    }

    public void addListeners()
    {
        BTN_Auteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        BTN_Titre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void CreateTableResults(String request) {
        ResultSet res;
        try {
            res = (conn.prepareStatement(request, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)).executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            Vector<String> columnNames = new Vector<String>();

            for (int i = 0; i < meta.getColumnCount(); i++)
                columnNames.add(meta.getColumnName(i + 1));

            int numberOfColumns = meta.getColumnCount();
            int numberOfRows = 0;
            while (res.next()) numberOfRows++;
            res.beforeFirst();
            Vector data = new Vector();
            for (int j = 0; j < numberOfRows; j++) {
                res.next();
                Vector range = new Vector();
                for (int i = 0; i < numberOfColumns; ++i) {
                    range.add(res.getObject(i + 1));
                }
                data.add(range);
            }
            //////////////////////////
            JFrame tableFrame = new JFrame("Allo test");

            JTable laTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(laTable);
            tableFrame.getContentPane().add(scrollPane);
            tableFrame.setSize(1000, 500);
            tableFrame.setVisible(true);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

}
