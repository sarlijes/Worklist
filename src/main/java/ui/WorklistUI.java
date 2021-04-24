package ui;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.EmployeeDao;
import domain.Employee;
import domain.Job;
import dao.JobDao;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class WorklistUI extends Application {

    Connection connection;
    private static JobDao jobDao;
    private static EmployeeDao employeeDao;
    private Locale locale = new Locale("fi", "FI");
    ResourceBundle b = ResourceBundle.getBundle("Label", locale);
    Employee loggedInEmployee = null;

    private TableView<Job> table = new TableView<>();
    private ObservableList<Job> data = FXCollections.observableArrayList(new ArrayList<>());
    Scene mainScene = new Scene(new Group());
    final VBox vbox = new VBox();

    public void refreshTableData() throws SQLException {
        data = FXCollections.observableArrayList(jobDao.list());
        table.setItems(data);
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

        stage.setHeight(600);
        stage.setWidth(1200);

        refreshTableData();

        table.setEditable(true);

        table.setPlaceholder(new Label("No visible columns and/or data exist."));

        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Job job, boolean empty) {
                super.updateItem(job, empty);
                if (job == null)
                    return;
                else if (job.isFinished())
                    setStyle("-fx-background-color: rgba(146,146,146,0.4);");
                else
                    setStyle("");
            }
        });

        final Label label = new Label(b.getString("worklist"));
        label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Button addNewJobButton = addNewJobButton();

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
        materialColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("material"));
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

        table.setItems(data);
        table.getColumns().addAll(idColumn, createdColumn, creatorColumn, customerColumn, nameColumn, materialColumn,
                quantityColumn, dueDateColumn, workLoadEstimateColumn, detailsColumn);

        TableColumn workLoadActualColumn = new TableColumn(b.getString("actual_work_load"));
        workLoadActualColumn.setCellValueFactory(new PropertyValueFactory<Job, Double>("workloadActual"));
        workLoadActualColumn.setMinWidth(100);

        addButtonToTable();

        table.getColumns().add(workLoadActualColumn);

        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, addNewJobButton, table);

        ((Group) mainScene.getRoot()).getChildren().addAll(vbox);

    }

    private Button addNewJobButton() {
        Button addNewJobButton = new Button(b.getString("insert_new_job"));

        addNewJobButton.setOnAction((ActionEvent event) -> {
            Stage s = new Stage();
            CreateNewJobDialog createNewJobDialog = new CreateNewJobDialog(jobDao, s, new GridPane(), b, loggedInEmployee);

            s.setOnHiding(ev -> {
                try {
                    refreshTableData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            createNewJobDialog.start(s);
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

                            if (chosenJob.isFinished())
                                jobDialog = new ViewFinishedJobDialog(jobDao, stage, new GridPane(), chosenJob, b, loggedInEmployee);
                            else jobDialog = new EditJobDialog(jobDao, stage, new GridPane(), chosenJob, b, loggedInEmployee);

                            stage.setOnHiding(ev -> {
                                try {
                                    refreshTableData();
                                } catch (SQLException e) {
                                    e.printStackTrace();
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
        table.getColumns().add(editButton);
    }

    public static void main(String[] args, Connection connection) {
        jobDao = new JobDao(connection);
        employeeDao = new EmployeeDao(connection);
        launch(args);
    }

    @Override
    public void stop() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}