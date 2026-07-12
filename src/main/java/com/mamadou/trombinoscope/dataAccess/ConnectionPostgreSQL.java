package com.mamadou.trombinoscope.dataAccess;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgreSQL {
    /**
     * URL de connexion
     */
    private static final String url = "jdbc:postgresql://localhost:5432/trombinoscope";
    /**
     * Nom du user
     */
    private static final String user = "postgres";
    /**
     * Mot de passe du user
     */
    private static final String passwd = "admin@user#700?";
    /**
     * Objet Connexion
     */
    private static Connection connect;
    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
     * @return Objet Connection representant la connexion à la base de données
     */
    public static Connection getInstance(){
        // connexion à la base de données
        System.out.println("Connexion à la base de données");
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Echec de la connexion à la base de données. Veuillez reéssayer");
                alert.showAndWait();
            }
        }
        return connect;
    }
}
