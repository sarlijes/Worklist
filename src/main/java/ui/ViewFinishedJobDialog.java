package ui;

import dao.Job;
import dao.JobDao;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ViewFinishedJobDialog extends JobDialog {

    private JobDao jobDao;
    private Job job;

    public ViewFinishedJobDialog(JobDao jobDao, Stage stage, GridPane grid, Job job) {
        super(stage, grid);
        this.jobDao = jobDao;
        this.job = job;
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("View finished job " + job.getId() + " (" + job.getName() + ")");

        customerTextField.setText(job.getCustomer());
        customerTextField.setDisable(true);

        nameTextField.setText(job.getName());
        nameTextField.setDisable(true);

        materialTextField.setText(job.getMaterial());
        materialTextField.setDisable(true);

        detailsTextField.setText(job.getDetails());
        detailsTextField.setDisable(true);

        dueDatePicker.setValue(job.getDueDate());
        dueDatePicker.setDisable(true);

        quantitySpinner.getValueFactory().setValue(job.getQuantity());
        quantitySpinner.setDisable(true);

        workloadEstimateSpinner.getValueFactory().setValue(job.getWorkloadEstimate());
        workloadEstimateSpinner.setDisable(true);

        Button markAsDoneButton = new Button("Mark job as not done");
        grid.add(markAsDoneButton, 0, 8);

        markAsDoneButton.setOnAction((ActionEvent e) -> {
            try {
                jobDao.markAsNotDone(job.getId());
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
