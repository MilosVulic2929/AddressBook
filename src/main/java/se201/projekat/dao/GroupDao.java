package se201.projekat.dao;

import se201.projekat.models.Group;
import se201.projekat.utils.DB;

import java.sql.*;
import java.util.List;

/**
 * TODO da se implementira insert/update
 */
public class GroupDao extends AbstractDao<Group> {

    protected GroupDao() {
        super("group");
    }

    @Override
    public int insert(Group group) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO `group`(group_name)" +
                        "VALUES (?);", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, group.getName());
        int affectedRow = stmt.executeUpdate();
        if (affectedRow > 0) {
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                int id = key.getInt(1);
                group.setId(id);
                return id;
            } else
                throw new SQLException("Couldn't insert address, generating ID failed");
        } else
            throw new SQLException("Couldn't insert address into table");
    }

    @Override
    public void update(Group group) throws SQLException {
        Connection conn = DB.getInstance().connect();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE `group` SET group_name=? WHERE `group_id` = ?;");
        stmt.setString(1, group.getName());
        stmt.setInt(2, group.getId());
        int rowCount = stmt.executeUpdate();
        System.out.println("Updated rows: " + rowCount);
        conn.close();
        if (rowCount != 1)
            throw new SQLException("Failed to address address with id " + group.getId());
    }


    @Override
    protected Group convertFromRow(ResultSet row) throws SQLException {
        return new Group(
                row.getInt("group_id"),
                row.getString("group_name"));
    }

}
