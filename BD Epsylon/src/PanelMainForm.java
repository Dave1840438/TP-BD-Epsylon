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
        JTable tableTest = new JTable();
        String name, supplier, id;
        DefaultTableModel dtm = new DefaultTableModel();
        ResultSet res;
        try{
            res = (MainForm.conn.prepareStatement("SELECT * FROM ADHERENTS")).executeQuery();
            ResultSetMetaData meta = res.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            while (res.next())
            {
                Object [] rowData = new Object[numberOfColumns];
                for (int i = 0; i < rowData.length; ++i)
                {
                    rowData[i] = res.getObject(i+1);
                }
                dtm.addRow(rowData);
            }
            tableTest.setModel(dtm);
            //////////////////////////
            pan.add(tableTest);
            dtm.fireTableDataChanged();
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

        getnPrintAllData(pan0);
    }


}