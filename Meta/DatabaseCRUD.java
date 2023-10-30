package Meta;

import Defensif.*;
import Offensif.*;
import Personnages.*;
import Tiles.EmptyTile;
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
            String URL = "jdbc:mysql://localhost:3306/mydb_java_8";
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
            myHero.setPlateau(getPlateau(myHero));
            heroList.add(myHero);
        }
        return heroList;
    }
    public void createHero(Characters hero)throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("INSERT INTO hero (name,type,hp,attack,offensiveItemID,defensiveItemID,emplacement) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,hero.getName());
        pstmt.setString(2,hero.getType());
        pstmt.setInt(3,hero.getHp());
        pstmt.setInt(4,hero.getAttack());
        pstmt.setInt(5,getIDFromOffensiveItem(hero.getAtkGear()));
        pstmt.setInt(6,getIDFromDefensiveItem(hero.getDefGear()));
        pstmt.setInt(7,hero.getEmplacement());
        pstmt.executeUpdate();
        int plateau_id=createPlateau(hero);
        int hero_id=0;
        ResultSet rs = pstmt.getGeneratedKeys();
        while (rs.next()){
           hero_id= (int)rs.getLong(1);
        }
        hero.setHero_id(hero_id);
        hero.setPlateau_id(plateau_id);
        createPartie(hero);
    }
    public void updateHero(Characters hero) throws SQLException{
        PreparedStatement pstmt = mydb.prepareStatement("UPDATE hero SET hp=?,offensiveItemID=?,defensiveItemID=?,emplacement=? WHERE name=?");
        pstmt.setInt(1,hero.getHp());
        pstmt.setInt(2,getIDFromOffensiveItem(hero.getAtkGear()));
        pstmt.setInt(3,getIDFromDefensiveItem(hero.getDefGear()));
        pstmt.setInt(4,hero.getEmplacement());
        pstmt.setString(5, hero.getName());
        pstmt.executeUpdate();
        updatePlateau(hero);
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
    public int createPlateau(Characters player)throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("INSERT INTO plateau (gobelin,sorcier,dragon,massue,epee,eclair,boule_de_feu,potion_m,potion_g,vide) VALUES (?,?,?,?,?,?,?,?,?,?) UNION SELECT id FROM plateau WHERE vide=?",Statement.RETURN_GENERATED_KEYS);
        String[] tileList=getTileList(player.getPlateau());
        for (int i = 0; i < 10; i++) {
            pstmt.setString(i+1,tileList[i]);
        }
        pstmt.setString(10,tileList[9]);
        pstmt.executeUpdate();
        ResultSet rs= pstmt.getGeneratedKeys();
        int id=0;
        while (rs.next()){
            id = (int) rs.getLong(1);
        }
        return id;
    }
    public ArrayList<Tile> getPlateau(Characters player) throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("SELECT * FROM plateau WHERE plateau_id=?");
        pstmt.setInt(1,player.getPlateau_id());
        ResultSet rs =pstmt.executeQuery();
        String [] list =new String[10];
        while (rs.next()){
            list[0]= rs.getString("gobelin");
            list[1]= rs.getString("sorcier");
            list[2]= rs.getString("dragon");
            list[3]= rs.getString("massue");
            list[4]= rs.getString("epee");
            list[5]= rs.getString("eclair");
            list[6]= rs.getString("boule_de_feu");
            list[7]= rs.getString("potion_m");
            list[8]= rs.getString("potion_g");
            list[9]= rs.getString("vide");
        }
        return getArrayList(list);
    }
    public void updatePlateau(Characters player)throws SQLException{
        PreparedStatement pstmt= mydb.prepareStatement("UPDATE INTO plateau SET gobelin=?,sorcier=?,dragon=?,massue=?,epee=?,eclair=?,boule_de_feu=?,potion_m=?,potion_g=?,vide=? WHERE plateau_id=?");
        String[] tileList=getTileList(player.getPlateau());
        for (int i = 0; i < 10; i++) {
            pstmt.setString(i+1,tileList[i]);
        }
        pstmt.setInt(11, player.getPlateau_id());
        pstmt.executeUpdate();
    }
    public void deletePlateau(Characters player)throws SQLException{
        PreparedStatement pstmt=mydb.prepareStatement("DELETE FROM plateau WHERE plateau_id=?");
        pstmt.setInt(1,player.getPlateau_id());
        pstmt.executeUpdate();
    }
    public ArrayList<Tile> getArrayList(String[] enemyList){
        ArrayList<Tile> al = new ArrayList<Tile>();
        int[] gobelins = toIntArray(enemyList[0].split(" "));
        int[] sorciers = toIntArray(enemyList[1].split(" "));
        int[] dragons = toIntArray(enemyList[2].split(" "));
        int[] massues = toIntArray(enemyList[3].split(" "));
        int[] epees = toIntArray(enemyList[4].split(" "));
        int[] eclairs = toIntArray(enemyList[5].split(" "));
        int[] boules = toIntArray(enemyList[6].split(" "));
        int[] potionsM = toIntArray(enemyList[7].split(" "));
        int[] potionsG = toIntArray(enemyList[8].split(" "));
        int[] vides = toIntArray(enemyList[9].split(" "));
        for (int i = 0; i < 64; i++) {
            if (gobelins[0]==i){
                al.add(new Enemy("Gobelin", 6, 1, i));
            }else if (sorciers[0]==i){
                al.add(new Enemy("Sorcier", 9, 2, i));
            }else if (dragons[0]==i){
                al.add(new Enemy("Dragon", 15, 4, i));
            }else if (massues[0]==i){
                al.add(new Massue(i));
            }else if (epees[0]==i){
                al.add(new Epee(i));
            }else if (eclairs[0]==i){
                al.add(new Eclair(i));
            }else if (boules[0]==i){
                al.add(new BouleDeFeu(i));
            }else if (potionsM[0]==i){
                al.add(new PotionM(i));
            }else if (potionsG[0]==i){
                al.add(new PotionG(i));
            }else if (vides[0]==i) {
                al.add(new EmptyTile(i));
            }
        }
        return al;
    }

    public int[] toIntArray(String[] s){
        int[] result = new int[s.length];
        for (int i = 1; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
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
    /*---------------------------Méthodes pour la partie---------------------------*/
    public void createPartie(Characters player) throws SQLException {
        PreparedStatement pstmt=mydb.prepareStatement("INSERT INTO partie (hero_id,plateau_id) VALUES (?,?)");
        pstmt.setInt(1,player.getHero_id());
        pstmt.setInt(2,player.getPlateau_id());
        pstmt.executeUpdate();
    }
    public void deletePartie(Characters player)throws SQLException{
        PreparedStatement pstmt =mydb.prepareStatement("DELETE FROM partie WHERE plateau_id = ?");
        pstmt.setInt(1, player.getPlateau_id());
        pstmt.executeUpdate();
        deletePlateau(player);
    }

}
