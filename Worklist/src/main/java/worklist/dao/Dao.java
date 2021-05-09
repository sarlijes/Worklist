package worklist.dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    T create(T object) throws SQLException;
    T read(K id) throws SQLException;
    T update(T object, K id) throws SQLException;
    void delete(K id) throws SQLException;
    List<T> list() throws SQLException;

}