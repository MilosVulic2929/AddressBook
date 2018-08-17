package se201.projekat.dao;

import se201.projekat.models.Contact;
import se201.projekat.utils.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO da se implementira insert/update/convert
 */
public class ContactDao extends AbstractDao<Contact> {

    protected ContactDao(){
        super("contact");
    }

    @Override
    public int insert(Contact value) throws SQLException {
        return 0;
    }


    @Override
    public void update(Contact value) throws SQLException {

    }


    @Override
    protected Contact convertFromRow(ResultSet row) throws SQLException {
        return null;
    }
}
