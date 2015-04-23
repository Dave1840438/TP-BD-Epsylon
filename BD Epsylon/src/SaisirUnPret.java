import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Dave on 2015-04-22.
 */
public class SaisirUnPret {
    public JPanel lePanel;
    private JComboBox CMBBOX_Users;
    private JComboBox CMBBOX_Exemplaires;
    private JButton BTNEmprunt;
    private Connection conn;

    public SaisirUnPret(Connection conn)
    {
        this.conn = conn;

        RefreshInfo();
        AddListeners();
    }

    public void AddListeners()
    {
        BTNEmprunt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parameteredInsertion = "INSERT INTO EMPRUNTS VALUES(?, ?, SYSDATE, SYSDATE + 21)";
                try {
                    PreparedStatement insertStatement = conn.prepareStatement(parameteredInsertion);
                    insertStatement.setInt(1, Integer.parseInt(((String) CMBBOX_Exemplaires.getSelectedItem()).replaceAll("[^0-9]+", " ").trim()));

                    insertStatement.setInt(2, Integer.parseInt(((String) CMBBOX_Users.getSelectedItem()).replaceAll("[^0-9]+", " ").trim()));
                    System.out.println(insertStatement.executeUpdate());
                    RefreshInfo();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void RefreshInfo()
    {
        CMBBOX_Exemplaires.removeAllItems();
        CMBBOX_Users.removeAllItems();
        String fetchUsers = "SELECT NUMERO, NOM, PRENOM FROM ADHERENTS";
        String fetchExemplaires = "SELECT E.NUMERO, L.TITRE FROM EXEMPLAIRES E INNER JOIN LIVRES L ON L.NUMERO = E.LIVRE WHERE E.NUMERO NOT IN (SELECT NUMEROEXEMPLAIRE FROM EMPRUNTS WHERE DATERETOUR >= SYSDATE) ";

        try {
            Statement stfetchUsers = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Statement stfetchExemplaires = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rstUsers = stfetchUsers.executeQuery(fetchUsers);
            ResultSet rstExemplaires = stfetchExemplaires.executeQuery(fetchExemplaires);

            while(rstUsers.next())
            {
                String item = rstUsers.getString(1) + ": " + rstUsers.getString(2) + ", " + rstUsers.getString(3);
                CMBBOX_Users.addItem(item);
            }
            CMBBOX_Users.setSelectedIndex(1);

            while(rstExemplaires.next())
            {
                String item = rstExemplaires.getString(1) + ": " + rstExemplaires.getString(2);
                CMBBOX_Exemplaires.addItem(item);
            }
            CMBBOX_Exemplaires.setSelectedIndex(1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
