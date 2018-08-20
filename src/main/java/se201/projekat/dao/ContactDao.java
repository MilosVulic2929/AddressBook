package se201.projekat.dao;

import se201.projekat.models.Address;
import se201.projekat.models.Contact;
import se201.projekat.models.Person;
import se201.projekat.utils.DB;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;

/**
 * TODO da se implementira insert/update/convert
 */
public class ContactDao extends AbstractDao<Contact> {

    protected ContactDao() {
        super("contact");
    }

    @Override
    public int insert(Contact contact) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO contact(person_id, address_id, email, phone, creation_date)" +
                        "VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        // Proverimo da li je person sacuvan u bazi, ako nije cuvamo
        if (contact.getPerson().getId() == -1) {
            DaoFactory.create(PersonDao.class).insert(contact.getPerson());
            System.out.println("Save person first, new id " + contact.getPerson().getId());
        }
        stmt.setInt(1, contact.getPerson().getId());

        // Proverimo da li je address sacuvan u bazi, ako nije cuvamo
        if (contact.getAddress().getId() == -1) {
            DaoFactory.create(AddressDao.class).insert(contact.getAddress());
            System.out.println("Save address first, new id " + contact.getAddress().getId());
        }
        stmt.setInt(2, contact.getAddress().getId());
        stmt.setString(3, contact.getEmail());
        stmt.setString(4, contact.getPhone());
        stmt.setDate(5, Date.valueOf(LocalDate.now()));
        int affectedRow = stmt.executeUpdate();
        if (affectedRow > 0) {
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                int id = key.getInt(1);
                contact.setId(id);
                conn.close();
                return id;
            } else
                throw new SQLException("Couldn't insert contact, generating ID failed");
        } else
            throw new SQLException("Couldn't insert contact into table");
    }


    @Override
    public void update(Contact contact) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE contact SET email=?, phone=?, street=?, number=? WHERE `contact_id` = ?;");
        stmt.setString(1, contact.getEmail());
        stmt.setString(2, contact.getPhone());
        stmt.setInt(3, contact.getId());
        int rowCount = stmt.executeUpdate();

        // Updatujemo ovo samo ako je sacuvano vec u bazu
        if(contact.getPerson().getId() > 0)
            DaoFactory.create(PersonDao.class).update(contact.getPerson());
        if(contact.getAddress().getId() > 0)
            DaoFactory.create(AddressDao.class).update(contact.getAddress());

        System.out.println("Updated rows: " + rowCount);
        conn.close();
        if (rowCount != 1)
            throw new SQLException("Failed to update contact with id " + contact.getId());
    }

    @Override
    protected Contact convertFromRow(ResultSet row) throws SQLException {
        Address contactAddress = DaoFactory.create(AddressDao.class).getById(row.getInt("address_id"));
        Person contactPerson = DaoFactory.create(PersonDao.class).getById(row.getInt("person_id"));
        LocalDate creationDate = row.getDate("creation_date").toLocalDate();
        return new Contact(
                row.getInt("person_id"),
                contactPerson,
                contactAddress,
                row.getString("email"),
                row.getString("phone"),
                creationDate
        );
    }
}
