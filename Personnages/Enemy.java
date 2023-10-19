package Personnages;

import Defensif.Bouclier;
import Offensif.Arme;
import Tiles.Tile;

public class Enemy extends Character implements Tile {


    /*---------------------------Attributs---------------------------*/
    public int id;
    /*---------------------------Constructeur---------------------------*/
    public Enemy(String name, int pv, int atk,int Id){
        this.setAttack(atk);
        this.setName(name);
        this.setHp(pv);
        this.setAtkGear(new Arme());
        this.setDefGear(new Bouclier());
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
