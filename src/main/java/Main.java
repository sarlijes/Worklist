import java.sql.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ikkuna) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Job");
        ResultSet resultSet = statement.executeQuery();

        Label teksti = new Label("Työlista");

        FlowPane buttonGroup = new FlowPane();
        
        buttonGroup.getChildren().add(teksti);


        while (resultSet.next()) {

            String name = resultSet.getString("name");
            System.out.println(name);
            Button b = new Button(name);
            buttonGroup.getChildren().add(b);
            
        }
        
        connection.close();
        Scene nakyma = new Scene(buttonGroup);
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }

    public static void main(String[] args) {
        launch(Main.class);
    }
}