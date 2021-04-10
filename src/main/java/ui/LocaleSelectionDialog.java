package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleSelectionDialog extends Application {

    Stage stage = new Stage();
    VBox vbox = new VBox();

    private Locale locale = new Locale("fi", "FI");
    ResourceBundle b = ResourceBundle.getBundle("Label", locale);

    @Override
    public void start(Stage stage) {
        ObservableList<String> locales =
                FXCollections.observableArrayList(
                        "fi_FI",
                        "en_EN"
                );
        final ComboBox comboBox = new ComboBox(locales);
        comboBox.setPromptText(b.getString("select_language"));

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.locale = new Locale(newValue.toString());
            stage.close();
        });

        vbox.getChildren().add(comboBox);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
