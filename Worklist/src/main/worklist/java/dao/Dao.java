package worklist.dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    T create(T object) throws SQLException;
    T read(K id) throws SQLException;
    T update(T object, K id) throws SQLException;
    void delete(K id) throws SQLException;
    List<T> list() throws SQLException;

    private int getGeneratedId(PreparedStatement stmt) throws SQLException {
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        stmt.close();
        return id;
    }

}