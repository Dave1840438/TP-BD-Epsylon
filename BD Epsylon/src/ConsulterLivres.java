import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Dave on 4/21/2015.
 */
public class ConsulterLivres {
    private JButton BTN_Previous;
    private JButton BTN_Next;
    private JComboBox CMBBOX_Genres;
    private JLabel LBL_Titre;
    private JLabel LBL_Auteur;
    private JLabel LBL_Genre;
    private JLabel LBL_DateParution;
    private JLabel LBL_MaisonEdition;
    public JPanel lePanel;
    private JFrame frame;
    private Connection conn;
    private ResultSet rst;

    public ConsulterLivres(Connection conn) {
        this.conn = conn;

        String fetchInfo = "SELECT * FROM GENRES";

        Statement fetchAll = null;

        try {
            fetchAll = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rstGenres = fetchAll.executeQuery(fetchInfo);

            while(rstGenres.next())
            {
                String item = rstGenres.getString(2);
                CMBBOX_Genres.addItem(item);
            }
            CMBBOX_Genres.setSelectedIndex(1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        addListeners();
        RefreshResultSet((String)CMBBOX_Genres.getSelectedItem());
        updateLabels();
        CMBBOX_Genres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RefreshResultSet((String)CMBBOX_Genres.getSelectedItem());
                updateLabels();
            }
        });
    }

    public void RefreshResultSet(String categorie) {
        String fetchInfo = "SELECT L.NUMERO, L.TITRE, L.AUTEUR, G.DESCRIPTION, L.DATEPARUTION, M.NOM FROM LIVRES L INNER JOIN GENRES G ON G.NUMERO = L.GENRE INNER JOIN MAISONSEDITION M ON M.NUMERO = L.MAISONEDITION";

        if (categorie != null)
            fetchInfo +=  " WHERE G.DESCRIPTION = '" + categorie + "'";

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
    }

    public void updateLabels() {
        try {
            LBL_Titre.setText(rst.getString(2));
            LBL_Auteur.setText(rst.getString(3));
            LBL_Genre.setText(rst.getString(4));
            LBL_DateParution.setText(rst.getString(5));
            LBL_MaisonEdition.setText(rst.getString(6));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
