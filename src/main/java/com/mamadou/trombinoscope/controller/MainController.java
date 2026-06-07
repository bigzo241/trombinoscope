package com.mamadou.trombinoscope.controller;

import com.mamadou.trombinoscope.FieldValidator;
import com.mamadou.trombinoscope.MainApp;
import com.mamadou.trombinoscope.dataAccess.IndividuDAO;
import com.mamadou.trombinoscope.metier.Individu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private VBox sectionHautVbox;
    @FXML
    private Label nbrIndividu;
    @FXML
    private FlowPane flowPane;

    @FXML
    public void initialize(){
        IndividuDAO individuDAO = new IndividuDAO();
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
                menuItem1.setOnAction(e -> individuDAO.update(individu));
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
}