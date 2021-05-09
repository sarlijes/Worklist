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

    /**
     * Adds a new material into the database
     *
     * @param       material            The new material with the info provided by an user
     * @return                          the newly created material
     * @throws      SQLException        Indicates that an <code>SQLException</code> has occurred during transaction
     */

    @Override
    public Material create(Material material) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Material (name, details) " +
                "VALUES (?, ?)");
        stmt.setString(1, material.getName());
        stmt.setString(2, material.getDetails());
        stmt.executeUpdate();

        return read(sqlUtils.getGeneratedId(stmt));
    }

    /**
     * Reads a material from the database by its id
     *
     * @param       id      The id of the material
     * @return              The found material or null, if no material was found with the id
     *
     * @throws SQLException Indicates that an <code>SQLException</code> has occurred during transaction
     */

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

    /**
     * Reads a material from the database by its name
     *
     * @param       name        The name of the material
     *
     * @return                  The found material or null, if no material was found with the name
     *
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

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

    /**
     *
     * !! Not yet implemented, not used (not in the scope of this course project)
     *
     * @param   material            a material object with the new info
     * @param   id                  the id of the material to edit
     *
     * @return  the given material without editing it
     * @throws SQLException          Indicates that an <code>SQLException</code> has occurred during transaction
     */

    @Override
    public Material update(Material material, Integer id) throws SQLException {
        // Not implemented
        return null;
    }

    /**
     * Deletes a material from the database
     *
     * @param       id              The id of the material to be deleted
     * @throws      SQLException    Indicates that an <code>SQLException</code> has occurred during transaction
     */

    @Override
    public void delete(Integer id) throws SQLException {
        // Not implemented
    }

    /**
     * Lists all the <code>Material</code> objects from the database
     *
     * @return                          all the <code>Material</code> objects as an arraylist
     *
     * @throws SQLException             Indicates that an <code>SQLException</code> has occurred during transaction
     */

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

    /**
     * Parses an <code>Material</code> from a <code>ResultSet</code>
     *
     * @param resultSet              <code>ResultSet</code> returned from the database
     *
     * @return                       <code>Material</code> object
     *
     * @throws SQLException          Indicates that an <code>SQLException</code> has occurred during transaction
     */

    public Material parseMaterialFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String details = resultSet.getString("details");
        Material m = new Material(id, name, details);
        return m;
    }

}

