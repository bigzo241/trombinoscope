package com.mamadou.trombinoscope.controller;

import com.mamadou.trombinoscope.FieldValidator;
import com.mamadou.trombinoscope.MainApp;
import com.mamadou.trombinoscope.dataAccess.IndividuDAO;
import com.mamadou.trombinoscope.metier.Individu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private VBox sectionHautVbox;
    @FXML
    private Label nbrIndividu;
    @FXML
    private TextField barreRecherche;
    @FXML
    private FlowPane flowPane;
    private final IndividuDAO individuDAO = new IndividuDAO();

    @FXML
    public void initialize(){
        List<Individu> individus = individuDAO.findAll();
        nbrIndividu.setText(String.valueOf(individus.size()));
        if (!individus.isEmpty()) {
            if (flowPane.getChildren().removeLast() instanceof Button button){
                sectionHautVbox.getChildren().add(button);
            }
            flowPane.getStylesheets().add(String.valueOf(this.getClass().getResource("/com/mamadou/trombinoscope/individu.css")));
            flowPane.getChildren().clear();
            for (Individu individu : individus) {
                HBox hBox = new HBox(10);
                hBox.setPrefWidth(550.0);
                hBox.setPrefHeight(240.0);
                hBox.getStyleClass().add("individu_card");

                Image image = null;
                try {
                    image = new Image(new FileInputStream(individu.getImagePath()), 110, 200, true, false);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                ImageView imageView = new ImageView(image);

                GridPane gridPane = new GridPane();
                ColumnConstraints cc1 = new ColumnConstraints();
                ColumnConstraints cc2 = new ColumnConstraints();
                cc1.setHgrow(Priority.SOMETIMES);
                cc2.setHgrow(Priority.ALWAYS);
                RowConstraints rc1 = new RowConstraints();
                RowConstraints rc2 = new RowConstraints();
                RowConstraints rc3 = new RowConstraints();
                RowConstraints rc4 = new RowConstraints();
                RowConstraints rc5 = new RowConstraints();
                RowConstraints rc6 = new RowConstraints();
                rc1.setVgrow(Priority.SOMETIMES);
                rc2.setVgrow(Priority.SOMETIMES);
                rc3.setVgrow(Priority.SOMETIMES);
                rc4.setVgrow(Priority.SOMETIMES);
                rc5.setVgrow(Priority.SOMETIMES);
                rc6.setVgrow(Priority.SOMETIMES);
                gridPane.addRow(0, new Label("Nom :"), new Label(individu.getNom()));
                gridPane.addRow(1, new Label("Prénom :"), new Label(individu.getPrenom()));
                gridPane.addRow(2, new Label("Date de naissance :"), new Label(individu.getDate().toString()));
                gridPane.addRow(3, new Label("Poste :"), new Label(individu.getPoste()));
                gridPane.addRow(4, new Label("Email :"), new Label(individu.getEmail()));
                gridPane.addRow(5, new Label("Numéro de téléphone : "), new Label(String.valueOf(individu.getNumeroTel())));
                gridPane.getColumnConstraints().addAll(cc1, cc2);
                gridPane.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5, rc6);

                hBox.getChildren().addAll(imageView, gridPane);
                hBox.setAlignment(Pos.CENTER);
                hBox.setFillHeight(true);
                HBox.setHgrow(gridPane, Priority.ALWAYS);

                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItem1 = new MenuItem("Modifier");
                menuItem1.setOnAction(e -> ajouterIndividuForm(individu));
                MenuItem menuItem2 = new MenuItem("Supprimer");
                menuItem2.setOnAction(e -> {
                    individuDAO.delete(individu);
                    this.initialize();
                });
                contextMenu.getItems().addAll(menuItem1, menuItem2);
                hBox.setOnContextMenuRequested(event -> {
                    // Show the menu at the exact mouse cursor coordinates on screen
                    contextMenu.show(hBox, event.getScreenX(), event.getScreenY());
                    // Consume the event to prevent parent containers from handling it too
                    event.consume();
                });

                // 5. Hide the context menu if the user left-clicks the node
                hBox.setOnMouseClicked(event -> {
                    if (contextMenu.isShowing()) {
                        contextMenu.hide();
                    }
                });

                flowPane.getChildren().add(hBox);
            }
        }
    }

    @FXML
    protected void ajouterIndividuForm() throws IOException {
        Stage ajoutForm = new Stage();
        ajoutForm.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ajout-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AjoutController ajoutController = fxmlLoader.getController();
        ajoutController.setAjoutStage(ajoutForm);
        ajoutController.setMainStage(this);
        FieldValidator.errorCounter = 0;

        ajoutForm.setTitle("Formulaire d'ajout");
        ajoutForm.setScene(scene);
        ajoutForm.showAndWait();
    }

    protected void ajouterIndividuForm(Individu individu) {
        Stage ajoutForm = new Stage();
        ajoutForm.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ajout-form.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        AjoutController ajoutController = fxmlLoader.getController();
        ajoutController.setNom(individu.getNom());
        ajoutController.setPrenom(individu.getPrenom());
        ajoutController.setDate_naissance(individu.getDate());
        ajoutController.setPoste(individu.getPoste());
        ajoutController.setEmail(individu.getEmail());
        ajoutController.setTel(individu.getNumeroTel());
        ajoutController.setAjoutStage(ajoutForm);
        ajoutController.setMainStage(this);
        ajoutController.setUpdate(true);
        ajoutController.setKey(individu.getEmail());
        FieldValidator.errorCounter = 0;

        ajoutForm.setTitle("Formulaire d'ajout");
        ajoutForm.setScene(scene);
        ajoutForm.showAndWait();
    }

    @FXML
    public void onRechercher() {
        List<Individu> resultats1 = individuDAO.findByNom(barreRecherche.getText().trim());
        List<Individu> resultats2 = individuDAO.findByPrenom(barreRecherche.getText().trim());
        List<Individu> resultats4 = individuDAO.findByPoste(barreRecherche.getText().trim());
        Individu resultats5 = individuDAO.find(barreRecherche.getText().trim());
        List<Individu> resultats6 = new ArrayList<>();
        if (barreRecherche.getText().matches("([0-9]+(\\p{Blank})?)+")) {
            resultats6 = individuDAO.findByNumero(barreRecherche.getText().trim());
        }

        flowPane.getChildren().clear();

        if (resultats1.isEmpty() && resultats2.isEmpty() && resultats4.isEmpty() && resultats5 == null && resultats6.isEmpty()) {
            Text text = new Text("Aucun résultats");
            text.setStyle("-fx-font-size: 18;"
                    + "-fx-font-family: Arial;");
            Button button = new Button("Retour");
            button.setStyle("-fx-background-color: #003EFF;"
                    + "-fx-text-fill: white;"
                    + "-fx-font-size: 18;"
                    + "-fx-font-family: Arial;");
            button.setOnAction(this::handle);

            flowPane.getChildren().addAll(text, button, new Label(" "));
        } else {
            for (Individu individu : resultats1) {
                flowPane.getChildren().addAll(componetIndividuMaker(individu));
            }
            for (Individu individu : resultats2) {
                flowPane.getChildren().addAll(componetIndividuMaker(individu));
            }
            for (Individu individu : resultats4) {
                flowPane.getChildren().addAll(componetIndividuMaker(individu));
            }

            if (resultats5 != null)
                flowPane.getChildren().addAll(componetIndividuMaker(resultats5));

            for (Individu individu : resultats6) {
                flowPane.getChildren().addAll(componetIndividuMaker(individu));
            }
        }
    }

    private HBox componetIndividuMaker(Individu individu) {
        HBox hBox = new HBox(10);
        hBox.setPrefWidth(550.0);
        hBox.setPrefHeight(240.0);
        hBox.getStyleClass().add("individu_card");

        Image image = null;
        try {
            image = new Image(new FileInputStream(individu.getImagePath()), 110, 200, true, false);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        ImageView imageView = new ImageView(image);

        GridPane gridPane = new GridPane();
        ColumnConstraints cc1 = new ColumnConstraints();
        ColumnConstraints cc2 = new ColumnConstraints();
        cc1.setHgrow(Priority.SOMETIMES);
        cc2.setHgrow(Priority.ALWAYS);
        RowConstraints rc1 = new RowConstraints();
        RowConstraints rc2 = new RowConstraints();
        RowConstraints rc3 = new RowConstraints();
        RowConstraints rc4 = new RowConstraints();
        RowConstraints rc5 = new RowConstraints();
        RowConstraints rc6 = new RowConstraints();
        rc1.setVgrow(Priority.SOMETIMES);
        rc2.setVgrow(Priority.SOMETIMES);
        rc3.setVgrow(Priority.SOMETIMES);
        rc4.setVgrow(Priority.SOMETIMES);
        rc5.setVgrow(Priority.SOMETIMES);
        rc6.setVgrow(Priority.SOMETIMES);
        gridPane.addRow(0, new Label("Nom :"), new Label(individu.getNom()));
        gridPane.addRow(1, new Label("Prénom :"), new Label(individu.getPrenom()));
        gridPane.addRow(2, new Label("Date de naissance :"), new Label(individu.getDate().toString()));
        gridPane.addRow(3, new Label("Poste :"), new Label(individu.getPoste()));
        gridPane.addRow(4, new Label("Email :"), new Label(individu.getEmail()));
        gridPane.addRow(5, new Label("Numéro de téléphone : "), new Label(String.valueOf(individu.getNumeroTel())));
        gridPane.getColumnConstraints().addAll(cc1, cc2);
        gridPane.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5, rc6);

        hBox.getChildren().addAll(imageView, gridPane);
        hBox.setAlignment(Pos.CENTER);
        hBox.setFillHeight(true);
        HBox.setHgrow(gridPane, Priority.ALWAYS);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Modifier");
        menuItem1.setOnAction(e -> {
            ajouterIndividuForm(individu);
        });
        MenuItem menuItem2 = new MenuItem("Supprimer");
        menuItem2.setOnAction(e -> {
            individuDAO.delete(individu);
            this.initialize();
        });
        contextMenu.getItems().addAll(menuItem1, menuItem2);
        hBox.setOnContextMenuRequested(event -> {
            // Show the menu at the exact mouse cursor coordinates on screen
            contextMenu.show(hBox, event.getScreenX(), event.getScreenY());
            // Consume the event to prevent parent containers from handling it too
            event.consume();
        });

        // 5. Hide the context menu if the user left-clicks the node
        hBox.setOnMouseClicked(event -> {
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            }
        });
        return hBox;
    }

    private void handle(ActionEvent e) {
        initialize();
    }
}