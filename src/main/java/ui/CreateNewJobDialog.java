package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class CreateNewJobDialog extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Add new job");
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text inputLabel = new Text("Insert information of new job");
        grid.add(inputLabel, 0, 0);

        Label customer = new Label("Customer:");
        grid.add(customer, 0, 1);
        TextField customerTextField = new TextField();
        grid.add(customerTextField, 1, 1);

        Label name = new Label("Name:");
        grid.add(name, 0, 2);
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 2);

        Label material = new Label("Material:");
        grid.add(material, 0, 3);
        TextField materialTextField = new TextField();
        grid.add(materialTextField, 1, 3);

        Label details = new Label("Details:");
        grid.add(details, 0, 4);
        TextField detailsTextField = new TextField();
        grid.add(detailsTextField, 1, 4);

        Label dueDateLabel = new Label("Due date:");
        grid.add(dueDateLabel, 0, 5);
        DatePicker dueDatePicker = new DatePicker();
        
        Label dueDateSelection = new Label("");

        EventHandler<ActionEvent> dueDatePickerEvent = e -> {
            LocalDate i = dueDatePicker.getValue();
            dueDateSelection.setText("Date :" + i);
        };
        dueDatePicker.setOnAction(dueDatePickerEvent);
        dueDatePicker.setShowWeekNumbers(true);

        grid.add(dueDatePicker, 1, 5);

//        dueDatePicker.setOnAction(event);
/*
    public Job(int id, String name, LocalDateTime created, LocalDate duedate, LocalDate finished, LocalDate deleted,
               int quantity, String material, Double workloadEstimate, Double workloadActual,
               String details, String customer)
 */

// grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        Text scenetitle = new Text("Welcome");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 1,1);
//
//        Label userName = new Label("User Name:");
//        grid.add(userName, 0, 1);
//
//        TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Password:");
//        grid.add(pw, 0, 2);
//
//        PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
//
        Button btn = new Button("Save");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 12);

        btn.setOnAction((ActionEvent e) -> {
            System.out.println("Create new");
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }


}
