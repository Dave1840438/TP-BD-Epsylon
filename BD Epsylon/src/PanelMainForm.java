/**
 * Created by Dave on 4/19/2015.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class PanelMainForm extends JPanel {

    final int pseudoCharacterLimit = 8;
    final int textCharacterLimit = 80;
    Socket socket = null;
    PrintWriter writer = null;
    Thread fetch = null;

    public void getnPrintAllData(JPanel pan){
        JTable tableTest;
        ResultSet res;
        try{
            res = (MainForm.conn.prepareStatement("SELECT * FROM ADHERENTS", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)).executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            String[] columnNames = new String[meta.getColumnCount()];

            for (int i = 0; i < meta.getColumnCount(); i++)
                columnNames[i] = meta.getColumnName(i+1);

            int numberOfColumns = meta.getColumnCount();
            int numberOfRows = 0;
            while(res.next()) numberOfRows++;
            res.beforeFirst();
            Object [][] data2 = new Object[numberOfRows][numberOfColumns];
            for (int j = 0; j < numberOfRows; j++)
            {
                res.next();
                for (int i = 0; i < numberOfColumns; ++i)
                {
                    data2[j][i] = res.getObject(i+1);
                }
            }
            //////////////////////////
            tableTest = new JTable(data2, columnNames);
            pan.add(tableTest);
        }
        catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }

    }

    public PanelMainForm() {
        setLayout(new GridLayout(0, 1)); // une seule colonne

        // rangée 0
        JLabel labelAdresse = new JLabel("Adresse IP");
        JPanel pan0 = new JPanel();
        add(pan0);
        pan0.add(labelAdresse);

        String[] columnNames = {"First Name", "Last Name"};
        Object[][] data = {{"Kathy", "Smith"},{"John", "Doe"}};
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        pan0.add(table);

        getnPrintAllData(pan0);
    }
}