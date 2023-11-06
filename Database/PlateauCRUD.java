package Database;

import Offensif.BouleDeFeu;
import Offensif.Eclair;
import Offensif.Epee;
import Offensif.Massue;
import Personnages.Characters;
import Personnages.Enemy;
import Tiles.EmptyTile;
import Tiles.PotionG;
import Tiles.PotionM;
import Tiles.Tile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class PlateauCRUD extends DatabaseCRUD{

    protected static final PlateauCRUD plateauInstance = new PlateauCRUD();
    public static int createPlateau(Characters player)throws SQLException {
        PreparedStatement pstmt= mydb.getConnection().prepareStatement("INSERT INTO plateau (gobelin,sorcier,dragon,massue,epee,eclair,boule_de_feu,potion_m,potion_g,vide) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        String[] tileList=getTileList(player.getPlateau());
        for (int i = 0; i < 10; i++) {
            pstmt.setString(i+1,tileList[i]);
        }
        pstmt.executeUpdate();
        ResultSet rs= pstmt.getGeneratedKeys();
        int id=0;
        while (rs.next()){
            id = (int) rs.getLong(1);
        }
        return id;
    }
    public static ArrayList<Tile> getPlateau(Characters player) throws SQLException{
        PreparedStatement pstmt= mydb.getConnection().prepareStatement("SELECT * FROM plateau WHERE plateau_id=?");
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
    public static void updatePlateau(Characters player)throws SQLException{
        PreparedStatement pstmt= mydb.getConnection().prepareStatement("UPDATE plateau SET gobelin=?,sorcier=?,dragon=?,massue=?,epee=?,eclair=?,boule_de_feu=?,potion_m=?,potion_g=?,vide=? WHERE plateau_id=?");
        String[] tileList=getTileList(player.getPlateau());
        for (int i = 0; i < 10; i++) {
            pstmt.setString(i+1,tileList[i]);
        }
        pstmt.setInt(11, player.getPlateau_id());
        pstmt.executeUpdate();
    }
    public static void deletePlateau(Characters player)throws SQLException{
        PreparedStatement pstmt=mydb.getConnection().prepareStatement("DELETE FROM plateau WHERE plateau_id=?");
        pstmt.setInt(1,player.getPlateau_id());
        pstmt.executeUpdate();
    }
    public static ArrayList<Tile> getArrayList(String[] enemyList){
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
            if (gobelins.length>0&&gobelins[0]==i){
                al.add(new Enemy("Gobelin", 6, 1, i));
                gobelins= Arrays.copyOfRange(gobelins,1,gobelins.length);
            }else if (sorciers.length>0&&sorciers[0]==i){
                al.add(new Enemy("Sorcier", 9, 2, i));
                sorciers=Arrays.copyOfRange(sorciers,1,sorciers.length);
            }else if (dragons.length>0&&dragons[0]==i){
                al.add(new Enemy("Dragon", 15, 4, i));
                dragons=Arrays.copyOfRange(dragons,1,dragons.length);
            }else if (massues.length>0&&massues[0]==i){
                al.add(new Massue(i));
                massues=Arrays.copyOfRange(massues,1,massues.length);
            }else if (epees.length>0&&epees[0]==i){
                al.add(new Epee(i));
                epees=Arrays.copyOfRange(epees,1,epees.length);
            }else if (eclairs.length>0&&eclairs[0]==i){
                al.add(new Eclair(i));
                eclairs=Arrays.copyOfRange(eclairs,1,eclairs.length);
            }else if (boules.length>0&&boules[0]==i){
                al.add(new BouleDeFeu(i));
                boules=Arrays.copyOfRange(boules,1,boules.length);
            }else if (potionsM.length>0&&potionsM[0]==i){
                al.add(new PotionM(i));
                potionsM=Arrays.copyOfRange(potionsM,1,potionsM.length);
            }else if (potionsG.length>0&&potionsG[0]==i){
                al.add(new PotionG(i));
                potionsG=Arrays.copyOfRange(potionsG,1,potionsG.length);
            }else if (vides.length>0&&vides[0]==i) {
                al.add(new EmptyTile(i));
                vides=Arrays.copyOfRange(vides,1,vides.length);
            }
        }
        return al;
    }

    public static int[] toIntArray(String[] s){
        int[] result = new int[s.length-1];
        for (int i = 1; i < s.length; i++) {
            result[i-1] = Integer.parseInt(s[i]);
        }
        return result;
    }

    public static String[] getTileList(ArrayList<Tile> arrayList){
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
    public static PlateauCRUD getInstance()
    {
        return plateauInstance;
    }
}
