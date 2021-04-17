package ui;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.Job;
import dao.JobDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    private JobDao jobDao;
    private Locale locale = new Locale("fi", "FI");
    ResourceBundle b = ResourceBundle.getBundle("Label", locale);

    private TableView<Job> table = new TableView<>();
    private ObservableList<Job> data = FXCollections.observableArrayList(new ArrayList<>());
    Scene mainScene = new Scene(new Group());
    final VBox vbox = new VBox();

    public void refreshTableData() throws SQLException {
        if (jobDao == null) jobDao = new JobDao(connection);
        data = FXCollections.observableArrayList(jobDao.list());
        table.setItems(data);
    }

    @Override
    public void start(Stage stage) throws SQLException {

        VBox localeSelectionBox = new VBox(10);
        localeSelectionBox.setPadding(new Insets(50));

        final ComboBox comboBox = new ComboBox(FXCollections.observableArrayList("fi", "en"));
        comboBox.setPromptText(b.getString("select_language"));

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.locale = new Locale(newValue.toString(), "FI");
            b = ResourceBundle.getBundle("Label", this.locale);
            System.out.println(this.locale);
            stage.setScene(mainScene);
            stage.show();
        });

        localeSelectionBox.getChildren().add(comboBox);

        Scene localeSelectionScene = new Scene(localeSelectionBox, 300, 250);

        stage.setScene(localeSelectionScene);
        stage.show();

        stage.setTitle(b.getString("worklist_app"));
        stage.setHeight(600);
        stage.setWidth(1100);

        jobDao = new JobDao(connection);
        refreshTableData();

        table.setEditable(true);

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

        Button addNewJobButton = new Button(b.getString("insert_new_job"));

        addNewJobButton.setOnAction((ActionEvent event) -> {
            Stage s = new Stage();
            CreateNewJobDialog dialog = new CreateNewJobDialog(jobDao, s, new GridPane(), b);

            s.setOnHiding(ev -> {
                try {
                    refreshTableData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dialog.start(s);
        });

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("id"));
        idColumn.setMinWidth(30);

        TableColumn createdColumn = new TableColumn(b.getString("created"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("createdString"));
        createdColumn.setMinWidth(100);

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

        TableColumn workLoadEstimateColumn = new TableColumn(b.getString("work_load_estimate"));
        workLoadEstimateColumn.setCellValueFactory(new PropertyValueFactory<Job, Double>("workloadEstimate"));
        workLoadEstimateColumn.setMinWidth(100);

        TableColumn detailsColumn = new TableColumn(b.getString("details"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("details"));
        detailsColumn.setMinWidth(100);

        table.setItems(data);
        table.getColumns().addAll(idColumn, createdColumn, customerColumn, nameColumn, materialColumn,
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
                                jobDialog = new ViewFinishedJobDialog(jobDao, stage, new GridPane(), chosenJob, b);
                            else jobDialog = new EditJobDialog(jobDao, stage, new GridPane(), chosenJob, b);

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
        connection = connection;
        launch(args);
    }

    @Override
    public void stop() throws SQLException {
        connection.close();
    }

}