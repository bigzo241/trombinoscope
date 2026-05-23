package com.mamadou.trombinoscope;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainController {


    @FXML
    protected void ajouterIndividuForm() throws IOException {
        Stage ajoutForm = new Stage();
        ajoutForm.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ajout-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        AjoutController ajoutController = fxmlLoader.getController();
        ajoutController.setAjoutStage(ajoutForm);
        FieldValidator.errorCounter = 0;

        ajoutForm.setTitle("Formulaire d'ajout");
        ajoutForm.setScene(scene);
        ajoutForm.showAndWait();
        ajoutForm.close();
    }
}