package ui;

import dao.Job;
import dao.JobDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class CreateNewJobDialog extends Application {

    private JobDao jobDao;

    public CreateNewJobDialog(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Insert new job");
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text inputLabel = new Text("Insert new job");
        inputLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(inputLabel, 0, 0);

        // Customer

        Label customer = new Label("Customer:");
        grid.add(customer, 0, 1);
        TextField customerTextField = new TextField();
        grid.add(customerTextField, 1, 1);

        // Name

        Label name = new Label("Name:");
        grid.add(name, 0, 2);
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 2);

        // Material

        Label material = new Label("Material:");
        grid.add(material, 0, 3);
        TextField materialTextField = new TextField();
        grid.add(materialTextField, 1, 3);

        // Details

        Label details = new Label("Details:");
        grid.add(details, 0, 4);
        TextField detailsTextField = new TextField();
        grid.add(detailsTextField, 1, 4);

        // Due date

        Label dueDateLabel = new Label("Due date:");
        grid.add(dueDateLabel, 0, 5);

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setShowWeekNumbers(true);
        Label dueDateSelection = new Label("");
        dueDatePicker.setOnAction(e -> dueDateSelection.setText(String.valueOf(dueDatePicker.getValue())));
        grid.add(dueDatePicker, 1, 5);

        // Quantity

        Label quantityLabel = new Label("Quantity:");
        grid.add(quantityLabel, 0, 6);

        final Spinner<Integer> quantitySpinner = new Spinner<>(1, 10_000, 1, 1);
        SpinnerValueFactory<Integer> quantityValueFactory = quantitySpinner.getValueFactory();

        quantitySpinner.setValueFactory(quantityValueFactory);
        quantitySpinner.setEditable(true);
        grid.add(quantitySpinner, 1, 6);

        // Work load estimate

        Label workloadEstimateLabel = new Label("Work load estimate");
        grid.add(workloadEstimateLabel, 0, 7);

        final Spinner<Double> workloadEstimateSpinner = new Spinner<>(0.5, 1000.0, 0.5, 0.5);
        SpinnerValueFactory<Double> workloadEstimateValueFactory = workloadEstimateSpinner.getValueFactory();

        workloadEstimateSpinner.setValueFactory(workloadEstimateValueFactory);
        workloadEstimateSpinner.setEditable(true);
        grid.add(workloadEstimateSpinner, 1, 7);

        // Save button

        Button saveButton = new Button("Save");
        grid.add(saveButton, 0, 8);

        saveButton.setOnAction((ActionEvent e) -> {
            Job job = new Job(nameTextField.getText(), dueDatePicker.getValue(), quantitySpinner.getValue(),
                    materialTextField.getText(), workloadEstimateSpinner.getValue(), detailsTextField.getText(),
                    customerTextField.getText());

            try {
                jobDao.create(job);
                System.out.println("Created new job");
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }


}
