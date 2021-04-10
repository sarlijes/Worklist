package ui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class JobJFXTextField extends JFXTextField {

    RequiredFieldValidator validator;

    public JobJFXTextField(String label, String error, boolean required) {

        setPromptText(label);
        setLabelFloat(true);

        setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));

        if (required) {
            validator = new RequiredFieldValidator();
            validator.setMessage(error);

            getValidators().add(validator);
            focusedProperty().addListener((o, oldValue, newValue) -> {
                if (!newValue) validate();
            });
        }
    }
}
