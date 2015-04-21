/**
 * Created by 201335700 on 2015-04-21.
 */
/**
 * Created by Dave on 4/19/2015.
 */
import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainForm {

    static Connection conn;

    public static void InitializeConnection() {
        System.out.println("hello");

        String user = "sylvestr";
        String password = "ORACLE2";
        String connectionString = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";


        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setURL(connectionString);
            conn = dataSource.getConnection();

            System.out.println("ok");
        } catch (Exception ex) {
            System.out.println("not ok");
            System.out.println(ex);
        }
    }


    private static void creerEtAfficherIug() {
        JFrame frame = new JFrame("Client de clavardage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = frame.getContentPane();
        PanelMainForm panneau = new PanelMainForm();
        c.add(panneau);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InitializeConnection();
                creerEtAfficherIug();
            }
        });
    }
}


/*
package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import oracle.jdbc.pool.*;

public class Main {

    static Connection conn;

    public static void main(String[] args) {
	System.out.println("hello");

        String user = "sylvestr";
        String password = "ORACLE1";
        String connectionString = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";



        try
        {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setURL(connectionString);
            conn = dataSource.getConnection();

            Exercice3();


            conn.close();
            System.out.println("ok");
        }
        catch (Exception ex)
        {
            System.out.println("not ok");
            System.out.println(ex);
        }






    }

    static void Exercice1()
    {
        String updateDaveSalary = "UPDATE EMPLOYESBIDON SET SALAIRE = 50000 WHERE PRENOMEMP = 'David'";
        String updateOlivierSalary = "UPDATE EMPLOYESBIDON SET SALAIRE = 520000 WHERE PRENOMEMP = 'Olivier'";
        int nombreLignes = 0;

        try {
            Statement updateCommand = conn.createStatement();
            nombreLignes += updateCommand.executeUpdate(updateDaveSalary);
            nombreLignes += updateCommand.executeUpdate(updateOlivierSalary);
            System.out.println("Nombres de lignes modifiées: " + nombreLignes);
            updateCommand.close();
        }
        catch (Exception ex)
        {
            System.out.println("not ok");
            System.out.println(ex);
        }
    }


    static void Exercice2()
    {
        String fetchInfo = "SELECT NOMEMP, PRENOMEMP, SALAIRE FROM EMPLOYESBIDON";

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            Statement fetchAll = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rst =  fetchAll.executeQuery(fetchInfo);

            rst.absolute(1);
            System.out.println("Premier enregistrement: " + rst.getString("NOMEMP") + ", " + rst.getString("PRENOMEMP") + ", " + rst.getString("SALAIRE"));

            rst.absolute(-1);
            System.out.println("Dernier enregistrement: " + rst.getString("NOMEMP") + ", " + rst.getString("PRENOMEMP") + ", " + rst.getString("SALAIRE"));
            System.out.println("Coucou");
            rst.absolute(3);
            System.out.println("Troisième enregistrement: " + rst.getString("NOMEMP") + ", " + rst.getString("PRENOMEMP") + ", " + rst.getString("SALAIRE"));


            rst.absolute(1);
            rst.relative(3);
            System.out.println("Troisième enregistrement à partir du premier: " + rst.getString("NOMEMP") + ", " + rst.getString("PRENOMEMP") + ", " + rst.getString("SALAIRE"));

            //1 correspond au premier
            //-1 correspond au dernier
            //Le précédent du premier est situé avant le tabaleau, avant le premier, mais ce n'est rien

            System.out.println();
            System.out.println("Lecture inverse: ");

            rst.afterLast();
            while (rst.relative(-1))
                System.out.println(rst.getString("NOMEMP") + ", " + rst.getString("PRENOMEMP") + ", " + rst.getString("SALAIRE"));
        }
        catch (Exception ex)
        {
            System.out.println("not ok");
            System.out.println(ex);
            ex.printStackTrace();
        }
    }


    static void Exercice3()
    {
        final String lastNameInsert = "Dave";
        final String firstNameInsert = "Lee";
        final String jobInsert = "God";
        final int salaryInsert = 99999999;

        final int salaryUpdate = 60000;
        final String nomUpdate = "Paquette";

        final String emploi = "PROGRAMMEUER";


        String parameteredInsertion = "INSERT INTO EMPLOYESBIDON VALUES(?, ?, ?, ?)";
        String parameteredUpdate = "UPDATE EMPLOYESBIDON SET SALAIRE = ? WHERE NOMEMP = ?";
        String parameteredSelect = "SELECT NOMEMP, PRENOMEMP, EMPLOI FROM EMPLOYESBIDON WHERE EMPLOI = ?";
        String fetchAll = "SELECT * FROM EMPLOYESBIDON";


        try
        {
            PreparedStatement insertStatement = conn.prepareStatement(parameteredInsertion);
            insertStatement.setString(1, lastNameInsert);
            insertStatement.setString(2, firstNameInsert);
            insertStatement.setString(3, jobInsert);
            insertStatement.setInt(4, salaryInsert);
            System.out.println(insertStatement.executeUpdate());

            PreparedStatement updateStatement = conn.prepareStatement(parameteredUpdate);
            updateStatement.setInt(1, salaryUpdate);
            updateStatement.setString(2, nomUpdate);
            System.out.println(updateStatement.executeUpdate());

            PreparedStatement selectEmploi = conn.prepareStatement(parameteredSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            selectEmploi.setString(1, emploi);

            ResultSet rstEmploi = selectEmploi.executeQuery();
            System.out.println();
            System.out.println();

            rstEmploi.beforeFirst();
            System.out.println("Employes selon l'emploi du début à la fin: ");
            while (rstEmploi.relative(1))
                System.out.println(rstEmploi.getString("NOMEMP") + ", " + rstEmploi.getString("PRENOMEMP") + ", " + rstEmploi.getString("EMPLOI"));

            System.out.println();
            System.out.println();
            rstEmploi.afterLast();
            System.out.println("Employes selon l'emploi de la fin jusqu'au debut: ");
            while (rstEmploi.relative(-1))
                System.out.println(rstEmploi.getString("NOMEMP") + ", " + rstEmploi.getString("PRENOMEMP") + ", " + rstEmploi.getString("EMPLOI"));
            System.out.println();
            System.out.println();

            Statement getAll = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rst =  getAll.executeQuery(fetchAll);
            ResultSetMetaData rstmd = rst.getMetaData();


            System.out.println("Nombre de colonne dans le result set: " + rstmd.getColumnCount());

            System.out.println("Info des colonnes");
            for (int i = 1; i <= rstmd.getColumnCount(); i++)
            {
                System.out.println("Nom: " + rstmd.getColumnName(i) + "   Type: " + rstmd.getColumnTypeName(i));
            }


          //  ResultSet rst = insertStatement.executeQuery();
            //while (rst.next())
            //    System.out.println(rst.getString("NOMEMP") + ", " + rst.getString("PRENOMEMP") + ", " + rst.getString("EMPLOI") + ", " + rst.getString("SALAIRE"));
        }
        catch (Exception ex)
        {
            System.out.println("not ok");
            System.out.println(ex);
        }
    }
}

 */