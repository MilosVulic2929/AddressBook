package se201.projekat.dao;

import se201.projekat.models.Address;
import se201.projekat.models.Contact;
import se201.projekat.models.Person;
import se201.projekat.utils.DB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ContactDao extends AbstractDao<Contact> {

    private List<Integer> listaSumaAddressa = new ArrayList<>();
    private List<Integer> listaSumaClanova = new ArrayList<>();

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
        if (contact.getPerson().getId() > 0)
            DaoFactory.create(PersonDao.class).update(contact.getPerson());
        if (contact.getAddress().getId() > 0)
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

    // query za uzimanje podataka za line-chart (dodavanje kontakta na mesecnom nivou)
    public int countContactsPerMounth(String godina, String mesec) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement st = conn.prepareCall("SELECT COUNT(CREATION_DATE) AS broj "
                + "FROM CONTACT "
                + "WHERE YEAR(CREATION_DATE) = ? AND MONTH(CREATION_DATE) = ?");
        st.setString(1, godina);
        st.setString(2, mesec);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return rs.getInt("broj");
        }
        conn.close();
        return 0;
    }

    // query za uzimanje podataka za bar-chart (zemlje)
    public List<String> countCountryNames() throws SQLException {
        List<String> listaImenaZemalja = new ArrayList<>();
        List<Integer> listaVrednosti = new ArrayList<>();
        Connection conn = DB.getInstance().connect();
        PreparedStatement st = conn.prepareCall("SELECT COUNTRY AS country, count(*) AS broj "
                + "FROM CONTACT "
                + "JOIN ADDRESS ON CONTACT.ADDRESS_ID = ADDRESS.ADDRESS_ID "
                + "GROUP BY COUNTRY "
                + "ORDER BY count(*) DESC "
                + "LIMIT 4");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            listaImenaZemalja.add(rs.getString("country"));
            listaVrednosti.add(rs.getInt("broj"));
        }
        conn.close();
        setListuSuma(listaVrednosti);
        return listaImenaZemalja;
    }

    public List<String> countGroupNames() throws SQLException {
        List<String> listaImenaGrupa = new ArrayList<>();
        List<Integer> listaVrednosti = new ArrayList<>();
        Connection conn = DB.getInstance().connect();
        PreparedStatement st = conn.prepareCall("SELECT GROUP_NAME AS ime, count(*) AS broj "
                + "FROM CONTACT "
                + "JOIN `GROUP` ON CONTACT.GROUP_ID = `GROUP`.GROUP_ID "
                + "GROUP BY GROUP_NAME "
                + "ORDER BY count(*) DESC "
                + "LIMIT 4");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            listaImenaGrupa.add(rs.getString("ime"));
            listaVrednosti.add(rs.getInt("broj"));
        }
        conn.close();
        setListuSumaClanovaGrupa(listaVrednosti);
        return listaImenaGrupa;
    }

    // bukvalno dupla metoda ali ovde broj null vrednosti tipa da bi pokazao kolko ljudi nisu grupama
    public int countNoGroupsMembers() throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement st = conn.prepareCall("SELECT count(*) AS broj "
                + "FROM CONTACT "
                + "WHERE GROUP_ID <=> NULL ");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return rs.getInt("broj");
        }
        conn.close();
        return 0;
    }


    public List<Integer> countCountryAddresses(){
        return this.listaSumaAddressa;
    }
    public List<Integer> countGroupMembers(){
        return this.listaSumaClanova;
    }

    public void setListuSuma(List<Integer> lista){
        this.listaSumaAddressa = lista;
    }
    public void setListuSumaClanovaGrupa(List<Integer> lista){
        this.listaSumaClanova = lista;
    }
}
