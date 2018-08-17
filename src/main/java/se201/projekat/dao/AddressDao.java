package se201.projekat.dao;

import se201.projekat.models.Address;
import se201.projekat.utils.DB;

import java.sql.*;

public class AddressDao extends AbstractDao<Address> {


    protected AddressDao() {
        super("address");
    }

    /**
     * @param address
     * @return index dodeljen sacuvanoj adresi
     */
    public int insert(Address address) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO address(country, city, street, number)" +
                        "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, address.getCountry());
        stmt.setString(2, address.getCity());
        stmt.setString(3, address.getStreet());
        stmt.setString(4, address.getNumber());
        int affectedRow = stmt.executeUpdate();
        if (affectedRow > 0) {
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                int id = key.getInt(1);
                address.setId(id);
                conn.close();
                return id;
            } else
                throw new SQLException("Couldn't insert address, generating ID failed");
        } else
            throw new SQLException("Couldn't insert address into table");
    }


    public void update(Address address) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE address SET country=?, city=?, street=?, number=? WHERE `address_id` = ?;");
        stmt.setString(1, address.getCountry());
        stmt.setString(2, address.getCity());
        stmt.setString(3, address.getStreet());
        stmt.setString(4, address.getNumber());
        stmt.setInt(5, address.getId());
        int rowCount = stmt.executeUpdate();
        System.out.println("Updated rows: " + rowCount);
        conn.close();
        if (rowCount != 1)
            throw new SQLException("Failed to address address with id " + address.getId());
    }

    @Override
    protected Address convertFromRow(ResultSet row) throws SQLException {
        return new Address(
                row.getInt("address_id"),
                row.getString("country"),
                row.getString("city"),
                row.getString("street"),
                row.getString("number"));
    }

}
