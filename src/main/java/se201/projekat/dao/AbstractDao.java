package se201.projekat.dao;

import se201.projekat.utils.DB;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Apstraktna Dao klasa koja implementira IDao, ima implementirane metode koje se ponavljaju za sve klase
 */
public abstract class AbstractDao<T extends Entity> implements IDao<T> {

    private final String tableName;

    AbstractDao(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Nalazi entitet na osnovu id-ija
     */
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

    /**
     *
     * Ucitava sve entitete iz baze
     */
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


    /**
     * Brise entitet sa prosledjenim id-ijem
     */
    @Override
    public void delete(int id) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM `"+tableName+"` WHERE `"+tableName+"`."+tableName+"_id=?;");
        stmt.setInt(1, id);
        int rowCount = stmt.executeUpdate();
        if (rowCount != 1) {
            throw new SQLException("Failed to delete address with id " + id);
        }
        conn.close();
    }

    /**
     * Brise prosledjeni entitet i setuje mu id na -1
     */
    @Override
    public void delete(T value) throws SQLException {
        if(value.getId() > 0){
            delete(value.getId());
            value.setId(-1);
        }
    }


    /**
     * Brise celu tabelu
     */
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

    /**
     * Metoda koju implementiraju druge klase, pretvara red u tabeli u entitet
     */
    protected abstract T convertFromRow(ResultSet row) throws SQLException;
}
