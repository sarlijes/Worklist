package ui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.skins.JFXTextFieldSkin;
import com.jfoenix.skins.ValidationPane;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class JobJFXTextField extends JFXTextField {

    RequiredFieldValidator validator;

    public JobJFXTextField(String label) {
        this(label, "", false);
    }

    public JobJFXTextField(String label, String error, boolean required) {

        setPromptText(label);
        setPrefWidth(350);

        skinProperty().addListener(

                // Source of the code below: https://github.com/sshahine/JFoenix/issues/924

                (observable, oldValue, newValue) -> {
                    JFXTextFieldSkin textFieldSkin = ((JFXTextFieldSkin) newValue);
                    ObservableList children = textFieldSkin.getChildren();
                    ValidationPane validationPane = (ValidationPane) children.get(children.size() - 1);
                    validationPane.setTranslateY(-24);
                    StackPane labelStackPane = (StackPane) validationPane.getChildren().get(0);
                    Label innerError = (Label) labelStackPane.getChildren().get(0);
                    StackPane.setAlignment(innerError, Pos.TOP_RIGHT);
                });

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
