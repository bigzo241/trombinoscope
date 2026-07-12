package com.mamadou.trombinoscope.controller;

import com.mamadou.trombinoscope.service.FieldValidator;
import com.mamadou.trombinoscope.metier.Individu;
import com.mamadou.trombinoscope.dataAccess.IndividuDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class AjoutController {
    FieldValidator validator = new FieldValidator();
    IndividuDAO individuDAO = new IndividuDAO();
    private boolean update = false;
    private String key = null;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker date_naissance;
    private LocalDate olddate = LocalDate.now();
    @FXML
    private TextField poste;
    @FXML
    private TextField email;
    @FXML
    private TextField tel;
    @FXML
    private Label imageName;
    @FXML
    private Hyperlink fileChooserButton;
    private File image;
    private Stage ajoutStage;
    private MainController mainController;
    private String oldname = "Invalide";

    public void setAjoutStage(Stage stage){
        this.ajoutStage = stage;
    }

    public void setMainStage(MainController controller){
        this.mainController = controller;
    }

    @FXML
    public void enregistrer(){
        System.out.println("Validation des champs");
        validator.validate(nom, "(\\p{Alpha})+");
        validator.validate(prenom, "(\\p{Alpha}(\\p{Blank})?)+");
        validator.validate(poste, "(\\p{Alpha})+");
        validator.validate(email, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        validator.validate(tel, "([0-9]+(\\p{Blank})?)+");

        if (date_naissance.getValue() == null) {
            validator.setVisuelError(date_naissance);
        } else {
            date_naissance.setStyle("all: unset;");
        }

        if (olddate==null && date_naissance.getValue()!=null)
            FieldValidator.errorCounter--;
        else if (olddate!=null && date_naissance.getValue()==null) {
            FieldValidator.errorCounter++;
        }
        olddate = date_naissance.getValue();

        if (image != null ){
            fileChooserButton.setStyle("all: unset;");
        } else {
            validator.setVisuelError(fileChooserButton);
        }

        if(!oldname.isEmpty() && imageName.getText().isEmpty())
            FieldValidator.errorCounter++;
        else if (oldname.isEmpty() && !imageName.getText().isEmpty()) {
            if (FieldValidator.errorCounter>0)
                FieldValidator.errorCounter--;
        }
        oldname = imageName.getText();

        System.out.println("Nombre d'erreur " + FieldValidator.errorCounter);

        if (FieldValidator.errorCounter==0){
            System.out.println("Enregistrement/Mise à jour");
            Individu individu = new Individu();
            individu.setNom(nom.getText());
            individu.setPrenom(prenom.getText());
            individu.setDate(date_naissance.getValue());
            individu.setPoste(poste.getText());
            individu.setEmail(email.getText());
            individu.setNumeroTel(Integer.parseInt(tel.getText()));
            individu.setImagePath(image.getPath());

            if (update)
                individuDAO.update(individu, key);
            else
                individuDAO.create(individu);

            System.out.println("Les information suivantes : \n" +
                    "Nom : " + individu.getNom() + "\n" +
                    "Prénom : " + individu.getPrenom() + "\n" +
                    "Date de naissance : " + individu.getDate() + "\n" +
                    "Poste : " + individu.getPoste() + "\n" +
                    "Email : " + individu.getEmail() + "\n" +
                    "Numéro de téléphone : " + individu.getNumeroTel() + "\n" +
                    "Image : " + individu.getImagePath() + "\n");
            ajoutStage.close();
            mainController.initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir des informations correctes s'il vous plait");
            alert.showAndWait();
        }
    }

    @FXML
    public void choisirFichier() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Sélectionnez une image");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png"));

        image = fileChooser.showOpenDialog(ajoutStage);
        oldname = imageName.getText();
        if(image != null) {
            System.out.println("le fichier sélectionné est : " + image);
            imageName.setText(image.getName());
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }

    public void setNom(String nom) {
        this.nom.setText(nom);
    }

    public void setPrenom(String prenom) {
        this.prenom.setText(prenom);
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance.setValue(date_naissance);
    }

    public void setPoste(String poste) {
        this.poste.setText(poste);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public void setTel(int tel) {
        this.tel.setText(String.valueOf(tel));
    }

    public void setUpdate(boolean bool){
        this.update = bool;
    }

    public void setKey(String key) {
        this.key = key;
    }
}