package worklist.ui;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import worklist.dao.EmployeeDao;
import worklist.dao.MaterialDao;
import worklist.entity.Employee;
import worklist.entity.Job;
import worklist.dao.JobDao;
import worklist.entity.Material;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class WorklistUI extends Application {


    Connection connection;
    private static JobDao jobDao;
    private static EmployeeDao employeeDao;
    private static MaterialDao materialDao;
    private Locale locale = new Locale("fi", "FI");
    ResourceBundle b = ResourceBundle.getBundle("Label", locale);
    Employee loggedInEmployee = null;

    private TableView<Job> jobTableView = new TableView<>();
    private TableView<Material> materialTableView = new TableView<>();
    private ObservableList<Job> jobData = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Material> materialData = FXCollections.observableArrayList(new ArrayList<>());

    Scene mainScene = new Scene(new Group());
    final GridPane grid = new GridPane();

    JobJFXTextField newMaterialNameTextField = newMaterialNameTextField();
    JobJFXTextField newMaterialDetailsTextField = newMaterialDetailsTextField();
    Label newMaterialErrorLabel = new Label(b.getString("material_already_exists"));

    public void refreshJobTableData() throws SQLException {
        jobData = FXCollections.observableArrayList(jobDao.list());
        jobTableView.setItems(jobData);
    }

    public void refreshMaterialTableData() throws SQLException {
        materialData = FXCollections.observableArrayList(materialDao.list());
        materialTableView.setItems(materialData);
    }

    @Override
    public void start(Stage stage) throws SQLException {

        // Login Dialog

        Stage loginStage = new Stage();
        GridPane loginGrid = new GridPane();
        LoginDialog loginDialog = new LoginDialog(employeeDao, loginStage, loginGrid, b);

        loginStage.setOnHiding(ev -> {
            if (loginDialog.authenticated) {
                loggedInEmployee = loginDialog.getLoggedInEmployee();
                stage.setScene(mainScene);
                stage.setMaximized(true);
                stage.show();
            } else {
                stage.close();
            }
        });
        loginDialog.start(loginStage);

        Scene loginScene = new Scene(new Group());
        stage.setScene(loginScene);

        // Main view

        stage.setTitle(b.getString("worklist_app"));

        refreshJobTableData();
        refreshMaterialTableData();
        initializeJobTableView();
        initializeMaterialTableView();

        final Label label = new Label(b.getString("worklist"));
        label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Button addNewJobButton = addNewJobButton();
        Button addNewMaterialButton = newMaterialButton();
        newMaterialErrorLabel.setVisible(false);

        grid.setVgap(30);
        grid.setHgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(label, 0, 0);
        grid.add(addNewJobButton, 0, 1);
        grid.add(jobTableView, 0, 2);
        grid.add(materialTableView, 1, 2);
        grid.add(newMaterialNameTextField, 1, 3);
        grid.add(newMaterialDetailsTextField, 1, 4);
        grid.add(addNewMaterialButton, 1, 5);
        grid.add(newMaterialErrorLabel, 1, 6);

        ((Group) mainScene.getRoot()).getChildren().addAll(grid);

    }

    private void initializeMaterialTableView() {
        materialTableView.setEditable(true);

        TableColumn nameColumn = new TableColumn(b.getString("material_name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
        nameColumn.setMinWidth(100);

        TableColumn detailsColumn = new TableColumn(b.getString("details"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<Material, String>("details"));
        detailsColumn.setMinWidth(280);

        materialTableView.setItems(materialData);
        materialTableView.getColumns().addAll(nameColumn, detailsColumn);

    }

    private JobJFXTextField newMaterialNameTextField() {
        return new JobJFXTextField(b.getString("material"), b.getString("field_cannot_be_empty"), true);
    }

    private JobJFXTextField newMaterialDetailsTextField() {
        return new JobJFXTextField(b.getString("details"));
    }

    private Button newMaterialButton() {
        Button loginButton = new Button(b.getString("add_new_material"));
        loginButton.setOnAction((ActionEvent event) -> {
            try {
                if (materialDao.readByName(newMaterialNameTextField.getText()) != null) {
                    newMaterialErrorLabel.setVisible(true);
                } else {
                    newMaterialErrorLabel.setVisible(false);
                    Material m = new Material(newMaterialNameTextField.getText(), newMaterialDetailsTextField.getText());
                    materialDao.create(m);
                    newMaterialNameTextField.clear();
                    newMaterialDetailsTextField.clear();
                    refreshMaterialTableData();
                }
            } catch (SQLException exception) {
            }
        });
        return loginButton;
    }

    private void initializeJobTableView() {
        jobTableView.setEditable(true);

        jobTableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Job job, boolean empty) {
                super.updateItem(job, empty);
                if (job == null)
                    setStyle("");
                else if (job.isFinished())
                    setStyle("-fx-background-color: rgba(146,146,146,0.4);");
                else
                    setStyle("");
            }
        });

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("id"));
        idColumn.setMinWidth(30);

        TableColumn createdColumn = new TableColumn(b.getString("created"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("createdString"));
        createdColumn.setMinWidth(100);

        TableColumn creatorColumn = new TableColumn(b.getString("creator"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory("creatorName"));
        creatorColumn.setMinWidth(100);

        TableColumn customerColumn = new TableColumn(b.getString("customer"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("customer"));
        customerColumn.setMinWidth(100);

        TableColumn nameColumn = new TableColumn(b.getString("name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("name"));
        nameColumn.setMinWidth(100);

        TableColumn materialColumn = new TableColumn(b.getString("material"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("materialName"));
        materialColumn.setMinWidth(100);

        TableColumn quantityColumn = new TableColumn(b.getString("quantity"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("quantity"));
        quantityColumn.setMinWidth(100);

        TableColumn dueDateColumn = new TableColumn(b.getString("due_date"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("dueDate"));
        dueDateColumn.setMinWidth(100);

        formatDueDate(dueDateColumn);

        TableColumn workLoadEstimateColumn = new TableColumn(b.getString("work_load_estimate"));
        workLoadEstimateColumn.setCellValueFactory(new PropertyValueFactory<Job, Double>("workloadEstimate"));
        workLoadEstimateColumn.setMinWidth(100);

        TableColumn detailsColumn = new TableColumn(b.getString("details"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("details"));
        detailsColumn.setMinWidth(100);

        jobTableView.setItems(jobData);
        jobTableView.getColumns().addAll(idColumn, createdColumn, creatorColumn, customerColumn, nameColumn,
                materialColumn, quantityColumn, dueDateColumn, workLoadEstimateColumn, detailsColumn);

        TableColumn workLoadActualColumn = new TableColumn(b.getString("actual_work_load"));
        workLoadActualColumn.setCellValueFactory(new PropertyValueFactory<Job, Double>("workloadActual"));
        workLoadActualColumn.setMinWidth(100);

        addButtonToTable();

        jobTableView.getColumns().add(workLoadActualColumn);
    }

    private Button addNewJobButton() {
        Button addNewJobButton = new Button(b.getString("insert_new_job"));

        addNewJobButton.setOnAction((ActionEvent event) -> {
            Stage stage = new Stage();
            CreateNewJobDialog createNewJobDialog = new CreateNewJobDialog(jobDao, materialDao, stage, new GridPane(),
                    b, loggedInEmployee);

            stage.setOnHiding(ev -> {
                try {
                    refreshJobTableData();
                } catch (SQLException e) {
                }
            });
            createNewJobDialog.start(stage);
        });
        return addNewJobButton;
    }

    private void formatDueDate(TableColumn dueDateColumn) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        dueDateColumn.setCellFactory(column -> new TableCell<Job, LocalDate>() {
            @Override
            protected void updateItem(LocalDate localDate, boolean empty) {
                super.updateItem(localDate, empty);

                if (localDate == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(formatter.format(localDate));
                    if (localDate.isBefore(LocalDate.now())) {
                        setTextFill(Color.INDIANRED);
                        setStyle("-fx-font-weight: bold");
                    } else if (localDate.isBefore(LocalDate.now().plusWeeks(1))) {
                        setTextFill(Color.DARKORANGE);
                    }
                }
            }
        });
    }

    private void addButtonToTable() {
        TableColumn<Job, Void> editButton = new TableColumn("");

        Callback<TableColumn<Job, Void>, TableCell<Job, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Job, Void> call(final TableColumn<Job, Void> param) {
                final TableCell<Job, Void> cell = new TableCell<>() {
                    private final Button button = new Button(b.getString("open"));

                    {
                        button.setOnAction((ActionEvent event) -> {
                            Job chosenJob = getTableView().getItems().get(getIndex());

                            Stage stage = new Stage();
                            JobDialog jobDialog;

                            if (chosenJob.isFinished()) {
                                jobDialog = new ViewFinishedJobDialog(jobDao, materialDao, stage, new GridPane(), chosenJob, b,
                                        loggedInEmployee);
                            } else {
                                jobDialog = new ManageJobDialog(jobDao, materialDao, stage, new GridPane(), chosenJob, b,
                                        loggedInEmployee);
                            }

                            stage.setOnHiding(ev -> {
                                try {
                                    refreshJobTableData();
                                } catch (SQLException e) {
                                }
                            });
                            jobDialog.start(stage);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(button);
                    }
                };
                return cell;
            }
        };

        editButton.setCellFactory(cellFactory);
        jobTableView.getColumns().add(editButton);
    }

    public static void main(String[] args, Connection connection) {
        jobDao = new JobDao(connection);
        employeeDao = new EmployeeDao(connection);
        materialDao = new MaterialDao(connection);
        launch(args);
    }

    @Override
    public void stop() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}