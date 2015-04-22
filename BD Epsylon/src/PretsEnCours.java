import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * Created by Dave on 2015-04-22.
 */
public class PretsEnCours {
    public JPanel lePanel;
    private JTable TB_Emprunts;
    private JTable laTable;
    private Connection conn;

    public PretsEnCours(Connection conn){
        this.conn = conn;
        RefreshTable();
    }

    public void RefreshTable()
    {
        String request = "SELECT * FROM ADHERENTS";
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

            laTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(laTable);
            tableFrame.getContentPane().add(scrollPane);
            tableFrame.setSize(500, 500);
            tableFrame.setVisible(true);

            TB_Emprunts = new JTable(data, columnNames);
        }
        catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
