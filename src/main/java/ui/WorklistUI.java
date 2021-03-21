package ui;

import java.sql.*;
import java.util.ArrayList;

import dao.Job;
import dao.JobDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private TableView<Job> table = new TableView<>();
    private ObservableList<Job> data = FXCollections.observableArrayList(new ArrayList<>());
    Scene scene = new Scene(new Group());
    final VBox vbox = new VBox();

    @Override
    public void start(Stage stage) throws SQLException {

        stage.setTitle("Worklist App");
        stage.setHeight(600);
        stage.setWidth(1100);

        final Label label = new Label("Worklist");
        label.setFont(new Font(20));

        JobDao jobDao = new JobDao();
        data = FXCollections.observableArrayList(jobDao.list());
        table.setEditable(true);

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("id"));
        idColumn.setMinWidth(30);

        TableColumn createdColumn = new TableColumn("Created");
        createdColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("created"));
        createdColumn.setMinWidth(100);

        TableColumn customerColumn = new TableColumn("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("customer"));
        customerColumn.setMinWidth(100);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("name"));
        nameColumn.setMinWidth(100);

        TableColumn materialColumn = new TableColumn("Material");
        materialColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("material"));
        materialColumn.setMinWidth(100);

        TableColumn quantityColumn = new TableColumn("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("quantity"));
        quantityColumn.setMinWidth(100);

        TableColumn dueDateColumn = new TableColumn("Due date");
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("dueDate"));
        dueDateColumn.setMinWidth(100);

        TableColumn workLoadEstimateColumn = new TableColumn("Work load estimate");
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("workLoadEstimate"));
        dueDateColumn.setMinWidth(100);

        TableColumn detailsColumn = new TableColumn("Details");
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("details"));
        dueDateColumn.setMinWidth(200);

        table.setItems(data);
        table.getColumns().addAll(idColumn, createdColumn, customerColumn, nameColumn, materialColumn,
                quantityColumn, dueDateColumn, workLoadEstimateColumn, detailsColumn);

        addButtonToTable();

        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private void addButtonToTable() {
        TableColumn<Job, Void> markAsDoneButton = new TableColumn("Mark as done");

        Callback<TableColumn<Job, Void>, TableCell<Job, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Job, Void> call(final TableColumn<Job, Void> param) {
                final TableCell<Job, Void> cell = new TableCell<>() {
                    private final Button button = new Button("Action");
                    {
                        button.setOnAction((ActionEvent event) -> {
                            Job data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data.getName());
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

        markAsDoneButton.setCellFactory(cellFactory);
        table.getColumns().add(markAsDoneButton);

    }

    public static void main(String[] args) {
        launch(args);
    }
}