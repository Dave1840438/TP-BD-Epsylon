import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * Created by Dave on 4/21/2015.
 */
public class GestionAdherents {
    public JPanel lePanel;
    private JTextField TBX_Nom;
    private JTextField TBX_Prenom;
    private JTextField TBX_Adresse;
    private JTextField TBX_Tel;
    private JButton BTNAdd;
    private JButton BTNModify;
    private JButton BTNDelete;
    private JButton BTN_Previous;
    private JButton BTN_Next;
    private JFrame frame;
    private Connection conn;
    private ResultSet rst;

    public GestionAdherents(Connection conn) {
        this.conn = conn;

       RefreshResultSet();

        addListeners();
        updateLabels();
    }

    public void RefreshResultSet() {
        String fetchInfo = "SELECT * FROM ADHERENTS";
        Statement fetchAll = null;

        try {
            fetchAll = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = fetchAll.executeQuery(fetchInfo);
            rst.first();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addListeners() {
        BTN_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rst.next())
                        rst.first();
                    updateLabels();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        BTN_Previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rst.previous())
                            rst.last();
                    updateLabels();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        BTNAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parameteredInsertion = "INSERT INTO ADHERENTS (NOM, PRENOM, ADRESSE, NUMEROTEL) VALUES(?, ?, ?, ?)";
                try {
                    PreparedStatement insertStatement = conn.prepareStatement(parameteredInsertion);
                    insertStatement.setString(1, TBX_Nom.getText());
                    insertStatement.setString(2, TBX_Prenom.getText());
                    insertStatement.setString(3, TBX_Adresse.getText());
                    insertStatement.setString(4, TBX_Tel.getText());
                    System.out.println(insertStatement.executeUpdate());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                RefreshResultSet();
                updateLabels();
            }
        });

        BTNModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parameteredUpdate = "UPDATE ADHERENTS SET NOM = ?, PRENOM = ?, ADRESSE = ?, NUMEROTEL = ? WHERE NUMERO = ?";
                try {
                    PreparedStatement updateStatement = conn.prepareStatement(parameteredUpdate);
                    updateStatement.setString(1, TBX_Nom.getText());
                    updateStatement.setString(2, TBX_Prenom.getText());
                    updateStatement.setString(3, TBX_Adresse.getText());
                    updateStatement.setString(4, TBX_Tel.getText());
                    updateStatement.setInt(5, rst.getInt(1));
                    System.out.println(updateStatement.executeUpdate());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                RefreshResultSet();
                updateLabels();
            }
        });

        BTNDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parameteredUpdate = "DELETE FROM ADHERENTS WHERE NUMERO = ?";
                try {
                    PreparedStatement deleteStatement = conn.prepareStatement(parameteredUpdate);
                    deleteStatement.setInt(1, rst.getInt(1));
                    System.out.println(deleteStatement.executeUpdate());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                RefreshResultSet();
                updateLabels();
            }
        });
    }

    public void updateLabels() {
        try {
            TBX_Nom.setText(rst.getString(2));
            TBX_Prenom.setText(rst.getString(3));
            TBX_Adresse.setText(rst.getString(4));
            TBX_Tel.setText(rst.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
