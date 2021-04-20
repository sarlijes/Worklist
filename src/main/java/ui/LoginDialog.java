package ui;

import dao.EmployeeDao;
import domain.Employee;
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

    JobJFXTextField newEmployeeUsernameTextField = new JobJFXTextField(b.getString("username"),
            b.getString("field_cannot_be_empty"), true);

    JobJFXTextField newEmployeePasswordTextField = new JobJFXTextField(b.getString("password"),
            b.getString("field_cannot_be_empty"), true);

    Button loginButton = new Button(b.getString("log_in"));
    Label loginInvalidLabel = new Label(b.getString("invalid_login"));

    Button createNewAccountButton = new Button(b.getString("create_new_account"));
    Label newAccountInfoLabel = new Label();

    public LoginDialog(EmployeeDao e, Stage s, GridPane g, ResourceBundle b) {
        this.employeeDao = e;
        this.stage = s;
        this.grid = g;
        this.b = b;
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setHgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(usernameTextField, 0, 1);
        grid.add(passwordTextField, 0, 2);
        grid.add(loginInvalidLabel, 0, 3);
        grid.add(loginButton, 0, 4);

        grid.add(newEmployeeUsernameTextField, 1, 1);
        grid.add(newEmployeePasswordTextField, 1, 2);
        grid.add(newAccountInfoLabel, 1, 3);
        grid.add(createNewAccountButton, 1, 4);

        loginInvalidLabel.setVisible(false);

        loginButton.setOnAction((ActionEvent event) -> {
            try {
                if (employeeDao.authenticate(usernameTextField.getText(), passwordTextField.getText())) {
                    authenticated = true;
                    stage.close();
                } else {
                    loginInvalidLabel.setVisible(true);
                }
            } catch (SQLException exception) {
                loginInvalidLabel.setVisible(true);
            }
        });

        createNewAccountButton.setOnAction((ActionEvent event) -> {
            try {
                Employee employee = new Employee(newEmployeeUsernameTextField.getText(),
                        newEmployeePasswordTextField.getText());
                employeeDao.create(employee);

                // TODO allow only unique usernames
                // TODO add validation to length
                // TODO make the password field password-field-like

                newAccountInfoLabel.setText(b.getString("creating_new_account_success"));
            } catch (SQLException exception) {
                exception.printStackTrace();
                newAccountInfoLabel.setText(b.getString("creating_new_account_failed"));
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

    }

}
