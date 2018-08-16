package se201.projekat.utils;

import java.sql.*;

public class DB {

    /*
        Uradjena osvnovna konekcija, necemo da koristimo nepredne metode
        TODO treba se dodaju metode za kreiranje tabela, i DAO klase
     */

    private static DB INSTANCE;

    public static DB getInstance(){
        if(INSTANCE == null)
            INSTANCE = new DB();
        return INSTANCE;
    }
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/nastavnik_to_predmet?createDatabaseIfNotExist=true";
    private static final String user= "root";
    private static final String password = "";


    private DB() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver not found:" + ex.getMessage());
        }
    }


    public Connection connect() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }


}
