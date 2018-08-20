package se201.projekat.dao;

import se201.projekat.models.Contact;
import se201.projekat.utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO da se implementira insert/update/convert
 */
public class ContactDao extends AbstractDao<Contact> {

    protected ContactDao() {
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

    // TODO INFO za (filipa)
    // query za uzimanje podataka za pie-chart (gender statistics)
    public int countContacts(String typeContact) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement st = conn.prepareCall("SELECT COUNT(GENDER) AS broj "
                + "FROM CONTACT "
                + "JOIN PERSON ON PERSON.PERSON_ID = CONTACT.PERSON_ID "
                + "WHERE GENDER = ?");
        st.setString(1, typeContact);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return rs.getInt("broj");
        }
        conn.close();
        return 0;
    }
}
