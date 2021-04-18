package ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;

public class LocaleSelectionDialog extends BaseView {

    Stage stage;
    VBox box = new VBox();

    private Locale locale = new Locale("fi", "FI");

    public LocaleSelectionDialog(Stage stage) {
        this.stage = stage;

        stage.setTitle("select_language");
        box.setPadding(new Insets(50));

        final ComboBox localeSelector = new ComboBox(FXCollections.observableArrayList("fi", "en"));
        localeSelector.promptTextProperty().bind(langProperty().bind("select_language"));

        localeSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.locale = new Locale(newValue.toString(), "FI");
            langProperty().changeLocale(this.locale);
            stage.close();
        });

        box.getChildren().add(localeSelector);
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    public void start(Stage stage) {

    }

}
