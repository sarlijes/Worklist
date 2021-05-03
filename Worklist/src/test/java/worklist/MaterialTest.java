package worklist;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import worklist.dao.MaterialDao;
import worklist.dao.SQLUtils;
import worklist.entity.Material;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaterialTest {

    private static MaterialDao materialDao;
    private static Connection connection;
    private static SQLUtils sqlUtils;

    @BeforeClass
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        materialDao = new MaterialDao(connection);
        sqlUtils = new SQLUtils();
        sqlUtils.createTables(connection);

        PreparedStatement stmt = connection.prepareStatement(
                "insert into material (name, details) " +
                        "values ('CrMo', '');" +
                        "insert into material (name, details) " +
                        "values ('Al', '');" +
                        "insert into material (name, details) " +
                        "values ('material 2', 'material details');" +
                        "insert into material (name, details) " +
                        "values ('material 3', 'material details');" +
                        "");
        stmt.executeUpdate();
        stmt.close();

        assertEquals(4, materialDao.list().size());
    }

    @Test
    public void cannotReadNonexistingMaterial() throws SQLException {
        Material m = materialDao.read(985);
        assert (m == null);
    }

    // TODO implement more tests


}
