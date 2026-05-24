package dataAccess;

import com.mamadou.trombinoscope.Individu;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndividuDAO extends DAO<Individu>{
    @Override
    public Individu find(long id) {
        Individu individu = new Individu();
        try {
            ResultSet result = this.connect
                    .createStatement().executeQuery(
                            "SELECT * FROM individu WHERE id = " + id
                    );
            if(result.first())
                individu = new Individu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        return individu;
    }

    @Override
    public void create(Individu individu) {
        try (PreparedStatement preStat = this.connect.prepareStatement("insert into individu values(?, ?, ?, ?, ?, ?, ?)")){
            preStat.setString(1, individu.getNom());
            preStat.setString(2, individu.getPrenom());
            preStat.setDate(3, Date.valueOf(individu.getDate()));
            preStat.setString(4, individu.getPoste());
            preStat.setString(5, individu.getEmail());
            preStat.setInt(6, individu.getNumeroTel());
            preStat.setString(7, individu.getImagePath());
            int t = preStat.executeUpdate();
            if(t==1)
                System.out.println("insertion dans la base de données réussie");
            else
                System.out.println("Echec de l'insertion");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Individu update(Individu individu) {
        return null;
    }

    @Override
    public void delete(Individu individu) {

    }
}
