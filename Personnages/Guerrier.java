package Personnages;

import Defensif.Bouclier;
import Offensif.Arme;

public class Guerrier extends Characters {

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Guerrier(String name) {
        super(name,"guerrier");
        this.setAttack(5);
        this.setMaxAttack(10);
        this.setHp(5);
        this.setMaxHp(10);
        this.setAtkGear(new Arme());
        this.setDefGear(new Bouclier());
    }

    /*---------------------------Méthodes---------------------------*/



    /*---------------------------Setters/Getters---------------------------*/

    public int getAttack() {
        return super.getAttack()+this.getAtkGear().getValue();
    }

}