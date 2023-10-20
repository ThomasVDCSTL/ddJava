package Personnages;

import Defensif.Philtre;
import Offensif.Sort;

public class Magicien extends Characters {

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Magicien(String name){
        super(name);
        this.setAttack(8);
        this.setMaxAttack(15);
        this.setHp(3);
        this.setMaxHp(6);
        this.setAtkGear(new Sort());
        this.setDefGear(new Philtre());
    }


    /*---------------------------Méthodes---------------------------*/


    public void getsHit(Characters opponent) {
        this.setHp(this.getHp()-(opponent.getAttack()-this.getDefGear().getValue()));
    }


    /*---------------------------Setters/Getters---------------------------*/

    public int getAttack() {
        return super.getAttack()+this.getAtkGear().getValue();
    }


}
