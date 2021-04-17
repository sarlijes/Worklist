import ui.WorklistUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");
        WorklistUI.main(args, connection);
    }
}