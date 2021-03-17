import java.sql.*;
import java.util.ArrayList;

import dao.Job;
import dao.JobDao;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView<Job> table = new TableView<Job>();
    private ObservableList<Job> data = FXCollections.observableArrayList(new ArrayList<Job>());

    @Override
    public void start(Stage stage) throws SQLException {

        JobDao jobDao = new JobDao();
        data = FXCollections.observableArrayList(jobDao.list());

        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label label = new Label("List");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Job, String>("name"));

        TableColumn lastNameCol = new TableColumn("Material");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Job, String>("material"));

        TableColumn emailCol = new TableColumn("dueDate");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Job, String>("dueDate"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

//    @Override
//    public void start(Stage window) throws SQLException {
//
//        JobDao jobDao = new JobDao();
//        data = FXCollections.observableArrayList(jobDao.list());
//
//
//        Label header = new Label("Tyolista");
//
//        FlowPane buttonGroup = new FlowPane();
//
//        buttonGroup.getChildren().add(header);
//
//        for (Job job : jobDao.list()) {
//            Button b = new Button(job.getName());
//            buttonGroup.getChildren().add(b);
//        }
//
//        Scene scene = new Scene(buttonGroup);
//        window.setScene(scene);
//        window.setResizable(true);
//        window.setWidth(500);
//        window.setHeight(600);
//        window.show();
//    }

    public static void main(String[] args) {
        launch(Main.class);
    }
}