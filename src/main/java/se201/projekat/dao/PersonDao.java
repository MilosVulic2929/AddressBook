package se201.projekat.dao;

import se201.projekat.models.Gender;
import se201.projekat.models.Person;
import se201.projekat.utils.DB;

import java.sql.*;


public class PersonDao extends AbstractDao<Person>{


    protected PersonDao() {
        super("person");
    }

    @Override
    public int insert(Person person) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO `person`(firstname, lastname, gender)" +
                        "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, person.getFirstName());
        stmt.setString(2, person.getLastName());
        stmt.setString(3, person.getGender().toString());
        int affectedRow = stmt.executeUpdate();
        if (affectedRow > 0) {
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                int id = key.getInt(1);
                person.setId(id);
                return id;
            } else
                throw new SQLException("Couldn't insert person, generating ID failed");
        } else
            throw new SQLException("Couldn't insert person into table");
    }

    @Override
    public void update(Person person) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE person SET firstname=?, lastname=?, gender=? WHERE `person_id` = ?;");
        stmt.setString(1, person.getFirstName());
        stmt.setString(2, person.getLastName());
        stmt.setString(3, person.getGender().toString());
        stmt.setInt(4, person.getId());
        int rowCount = stmt.executeUpdate();
        conn.close();
        if (rowCount != 1)
            throw new SQLException("Failed to update person with id " + person.getId());
    }

    @Override
    protected Person convertFromRow(ResultSet row) throws SQLException {
        // Mora toUppserCase inace ne cita jer je enum case sensitive
        Gender gender = Gender.valueOf(row.getString("gender").toUpperCase());
        return new Person(
                row.getInt("person_id"),
                row.getString("firstname"),
                row.getString("lastname"),
                gender);
    }

}
