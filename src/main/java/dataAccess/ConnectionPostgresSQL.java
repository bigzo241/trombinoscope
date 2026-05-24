package dataAccess;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgresSQL {
    /**
     * URL de connexion
     */
    private static String url = "jdbc:mysql://localhost:3306/trombinoscope";
    /**
     * Nom du user
     */
    private static String user = "root";
    /**
     * Mot de passe du user
     */
    private static String passwd = "root@MySQL#700";
    /**
     * Objet Connexion
     */
    private static Connection connect;
    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
     * @return
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
