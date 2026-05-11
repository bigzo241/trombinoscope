package com.mamadou.trombinoscope;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AjoutController {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker date;
    @FXML
    private TextField poste;
    @FXML
    private TextField email;
    @FXML
    private TextField tel;
    @FXML
    private Label imageName;

    private File image;
    private Stage ajoutStage;

    public void setAjoutStage(Stage stage){
        this.ajoutStage = stage;
    }

    @FXML
    public void enregistrer(){
        System.out.println("Enregistrement");
        Individu individu = new Individu();
        individu.setNom(nom.getText());
        individu.setPrenom(prenom.getText());
        individu.setDate(date.getValue());
        individu.setPoste(poste.getText());
        individu.setEmail(email.getText());
        individu.setNumeroTel(Integer.parseInt(tel.getText()));
        individu.setImagePath(image.getPath());

        System.out.println("Les information suivantes : \n" +
                "Nom : " + individu.getNom() + "\n" +
                "Prénom : " + individu.getPrenom() + "\n" +
                "Date de naissance : " + individu.getDate() + "\n" +
                "Poste : " + individu.getPoste() + "\n" +
                "Email : " + individu.getEmail() + "\n" +
                "Numéro de téléphone : " + individu.getNumeroTel() + "\n" +
                "Image : " + individu.getImagePath() + "\n");
    }

    @FXML
    public void choisirFichier() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Sélectionnez une image");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png"));

        image = fileChooser.showOpenDialog(ajoutStage);
        if(image != null) {
            System.out.println("le fichier sélectionné est : " + image);
            imageName.setText(image.getName());
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }
}
