package com.mamadou.trombinoscope;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class FieldValidator {
    public static int errorCounter = 0;

    public FieldValidator() {

    }

    public void validate(TextField textField, String regex) {
        System.out.println(textField.getPromptText());
        String oldPromptext = textField.getPromptText();
        if (textField.getText().matches(regex)) {
            textField.setStyle("all: unset;");
            textField.setPromptText("");
        } else {
            setVisuelError(textField);
            textField.setText("");
            textField.setPromptText("Invalide");
        }

        if (oldPromptext.isEmpty() && textField.getPromptText().equals("Invalide")) {
            errorCounter++;
        } else if (oldPromptext.equals("Invalide") && textField.getPromptText().isEmpty())
            if(errorCounter>0)
                errorCounter--;


            System.out.println("Nombre d'erreur : " + errorCounter);
    }

    public void setVisuelError(Control control) {
        control.setStyle("-fx-border-color: red;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 0;"
                + "-fx-border-insets: 0;"
                + "-fx-border-style: solid;");
    }
}
