package Meta;

import Defensif.*;
import Offensif.*;
import Personnages.*;
import Tiles.PotionG;
import Tiles.PotionM;
import Tiles.Tile;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DatabaseCRUD {
    private Connection mydb;
    public DatabaseCRUD() {
        try {
            Driver myDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(myDriver);
            String URL = "jdbc:mysql://localhost:3306/mydb_java";
            this.mydb = DriverManager.getConnection(URL, "root", "root");
        } catch (SQLException e) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
    }
    /*---------------------------Méthodes pour le player---------------------------*/
    public ArrayList<Characters> getHeroList()throws SQLException{
        ArrayList<Characters> heroList = new ArrayList<Characters>();
        Statement stmt = mydb.createStatement( );
        ResultSet heroData=stmt.executeQuery("SELECT * FROM hero");
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
        createPlateau(hero);
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

    /*---------------------------Méthodes pour le plateau---------------------------*/
    public void createPlateau(Characters player)throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("INSERT INTO plateau (gobelin,sorcier,dragon,massue,epee,eclair,boule_de_feu,potion_m,potion_g,vide) VALUES (?,?,?,?,?,?,?,?,?,?)");
        String[] tileList=getTileList(player.getPlateau());
        for (int i = 0; i < 10; i++) {
            pstmt.setString(i+1,tileList[i]);
        }
        pstmt.executeUpdate();
    }
    public ArrayList<Tile> getPlateau(Characters player) throws SQLException{
        ArrayList<Tile> arrayList =new ArrayList<Tile>();
        PreparedStatement pstmt= mydb.prepareStatement("SELECT * FROM (plateau INNER JOIN partie ON plateau.id=partie.plateau_id) INNER JOIN hero ON partie.hero_id=hero.id WHERE hero.name=?");
        pstmt.setString(1,player.getName());
        return arrayList;
    }
    public void updatePlateau(Characters player)throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("UPDATE INTO (plateau INNER JOIN partie ON plateau.id=partie.plateau_id) INNER JOIN hero ON partie.hero_id=hero.id SET gobelin=?,sorcier=?,dragon=?,massue=?,epee=?,eclair=?,boule_de_feu=?,potion_m=?,potion_g=?,vide=? WHERE hero.name=?");
        String[] tileList=getTileList(player.getPlateau());
        for (int i = 0; i < 10; i++) {
            pstmt.setString(i+1,tileList[i]);
        }
        pstmt.setString(10, player.getName());
        pstmt.executeUpdate();
    }
    public void deletePlateau(Characters player)throws SQLException{
        PreparedStatement pstmt=mydb.prepareStatement("DELETE FROM (plateau INNER JOIN partie ON plateau.id=partie.plateau_id) INNER JOIN hero ON partie.hero_id=hero.id WHERE hero.name=?");
        pstmt.setString(1,player.getName());
        pstmt.executeUpdate();
    }

    public String[] getTileList(ArrayList<Tile> arrayList){
        String [] rep = new String[10];
        for (int i = 0; i < 10; i++) {
            rep[i]="";
        }
        for (Tile cases : arrayList){
            if (cases instanceof Enemy) {
                if (((Enemy) cases).getName().equals("Gobelin")){
                    rep[0]+=(" "+(cases).getId());
                } else if (((Enemy) cases).getName().equals("Sorcier")) {
                    rep[1]+=(" "+(cases).getId());
                }else if (((Enemy) cases).getName().equals("Dragon")){
                    rep[2]+=(" "+(cases).getId());
                }
            }else if (cases instanceof Massue){
                rep[3]+=(" "+(cases).getId());
            }else if (cases instanceof Epee){
                rep[4]+=(" "+(cases).getId());
            }else if (cases instanceof Eclair){
                rep[5]+=(" "+(cases).getId());
            }else if (cases instanceof BouleDeFeu){
                rep[6]+=(" "+(cases).getId());
            }else if (cases instanceof PotionM){
                rep[7]+=(" "+(cases).getId());
            }else if (cases instanceof PotionG){
                rep[8]+=(" "+(cases).getId());
            }else{
                rep[9]+=(" "+(cases).getId());
            }
        }
        return rep;
    }

}
