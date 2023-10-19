package Offensif;
import Tiles.Tile;

import Offensif.EquipementOffensif;

public class Sort extends EquipementOffensif implements Tile {

    /*---------------------------Attributs---------------------------*/

    public int id;
    /*---------------------------Constructeur---------------------------*/
    public Sort(){
        this.setGearType("Offensif.Sort");
        this.setValue(0);
        this.setName("Rine");
    }

    public Sort(String nom,int dmg,int Id){
        this.setGearType("Offensif.Sort");
        this.setValue(dmg);
        this.setName(nom);
        this.id=Id;
    }



    /*---------------------------Méthodes---------------------------*/




    /*---------------------------Setters/Getters---------------------------*/
    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
