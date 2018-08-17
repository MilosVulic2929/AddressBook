package se201.projekat.utils;

import java.sql.*;

public class DB {

    /*
        Uradjena osvnovna konekcija, necemo da koristimo nepredne metode
        Singleton pattern
     */

    private static DB INSTANCE;

    public static DB getInstance(){
        if(INSTANCE == null)
            INSTANCE = new DB();
        return INSTANCE;
    }
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/addressbook?createDatabaseIfNotExist=true";
    private static final String user= "root";
    private static final String password = "";


    private DB() {
        try {
            Class.forName(driver);
            createTable(GROUP_TABLE_SQL);
            createTable(PERSON_TABLE_SQL);
            createTable(ADDRESS_TABLE_SQL);
            // CONTACT mora zadnji da se kreira jer ima foreign key na ostale tabele
            createTable(CONTACT_TABLE_SQL);
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver not found:" + ex.getMessage());
        }
    }


    public Connection connect() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    private void createTable(String sql){
        try(Connection conn = connect()){
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    private static final String GROUP_TABLE_SQL = "create table IF NOT EXISTS `GROUP`\n" +
            "(\n" +
            "   GROUP_ID             int not null auto_increment,\n" +
            "   GROUP_NAME           varchar(255) not null,\n" +
            "   primary key (GROUP_ID)\n" +
            ");";

    private static final String PERSON_TABLE_SQL = "create table IF NOT EXISTS PERSON\n" +
            "(\n" +
            "   PERSON_ID            int not null auto_increment,\n" +
            "   FIRSTNAME            varchar(255) not null,\n" +
            "   LASTNAME             varchar(255) not null,\n" +
            "   GENDER               ENUM('male', 'female', 'other') not null,\n" +
            "   primary key (PERSON_ID)\n" +
            ");";

    private static final String ADDRESS_TABLE_SQL = "create table IF NOT EXISTS ADDRESS\n" +
            "(\n" +
            "   ADDRESS_ID           int not null auto_increment,\n" +
            "   CITY                 varchar(255) not null,\n" +
            "   COUNTRY              varchar(255) not null,\n" +
            "   STREET               varchar(255) not null,\n" +
            "   NUMBER               varchar(255) not null,\n" +
            "   primary key (ADDRESS_ID)\n" +
            ");";

    private static final String CONTACT_TABLE_SQL = "create table IF NOT EXISTS CONTACT\n" +
            "(\n" +
            "   CONTACT_ID           int not null auto_increment,\n" +
            "   GROUP_ID             int,\n" +
            "   ADDRESS_ID           int not null,\n" +
            "   PERSON_ID            int not null,\n" +
            "   PHONE                varchar(255) not null,\n" +
            "   EMAIL                varchar(255) not null,\n" +
            "   CREATION_DATE        date not null,\n" +
            "   primary key (CONTACT_ID),\n" +
            "   FOREIGN KEY (GROUP_ID) REFERENCES `GROUP`(GROUP_ID)\n" +
            "   on delete cascade on update cascade,\n" +
            "      FOREIGN KEY (ADDRESS_ID) REFERENCES `ADDRESS`(ADDRESS_ID)\n" +
            "   on delete cascade on update cascade,\n" +
            "      FOREIGN KEY (PERSON_ID) REFERENCES `PERSON`(PERSON_ID)\n" +
            "   on delete cascade on update cascade\n" +
            ");";


}
