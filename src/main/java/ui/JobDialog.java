package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class JobDialog {

    public void start(Stage stage) {
        // This will be overridden
    }

    Stage s;
    GridPane grid;

    JobJFXTextField customerTextField = new JobJFXTextField("Customer", "field cannot be empty", true);

    JobJFXTextField nameTextField = new JobJFXTextField("Name", "field cannot be empty", true);

    JobJFXTextField materialTextField = new JobJFXTextField("Material", "field cannot be empty", true);

    JobJFXTextField detailsTextField = new JobJFXTextField("Details");

    Label dueDateLabel = new Label("Due date:");
    Label dueDateSelection = new Label("");
    DatePicker dueDatePicker = new DatePicker();

    Label quantityLabel = new Label("Quantity:");
    Spinner<Integer> quantitySpinner = new Spinner<>(1, 10_000, 1, 1);

    Label workloadEstimateLabel = new Label("Work load estimate");
    Spinner<Double> workloadEstimateSpinner = new Spinner<>(0.5, 1000.0, 0.5, 0.5);

    public JobDialog(Stage stage, GridPane grid) {
        this.s = stage;
        this.grid = grid;
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Customer

        grid.add(customerTextField, 0, 1);

        // Name

        grid.add(nameTextField, 0, 2);

        // Material

        grid.add(materialTextField, 0, 3);

        // Details

        grid.add(detailsTextField, 0, 4);

        // Due date

        grid.add(dueDateLabel, 0, 5);
        dueDatePicker.setShowWeekNumbers(true);
        dueDatePicker.setOnAction(e -> dueDateSelection.setText(String.valueOf(dueDatePicker.getValue())));
        grid.add(dueDatePicker, 1, 5);

        // TODO add validator so that due date cannot be in the past

        // Quantity

        grid.add(quantityLabel, 0, 6);
        SpinnerValueFactory<Integer> quantityValueFactory = quantitySpinner.getValueFactory();

        quantitySpinner.setValueFactory(quantityValueFactory);
        quantitySpinner.setEditable(true);
        grid.add(quantitySpinner, 1, 6);

        // Work load estimate

        grid.add(workloadEstimateLabel, 0, 7);

        SpinnerValueFactory<Double> workloadEstimateValueFactory = workloadEstimateSpinner.getValueFactory();

        workloadEstimateSpinner.setValueFactory(workloadEstimateValueFactory);
        workloadEstimateSpinner.setEditable(true);
        grid.add(workloadEstimateSpinner, 1, 7);

    }

}
