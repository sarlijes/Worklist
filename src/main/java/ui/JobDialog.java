package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public abstract class JobDialog extends BaseView {

    public void start(Stage stage) {
        // This will be overridden
    }

    Stage stage;
    GridPane grid;

    JobJFXTextField customerTextField = new JobJFXTextField("", "", true);

    JobJFXTextField nameTextField = new JobJFXTextField("", "", true);

    JobJFXTextField materialTextField = new JobJFXTextField("", "", true);

    JobJFXTextField detailsTextField = new JobJFXTextField("", "", false);

    Label dueDateLabel = new Label("");
    Label dueDateSelection = new Label("");
    DatePicker dueDatePicker = new DatePicker();

    Label quantityLabel = new Label("");
    Spinner<Integer> quantitySpinner = new Spinner<>(1, 10_000, 1, 1);

    Label workloadEstimateLabel = new Label("");
    Spinner<Double> workloadEstimateSpinner = new Spinner<>(0.5, 1000.0, 0.5, 0.5);


    public JobDialog(Stage stage, GridPane grid, ResourceBundle b) {
        this.stage = stage;
        this.grid = grid;
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Customer

        customerTextField.promptTextProperty().bind(langProperty().bind("customer"));
        customerTextField.getValidators().get(0).messageProperty()
                .bind(langProperty().bind("field_cannot_be_empty"));
        grid.add(customerTextField, 0, 1);

        // Name

        nameTextField.promptTextProperty().bind(langProperty().bind("name"));
        nameTextField.getValidators().get(0).messageProperty()
                .bind(langProperty().bind("field_cannot_be_empty"));
        grid.add(nameTextField, 0, 2);

        // Material

        materialTextField.promptTextProperty().bind(langProperty().bind("material"));
        materialTextField.getValidators().get(0).messageProperty()
                .bind(langProperty().bind("field_cannot_be_empty"));
        grid.add(materialTextField, 0, 3);

        // Details

        detailsTextField.promptTextProperty().bind(langProperty().bind("details"));
        grid.add(detailsTextField, 0, 4);

        // Due date

        dueDateLabel.textProperty().bind(langProperty().bind("due_date"));
        grid.add(dueDateLabel, 0, 5);
        dueDatePicker.setShowWeekNumbers(true);
        dueDatePicker.setOnAction(e -> dueDateSelection.setText(String.valueOf(dueDatePicker.getValue())));
        grid.add(dueDatePicker, 1, 5);

        // TODO add validator so that due date cannot be in the past

        // Quantity

        quantityLabel.textProperty().bind(langProperty().bind("quantity"));
        grid.add(quantityLabel, 0, 6);
        SpinnerValueFactory<Integer> quantityValueFactory = quantitySpinner.getValueFactory();

        quantitySpinner.setValueFactory(quantityValueFactory);
        quantitySpinner.setEditable(true);
        grid.add(quantitySpinner, 1, 6);

        // Work load estimate

        workloadEstimateLabel.textProperty().bind(langProperty().bind("work_load_estimate"));
        grid.add(workloadEstimateLabel, 0, 7);

        SpinnerValueFactory<Double> workloadEstimateValueFactory = workloadEstimateSpinner.getValueFactory();

        workloadEstimateSpinner.setValueFactory(workloadEstimateValueFactory);
        workloadEstimateSpinner.setEditable(true);
        grid.add(workloadEstimateSpinner, 1, 7);

    }

}
