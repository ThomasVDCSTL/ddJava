package Meta;

import Defensif.*;
import Offensif.*;
import Personnages.Characters;
import Personnages.Guerrier;
import Personnages.Magicien;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCRUD {
    Connection mydb;
    public DatabaseCRUD() {
        try {
            Driver myDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(myDriver);
            String URL = "jdbc:mysql://localhost:3306/mydb_java";
            mydb = DriverManager.getConnection(URL, "root2", "");
        } catch (SQLException e) {
            System.out.println("Error: unable to load driver class! dès le début");
            System.exit(1);
        }
    }

    public ArrayList<Characters> getHeroList()throws SQLException{
        Statement stmt = null;
        ResultSet heroData = null;
        ArrayList<Characters> heroList = new ArrayList<Characters>();
        stmt = mydb.createStatement( );
        heroData=stmt.executeQuery("SELECT * FROM hero");
        while (heroData.next()){
            Characters myHero;
            String heroName = heroData.getString("name");
            String type = heroData.getString("type");
            int hp = heroData.getInt("hp");
            int attack = heroData.getInt("attack");
            EquipementOffensif offensiveItem = getOffensiveItemFromID(heroData.getInt("offensiveItemID"));
            EquipementDefensif defensiveItem = getDefensiveItemFromID(heroData.getInt("defensiveItemID"));
            int emplacement=heroData.getInt("emplacement");
            if (type.equals("guerrier")){
                myHero =new Guerrier(heroName);
            }else {
                myHero = new Magicien(heroName);
            }
            myHero.setAttack(attack);
            myHero.setHp(hp);
            myHero.setAtkGear(offensiveItem);
            myHero.setDefGear(defensiveItem);
            myHero.setEmplacement(emplacement);
            heroList.add(myHero);
        }
        return heroList;
    }
    public void createHero(Characters hero)throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("INSERT INTO hero (name,type,hp,attack,offensiveItemID,defensiveItemID,emplacement) VALUES (?,?,?,?,?,?,?)");
        pstmt.setString(1,hero.getName());
        pstmt.setString(2,hero.getType());
        pstmt.setInt(3,hero.getHp());
        pstmt.setInt(4,hero.getAttack());
        pstmt.setInt(5,getIDFromOffensiveItem(hero.getAtkGear()));
        pstmt.setInt(6,getIDFromDefensiveItem(hero.getDefGear()));
        pstmt.setInt(7,hero.getEmplacement());
        pstmt.executeUpdate();
    }
    public void updateHero(Characters hero) throws SQLException{
        PreparedStatement pstmt = mydb.prepareStatement("UPDATE hero SET hp=?,offensiveItemID=?,defensiveItemID=?,emplacement=? WHERE name=?");
        pstmt.setInt(1,hero.getHp());
        pstmt.setInt(2,getIDFromOffensiveItem(hero.getAtkGear()));
        pstmt.setInt(3,getIDFromDefensiveItem(hero.getDefGear()));
        pstmt.setInt(4,hero.getEmplacement());
        pstmt.setString(5, hero.getName());
        pstmt.executeUpdate();
    }
    public void deleteHero(String name)throws SQLException{
        PreparedStatement pstmt=mydb.prepareStatement("DELETE FROM hero WHERE name=?");
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
        if (item instanceof Epee) {return 5;}
        else if (item instanceof BouleDeFeu) {return 6;}
        else if (item instanceof Massue) {return 3;}
        else if (item instanceof Eclair) {return 4;}
        else if (item instanceof Arme) {return 1;}
        else if (item instanceof Sort) {return 2;}
        else{return 0;}
    }

}
