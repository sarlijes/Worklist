package ui;

import dao.EmployeeDao;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginDialog {

    private EmployeeDao employeeDao;
    public boolean authenticated = false;

    public void start(Stage stage) {
    }

    Stage stage;
    GridPane grid;
    private ResourceBundle b = ResourceBundle.getBundle("Label", new Locale("fi", "FI"));

    JobJFXTextField usernameTextField = new JobJFXTextField(b.getString("username"),
            b.getString("field_cannot_be_empty"), true);

    JobJFXTextField passwordTextField = new JobJFXTextField(b.getString("password"),
            b.getString("field_cannot_be_empty"), true);

    Button loginButton = new Button(b.getString("log_in"));
    Label loginInvalidLabel = new Label(b.getString("invalid_login"));

    public LoginDialog(EmployeeDao employeeDao, Stage stage, GridPane grid, ResourceBundle b) {
        this.employeeDao = employeeDao;
        this.stage = stage;
        this.grid = grid;
        this.b = b;
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(usernameTextField, 0, 1);
        grid.add(passwordTextField, 0, 2);
        grid.add(loginInvalidLabel, 0, 3);
        grid.add(loginButton, 0, 4);

        loginInvalidLabel.setVisible(false);

        loginButton.setOnAction((ActionEvent event) -> {
            try {
                if (employeeDao.authenticate(usernameTextField.getText(), passwordTextField.getText())) {
                    authenticated = true;
                    stage.close();
                } else {
                    loginInvalidLabel.setVisible(true);
                }
            } catch (SQLException e) {
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

    }

}
