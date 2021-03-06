/**
 * A dialog that allows users to manage a job by editing it, marking it as finished or deleting it.
 */

package worklist.ui;

import worklist.dao.MaterialDao;
import worklist.entity.Employee;
import worklist.entity.Job;
import worklist.dao.JobDao;
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

public class ManageJobDialog extends JobDialog {

    private JobDao jobDao;
    private Job job;
    private ResourceBundle b;

    public ManageJobDialog(JobDao jobDao, MaterialDao materialDao, Stage stage, GridPane grid, Job job, ResourceBundle b,
                           Employee loggedInEmployee) {
        super(stage, grid, b, loggedInEmployee, materialDao);
        this.jobDao = jobDao;
        this.job = job;
        this.b = b;
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle(b.getString("edit_job") + " (" + job.getId() + " ," + job.getName() + ")");

        Text inputLabel = new Text(b.getString("edit_job"));
        inputLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(inputLabel, 0, 0);

        customerTextField.setText(job.getCustomer());
        nameTextField.setText(job.getName());
        materialComboBox.getSelectionModel().select(job.getMaterial().getName());

        detailsTextField.setText(job.getDetails());
        dueDatePicker.setValue(job.getDueDate());
        quantitySpinner.getValueFactory().setValue(job.getQuantity());
        workloadEstimateSpinner.getValueFactory().setValue(job.getWorkloadEstimate());

        Button saveButton = new Button(b.getString("save_changes"));
        grid.add(saveButton, 0, 8);

        saveButton.setOnAction((ActionEvent e) -> {

            Job newJob = null;
            try {
                newJob = new Job(nameTextField.getText(), dueDatePicker.getValue(), quantitySpinner.getValue(),
                        materialDao.readByName(materialComboBox.getValue().toString()), workloadEstimateSpinner.getValue(),
                        detailsTextField.getText(), customerTextField.getText(), job.getCreator());
            } catch (SQLException exception) {
            }

            try {
                if (customerTextField.validate() && nameTextField.validate()) {
                    jobDao.update(newJob, job.getId());
                    stage.close();
                }
            } catch (SQLException ex) {
            }
        });

        Text markAsFinishedLabel = new Text(b.getString("mark_job_as_finished"));
        markAsFinishedLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(markAsFinishedLabel, 0, 9);

        Label workloadActualLabel = new Label(b.getString("actual_work_load"));
        Spinner<Double> workloadActualSpinner = new Spinner<>(job.getWorkloadEstimate(), 1000.0, 0.5, 0.5);

        grid.add(workloadActualLabel, 0, 10);
        grid.add(workloadActualSpinner, 1, 10);

        Button markAsFinishedButton = new Button(b.getString("mark_as_finished"));
        grid.add(markAsFinishedButton, 0, 11);

        markAsFinishedButton.setOnAction((ActionEvent e) -> {
            try {
                jobDao.markAsFinished(job.getId(), workloadActualSpinner.getValue());
                stage.close();
            } catch (SQLException ex) {
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
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

}
