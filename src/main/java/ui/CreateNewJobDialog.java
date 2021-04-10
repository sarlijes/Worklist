package ui;

import dao.Job;
import dao.JobDao;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class CreateNewJobDialog extends JobDialog {

    private JobDao jobDao;

    public CreateNewJobDialog(JobDao jobDao, Stage stage, GridPane grid) {
        super(stage, grid);
        this.jobDao = jobDao;
    }

    public void start(Stage stage) {

        stage.setTitle("Insert new job");

        Text inputLabel = new Text("Insert new job");
        inputLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(inputLabel, 0, 0);

        Button saveButton = new Button("Save");
        grid.add(saveButton, 0, 8);

        saveButton.setOnAction((ActionEvent e) -> {

            Job job = new Job(nameTextField.getText(), dueDatePicker.getValue(), quantitySpinner.getValue(),
                    materialTextField.getText(), workloadEstimateSpinner.getValue(), detailsTextField.getText(),
                    customerTextField.getText());

            try {
                if (customerTextField.validate() && nameTextField.validate() && materialTextField.validate()) {
                    jobDao.create(job);
                    stage.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

}
