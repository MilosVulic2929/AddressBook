package se201.projekat.dao;

import se201.projekat.models.Address;
import se201.projekat.utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Apstraktna klasa gde je sve sto se ponavlja izvuceno
 * @param <T>
 */
public abstract class AbstractDao<T> implements IDao<T> {

    private final String tableName;

    AbstractDao(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public T getById(int id) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM `"+tableName+"` WHERE `"+tableName+"`."+tableName+"_id=?;", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            return convertFromRow(result);
        } else
            throw new SQLException("Entity with id " + id + " doesn't exist");
    }

    @Override
    public final List<T> getAll() throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM `" + tableName + "`;");
        ResultSet result = stmt.executeQuery();
        List<T> tableData = new ArrayList<>();
        while (result.next()) {
            tableData.add(convertFromRow(result));
        }
        return tableData;
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM `"+tableName+"` WHERE `"+tableName+"`."+tableName+"_id=?;");
        stmt.setInt(1, id);
        int rowCount = stmt.executeUpdate();
        System.out.println("Deleted rows: " + rowCount);
        if (rowCount != 1) {
            throw new SQLException("Failed to delete address with id " + id);
        }
        conn.close();
    }

    @Override
    public final void deleteAll() {
        try (Connection conn = DB.getInstance().connect()) {
            Statement stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            int result = stmt.executeUpdate("truncate TABLE `" + tableName + "`;");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    protected abstract T convertFromRow(ResultSet row) throws SQLException;
}
