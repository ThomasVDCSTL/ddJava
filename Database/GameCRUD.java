package Database;

import Meta.Game;
import Personnages.Characters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameCRUD extends DatabaseCRUD{
    protected static final GameCRUD partieInstance =new GameCRUD();

    public static void createPartie(Characters player) throws SQLException {
        PreparedStatement pstmt=mydb.getConnection().prepareStatement("INSERT INTO partie (hero_id,plateau_id) VALUES (?,?)");
        pstmt.setInt(1,player.getHero_id());
        pstmt.setInt(2,player.getPlateau_id());
        pstmt.executeUpdate();
    }
    public static void deletePartie(Characters player)throws SQLException{
        PreparedStatement pstmt =mydb.getConnection().prepareStatement("DELETE FROM partie WHERE plateau_id = ?");
        pstmt.setInt(1, player.getPlateau_id());
        pstmt.executeUpdate();
        PlateauCRUD.deletePlateau(player);

    }
    public static GameCRUD getInstance()
    {
        return partieInstance;
    }
}
