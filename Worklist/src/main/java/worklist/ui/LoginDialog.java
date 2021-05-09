/**
 * A dialog that allows users to either log in or create a new account
 */

package worklist.ui;

import com.jfoenix.controls.JFXPasswordField;
import worklist.dao.EmployeeDao;
import worklist.entity.Employee;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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

    boolean errorOccurred = false;

    /**
     * @param employeeDao <code>EmployeeDao</code> to allow database connections
     * @param stage       <code>Stage</code> represents the window if the dialog
     * @param grid        <code>GridPane</code> used to position buttons an other components to the view
     * @param b           <code>ResourceBundle</code> to allow localizing texts
     */

    public LoginDialog(EmployeeDao employeeDao, Stage stage, GridPane grid, ResourceBundle b) {

        this.employeeDao = employeeDao;
        this.stage = stage;
        this.grid = grid;
        this.b = b;
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setVgap(30);
        this.grid.setHgap(30);
        this.grid.setPadding(new Insets(25, 25, 25, 25));

        logInLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        createNewAccountLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginInvalidLabel.setTextFill(Color.RED);

        passwordTextField.setPromptText(b.getString("password"));
        newEmployeePasswordTextField.setPromptText(b.getString("password"));

        usernameTextField.textProperty().addListener(event -> {
            if (errorOccurred) loginInvalidLabel.setVisible(false);
        });
        passwordTextField.textProperty().addListener(event -> {
            if (errorOccurred)
                loginInvalidLabel.setVisible(false);
        });
        newEmployeeUsernameTextField.textProperty().addListener(event -> {
            if (errorOccurred)
                newAccountInfoLabel.setVisible(false);
        });
        newEmployeePasswordTextField.textProperty().addListener(event -> {
            if (errorOccurred)
                newAccountInfoLabel.setVisible(false);
        });

        this.grid.add(logInLabel, 0, 0);
        this.grid.add(usernameTextField, 0, 1);
        this.grid.add(passwordTextField, 0, 2);
        this.grid.add(loginInvalidLabel, 0, 3);
        this.grid.add(loginButton, 0, 4);

        this.grid.add(createNewAccountLabel, 1, 0);
        this.grid.add(newEmployeeUsernameTextField, 1, 1);
        this.grid.add(newEmployeePasswordTextField, 1, 2);
        this.grid.add(newAccountInfoLabel, 1, 3);
        this.grid.add(createNewAccountButton, 1, 4);

        loginInvalidLabel.setVisible(false);

        loginButton.setOnAction((ActionEvent event) -> {
            try {
                Optional<Employee> employee = Optional.ofNullable(this.employeeDao.authenticate(usernameTextField.getText(),
                        passwordTextField.getText()));
                if (employee.isPresent()) {
                    loggedInEmployee = employee.get();
                    authenticated = true;
                    this.stage.close();
                } else {
                    loginInvalidLabel.setVisible(true);
                    errorOccurred = true;
                }
            } catch (SQLException exception) {
                loginInvalidLabel.setVisible(true);
                errorOccurred = true;
            }
        });

        createNewAccountButton.setOnAction((ActionEvent event) -> {

            String username = newEmployeeUsernameTextField.getText();
            String password = newEmployeePasswordTextField.getText();

            if (username.length() < 5 || password.length() < 5) {
                newAccountInfoLabel.setText(b.getString("username_or_password_too_short"));
                newAccountInfoLabel.setVisible(true);
                errorOccurred = true;
                return;
            }
            errorOccurred = false;
            try {
                Employee employee = new Employee(username, password);
                employee = this.employeeDao.create(employee);

                if (employee == null) {
                    newAccountInfoLabel.setText(b.getString("creating_new_account_failed"));
                    newAccountInfoLabel.setVisible(true);
                    errorOccurred = true;
                } else {
                    newAccountInfoLabel.setText(b.getString("creating_new_account_success"));
                    newAccountInfoLabel.setVisible(true);
                }
            } catch (SQLException exception) {
                newAccountInfoLabel.setText(b.getString("creating_new_account_failed"));
            }
        });

        Scene scene = new Scene(this.grid);
        this.stage.setScene(scene);
        this.stage.show();

    }

    /**
     * Returns an <code>Emploee</code> that has been successfully authenticates and is thus logged into the app
     *
     * @return An authenticated <code>Employee</code> or null
     */

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

}
