/**
 * Dialog that allows users to view finished jobs and mark them as unfinished if necessary.
 */

package worklist.ui;

import worklist.dao.MaterialDao;
import worklist.entity.Employee;
import worklist.entity.Job;
import worklist.dao.JobDao;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewFinishedJobDialog extends JobDialog {

    private JobDao jobDao;
    private Job job;
    private ResourceBundle b;

    public ViewFinishedJobDialog(JobDao jobDao, MaterialDao materialDao, Stage stage, GridPane grid, Job job,
                                 ResourceBundle b, Employee loggedInEmployee) {
        super(stage, grid, b, loggedInEmployee, materialDao);
        this.jobDao = jobDao;
        this.job = job;
        this.b = b;
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle(b.getString("view_finished_job"));

        customerTextField.setText(job.getCustomer());
        customerTextField.setDisable(true);

        nameTextField.setText(job.getName());
        nameTextField.setDisable(true);

        materialComboBox.getSelectionModel().select(job.getMaterial().getName());
        materialComboBox.setDisable(true);

        detailsTextField.setText(job.getDetails());
        detailsTextField.setDisable(true);

        dueDatePicker.setValue(job.getDueDate());
        dueDatePicker.setDisable(true);

        quantitySpinner.getValueFactory().setValue(job.getQuantity());
        quantitySpinner.setDisable(true);

        workloadEstimateSpinner.getValueFactory().setValue(job.getWorkloadEstimate());
        workloadEstimateSpinner.setDisable(true);

        Button markAsNotFinishedButton = new Button(b.getString("mark_job_as_not_finished"));
        grid.add(markAsNotFinishedButton, 0, 8);

        markAsNotFinishedButton.setOnAction((ActionEvent e) -> {
            try {
                jobDao.markAsNotFinished(job.getId());
                stage.close();
            } catch (SQLException ex) {
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

}
