package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JobDialog {

    Stage s;
    GridPane grid;

    Label customerLabel = new Label("Customer:");
    TextField customerTextField = new TextField();

    Label name = new Label("Name:");
    TextField nameTextField = new TextField();

    Label material = new Label("Material:");
    TextField materialTextField = new TextField();

    Label details = new Label("Details:");
    TextField detailsTextField = new TextField();

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
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Customer

        grid.add(customerLabel, 0, 1);
        grid.add(customerTextField, 1, 1);

        // Name

        grid.add(name, 0, 2);
        grid.add(nameTextField, 1, 2);

        // Material

        grid.add(material, 0, 3);
        grid.add(materialTextField, 1, 3);

        // Details

        grid.add(details, 0, 4);
        grid.add(detailsTextField, 1, 4);

        // Due date

        grid.add(dueDateLabel, 0, 5);
        dueDatePicker.setShowWeekNumbers(true);
        dueDatePicker.setOnAction(e -> dueDateSelection.setText(String.valueOf(dueDatePicker.getValue())));
        grid.add(dueDatePicker, 1, 5);

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
