package com.mamadou.trombinoscope;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {


    @FXML
    protected void ajouterIndividuForm() throws IOException {
        Stage ajoutForm = new Stage();
        ajoutForm.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ajout-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ajoutForm.setTitle("Formulaire d'ajout");
        ajoutForm.setScene(scene);
        ajoutForm.showAndWait();
    }
}