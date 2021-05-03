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

public class CreateNewJobDialog extends JobDialog {

    private JobDao jobDao;
    private ResourceBundle b;

    public CreateNewJobDialog(JobDao jobDao, MaterialDao materialDao, Stage stage, GridPane grid, ResourceBundle b,
                              Employee loggedInEmployee) {
        super(stage, grid, b, loggedInEmployee, materialDao);
        this.jobDao = jobDao;
        this.b = b;
    }

    public void start(Stage stage) {

        stage.setTitle(b.getString("insert_new_job"));

        Text inputLabel = new Text(b.getString("insert_new_job"));
        inputLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(inputLabel, 0, 0);

        Button saveButton = new Button(b.getString("save"));
        grid.add(saveButton, 0, 8);

        //Material m = materialComboBox.getValue();

        saveButton.setOnAction((ActionEvent e) -> {

            Job job = null;

            try {
                job = new Job(nameTextField.getText(), dueDatePicker.getValue(), quantitySpinner.getValue(),
                        materialDao.readByName(materialComboBox.getValue().toString()),
                        workloadEstimateSpinner.getValue(), detailsTextField.getText(),
                        customerTextField.getText(), loggedInEmployee);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            try {
                if (customerTextField.validate() && nameTextField.validate()) {
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
