import worklist.dao.SQLUtils;
import worklist.ui.WorklistUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static SQLUtils sqlUtils = new SQLUtils();

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");
        sqlUtils.createTables(connection);
        WorklistUI.main(args, connection);
    }

}