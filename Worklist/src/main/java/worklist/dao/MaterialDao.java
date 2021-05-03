package worklist.dao;

import worklist.entity.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDao implements Dao<Material, Integer> {

    private Connection connection;
    private SQLUtils sqlUtils;

    public MaterialDao(Connection connection) {
        this.connection = connection;
        this.sqlUtils = new SQLUtils();
    }

    @Override
    public Material create(Material material) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Material (name, details) " +
                "VALUES (?, ?)");
        stmt.setString(1, material.getName());
        stmt.setString(2, material.getDetails());
        stmt.executeUpdate();

        return read(sqlUtils.getGeneratedId(stmt));
    }

    @Override
    public Material read(Integer id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Material WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Material m = parseMaterialFromResult(rs);

        stmt.close();
        rs.close();

        return m;
    }

    public Material readByName(String name) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Material WHERE name = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Material m = parseMaterialFromResult(rs);

        stmt.close();
        rs.close();

        return m;
    }

    @Override
    public Material update(Material object, Integer id) throws SQLException {
        // Not implemented
        return null;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        // Not implemented
    }

    @Override
    public List<Material> list() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Material;");
        ResultSet resultSet = statement.executeQuery();
        List<Material> materials = new ArrayList<>();

        while (resultSet.next()) {
            materials.add(parseMaterialFromResult(resultSet));
        }
        return materials;
    }

    public Material parseMaterialFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String details = resultSet.getString("details");
        Material m = new Material(id, name, details);
        return m;
    }

}

