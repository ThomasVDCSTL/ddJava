package Offensif;
import Tiles.Tile;

public class Arme extends EquipementOffensif implements Tile {

    /*---------------------------Attributs---------------------------*/

    public int id;
    /*---------------------------Constructeur---------------------------*/
    public Arme(){
        this.setGearType("Offensif.Arme");
        this.setValue(0);
        this.setName("Rine");
    }
    public Arme(String type,int dmg,int Id){
        this.setGearType("Offensif.Arme");
        this.setValue(dmg);
        this.setName(type);
        this.id=Id;
    }


    /*---------------------------Méthodes---------------------------*/



    /*---------------------------Setters/Getters---------------------------*/

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
