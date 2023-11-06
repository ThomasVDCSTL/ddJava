package Database;

import Defensif.Bouclier;
import Defensif.EquipementDefensif;
import Defensif.Philtre;
import Offensif.*;
import Personnages.Characters;
import Personnages.Guerrier;
import Personnages.Magicien;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HeroCRUD extends DatabaseCRUD {

    protected static final HeroCRUD heroInstance =new HeroCRUD();


    public ArrayList<Characters> getHeroList()throws SQLException {
        ArrayList<Characters> heroList = new ArrayList<Characters>();
        Statement stmt = mydb.getConnection().createStatement( );
        ResultSet heroData=stmt.executeQuery("SELECT * FROM hero JOIN partie ON hero.hero_id=partie.hero_id");
        while (heroData.next()){
            Characters myHero;
            if (heroData.getString("hero.type").equals("guerrier")){
                myHero =new Guerrier(heroData.getString("hero.name"));
            }else {
                myHero = new Magicien(heroData.getString("hero.name"));
            }
            myHero.setAttack(heroData.getInt("hero.attack"));
            myHero.setHp(heroData.getInt("hero.hp"));
            myHero.setAtkGear(getOffensiveItemFromID(heroData.getInt("hero.offensiveItemID")));
            myHero.setDefGear(getDefensiveItemFromID(heroData.getInt("hero.defensiveItemID")));
            myHero.setEmplacement(heroData.getInt("hero.emplacement"));
            myHero.setHero_id(heroData.getInt("partie.hero_id"));
            myHero.setPlateau_id(heroData.getInt("partie.plateau_id"));
            myHero.setPlateau(PlateauCRUD.getPlateau(myHero));
            heroList.add(myHero);
        }
        return heroList;
    }
    public void createHero(Characters hero)throws SQLException{
        PreparedStatement pstmt= mydb.getConnection().prepareStatement("INSERT INTO hero (name,type,hp,attack,offensiveItemID,defensiveItemID,emplacement) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,hero.getName());
        pstmt.setString(2,hero.getType());
        pstmt.setInt(3,hero.getHp());
        pstmt.setInt(4,hero.getAttack());
        pstmt.setInt(5,getIDFromOffensiveItem(hero.getAtkGear()));
        pstmt.setInt(6,getIDFromDefensiveItem(hero.getDefGear()));
        pstmt.setInt(7,hero.getEmplacement());
        pstmt.executeUpdate();
        int plateau_id=PlateauCRUD.createPlateau(hero);
        int hero_id=0;
        ResultSet rs = pstmt.getGeneratedKeys();
        while (rs.next()){
            hero_id= (int)rs.getLong(1);
        }
        hero.setHero_id(hero_id);
        hero.setPlateau_id(plateau_id);
        GameCRUD.createPartie(hero);
    }
    public void updateHero(Characters hero) throws SQLException{
        PreparedStatement pstmt = mydb.getConnection().prepareStatement("UPDATE hero SET hp=?,offensiveItemID=?,defensiveItemID=?,emplacement=? WHERE name=?");
        pstmt.setInt(1,hero.getHp());
        pstmt.setInt(2,getIDFromOffensiveItem(hero.getAtkGear()));
        pstmt.setInt(3,getIDFromDefensiveItem(hero.getDefGear()));
        pstmt.setInt(4,hero.getEmplacement());
        pstmt.setString(5, hero.getName());
        pstmt.executeUpdate();
        PlateauCRUD.updatePlateau(hero);
    }
    public static void deleteHero(String name)throws SQLException{
        PreparedStatement pstmt=mydb.getConnection().prepareStatement("DELETE partie.*,hero.*,plateau.* FROM hero JOIN partie ON hero.hero_id=partie.hero_id JOIN plateau ON plateau.plateau_id=partie.plateau_id WHERE name=?");
        pstmt.setString(1, name);
        pstmt.executeUpdate();
    }

    public EquipementDefensif getDefensiveItemFromID(int id){
        switch (id) {
            case 1 -> {return new Bouclier();}
            case 2 -> {return new Philtre();}
            default -> {return null;}
        }
    }
    public int getIDFromDefensiveItem(EquipementDefensif item){
        if (item instanceof Bouclier) {return 1;}
        else if (item instanceof Philtre) {return 2;}
        else{return 0;}
    }
    public EquipementOffensif getOffensiveItemFromID(int id){
        switch (id) {
            case 1 -> {return new Arme();}
            case 2 -> {return new Sort();}
            case 3 ->{return new Massue(0);}
            case 4 ->{return new Eclair(0);}
            case 5 ->{return new Epee(0);}
            case 6 ->{return new BouleDeFeu(0);}
            default -> {return null;}
        }
    }
    public int getIDFromOffensiveItem(EquipementOffensif item){
        return switch (item) {
            case Epee epee -> 5;
            case BouleDeFeu bouleDeFeu -> 6;
            case Massue massue -> 3;
            case Eclair eclair -> 4;
            case Arme arme -> 1;
            case Sort sort -> 2;
            case null, default -> 0;
        };
    }
    public static HeroCRUD getInstance()
    {
        return heroInstance;
    }
}
