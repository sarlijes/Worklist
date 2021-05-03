package worklist.ui;

import com.jfoenix.controls.JFXPasswordField;
import worklist.dao.EmployeeDao;
import worklist.entity.Employee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class LoginDialog {

    private EmployeeDao employeeDao;
    public boolean authenticated = false;
    private Employee loggedInEmployee;

    public void start(Stage stage) {
    }

    Stage stage;
    GridPane grid;
    private ResourceBundle b = ResourceBundle.getBundle("Label", new Locale("fi", "FI"));

    Text logInLabel = new Text(b.getString("log_in"));

    Text createNewAccountLabel = new Text(b.getString("create_new_account"));

    JobJFXTextField usernameTextField = new JobJFXTextField(b.getString("username"),
            b.getString("field_cannot_be_empty"), true);

    JFXPasswordField passwordTextField = new JFXPasswordField();

    JobJFXTextField newEmployeeUsernameTextField = new JobJFXTextField(b.getString("username"),
            b.getString("field_cannot_be_empty"), true);

    JFXPasswordField newEmployeePasswordTextField = new JFXPasswordField();

    Button loginButton = new Button(b.getString("log_in"));
    Label loginInvalidLabel = new Label(b.getString("invalid_login"));

    Button createNewAccountButton = new Button(b.getString("create_new_account"));
    Label newAccountInfoLabel = new Label();

    boolean errorOccured = false;

    public LoginDialog(EmployeeDao e, Stage s, GridPane g, ResourceBundle b) {
        this.employeeDao = e;
        this.stage = s;
        this.grid = g;
        this.b = b;
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setHgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));

        logInLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        createNewAccountLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginInvalidLabel.setTextFill(Color.RED);

        passwordTextField.setPromptText(b.getString("password"));
        newEmployeePasswordTextField.setPromptText(b.getString("password"));

        usernameTextField.textProperty().addListener(event -> {
            if (errorOccured) loginInvalidLabel.setVisible(false);
        });
        passwordTextField.textProperty().addListener(event -> {
            if (errorOccured)
                loginInvalidLabel.setVisible(false);
        });
        newEmployeeUsernameTextField.textProperty().addListener(event -> {
            if (errorOccured)
                newAccountInfoLabel.setVisible(false);
        });
        newEmployeePasswordTextField.textProperty().addListener(event -> {
            if (errorOccured)
                newAccountInfoLabel.setVisible(false);
        });

        grid.add(logInLabel, 0, 0);
        grid.add(usernameTextField, 0, 1);
        grid.add(passwordTextField, 0, 2);
        grid.add(loginInvalidLabel, 0, 3);
        grid.add(loginButton, 0, 4);

        grid.add(createNewAccountLabel, 1, 0);
        grid.add(newEmployeeUsernameTextField, 1, 1);
        grid.add(newEmployeePasswordTextField, 1, 2);
        grid.add(newAccountInfoLabel, 1, 3);
        grid.add(createNewAccountButton, 1, 4);

        loginInvalidLabel.setVisible(false);

        loginButton.setOnAction((ActionEvent event) -> {
            try {
                Optional<Employee> employee = Optional.ofNullable(employeeDao.authenticate(usernameTextField.getText(),
                        passwordTextField.getText()));
                if (employee.isPresent()) {
                    loggedInEmployee = employee.get();
                    authenticated = true;
                    stage.close();
                } else {
                    loginInvalidLabel.setVisible(true);
                    errorOccured = true;
                }
            } catch (SQLException exception) {
                loginInvalidLabel.setVisible(true);
                errorOccured = true;
            }
        });

        createNewAccountButton.setOnAction((ActionEvent event) -> {

            String username = newEmployeeUsernameTextField.getText();
            String password = newEmployeePasswordTextField.getText();

            if (username.length() < 5 || password.length() < 5) {
                newAccountInfoLabel.setText(b.getString("username_or_password_too_short"));
                newAccountInfoLabel.setVisible(true);
                errorOccured = true;
                return;
            }
            errorOccured = false;
            try {
                Employee employee = new Employee(username, password);
                employee = employeeDao.create(employee);

                if (employee == null) {
                    newAccountInfoLabel.setText(b.getString("creating_new_account_failed"));
                    newAccountInfoLabel.setVisible(true);
                    errorOccured = true;
                } else {
                    newAccountInfoLabel.setText(b.getString("creating_new_account_success"));
                    newAccountInfoLabel.setVisible(true);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                newAccountInfoLabel.setText(b.getString("creating_new_account_failed"));
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

}
