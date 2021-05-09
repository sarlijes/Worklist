package worklist;

import static org.junit.Assert.*;

import org.junit.AfterClass;
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

        // The database has 2 default materials

        assertEquals(6, materialDao.list().size());
    }

    @Test
    public void cannotReadNonExistingMaterial() throws SQLException {
        Material m = materialDao.read(7798);
        assert (m == null);

        m = materialDao.readByName("aadssdasds");
        assert (m == null);
    }

    @Test
    public void materialConstructorsWork() throws SQLException {
        Material m1 = new Material(669, "material name", "material details");
        assert (m1 != null);
        assertEquals("material name", m1.getName());
        assertEquals("material details", m1.getDetails());

        Material m2 = new Material("other material name", "other material details");
        assert (m2 != null);
        assertEquals("other material name", m2.getName());
        assertEquals("other material details", m2.getDetails());
    }

    @Test
    public void createdMaterialHasTheParametersItWasCreatedWith() throws SQLException {
        Material m = materialDao.create(new Material("AKPWNV 55", "fascinating details"));
        assert (m != null);
        assertEquals("AKPWNV 55", m.getName());
        assertEquals("fascinating details", m.getDetails());

    }

    @AfterClass
    public static void closeTestConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void cannotCreateTwoMaterialsWithSameUsername() throws SQLException {
        // TODO implement logic and add test here
    }

}
