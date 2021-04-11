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
import java.util.ResourceBundle;

public class EditJobDialog extends JobDialog {

    private JobDao jobDao;
    private Job job;
    private ResourceBundle b;

    public EditJobDialog(JobDao jobDao, Stage stage, GridPane grid, Job job, ResourceBundle b) {
        super(stage, grid, b);
        this.jobDao = jobDao;
        this.job = job;
        this.b = b;
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle(b.getString("edit_job") + "(" + job.getId() + " ," + job.getName() + ")");

        Text inputLabel = new Text(b.getString("edit_job"));
        inputLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(inputLabel, 0, 0);

        customerTextField.setText(job.getCustomer());
        nameTextField.setText(job.getName());
        materialTextField.setText(job.getMaterial());
        detailsTextField.setText(job.getDetails());
        dueDatePicker.setValue(job.getDueDate());
        quantitySpinner.getValueFactory().setValue(job.getQuantity());
        workloadEstimateSpinner.getValueFactory().setValue(job.getWorkloadEstimate());

        Button saveButton = new Button(b.getString("save_changes"));
        grid.add(saveButton, 0, 8);

        saveButton.setOnAction((ActionEvent e) -> {

            Job newJob = new Job(nameTextField.getText(), dueDatePicker.getValue(), quantitySpinner.getValue(),
                    materialTextField.getText(), workloadEstimateSpinner.getValue(), detailsTextField.getText(),
                    customerTextField.getText());

            try {
                if (customerTextField.validate() && nameTextField.validate() && materialTextField.validate()) {
                    jobDao.update(newJob, job.getId());
                    stage.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Text markAsDoneLabel = new Text(b.getString("mark_job_as_done"));
        markAsDoneLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(markAsDoneLabel, 0, 9);

        Label workloadActualLabel = new Label(b.getString("actual_work_load"));
        Spinner<Double> workloadActualSpinner = new Spinner<>(job.getWorkloadEstimate(), 1000.0, 0.5, 0.5);

        grid.add(workloadActualLabel, 0, 10);
        grid.add(workloadActualSpinner, 1, 10);

        Button markAsDoneButton = new Button(b.getString("mark_as_done"));
        grid.add(markAsDoneButton, 0, 11);

        markAsDoneButton.setOnAction((ActionEvent e) -> {
            try {
                jobDao.markAsDone(job.getId(), workloadActualSpinner.getValue());
                stage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Text deleteLabel = new Text(b.getString("delete_job"));
        deleteLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(deleteLabel, 0, 12);

        Button deleteButton = new Button(b.getString("delete_job"));
        grid.add(deleteButton, 0, 13);

        deleteButton.setOnAction((ActionEvent e) -> {
            try {
                jobDao.delete(job.getId());
                stage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

}
