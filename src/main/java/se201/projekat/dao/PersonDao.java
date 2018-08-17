package se201.projekat.dao;

import se201.projekat.models.Address;
import se201.projekat.models.Gender;
import se201.projekat.models.Person;
import se201.projekat.utils.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO da se implementira insert/update
 */
public class PersonDao extends AbstractDao<Person>{


    protected PersonDao() {
        super("person");
    }

    @Override
    public int insert(Person value) throws SQLException {
        return 0;
    }

    @Override
    public void update(Person value) throws SQLException {

    }

    @Override
    protected Person convertFromRow(ResultSet row) throws SQLException {
        Gender gender = Gender.valueOf(row.getString("gender"));
        System.out.println("Gender is " + gender);
        return new Person(
                row.getInt("person_id"),
                row.getString("firstname"),
                row.getString("lastname"),
                gender);
    }

}
