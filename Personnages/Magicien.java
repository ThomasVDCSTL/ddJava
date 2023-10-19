package Personnages;

import Defensif.Philtre;
import Offensif.Sort;
import Personnages.Character;

public class Magicien extends Character {

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Magicien(String name){
        this.setName(name);
        this.setAttack(8);
        this.setMaxAttack(15);
        this.setHp(3);
        this.setMaxHp(6);
        this.setAtkGear(new Sort());
        this.setDefGear(new Philtre());
    }


    /*---------------------------Méthodes---------------------------*/


    public void getsHit(Character opponent) {
        this.setHp(this.getHp()-(opponent.getAttack()-this.getDefGear().getValue()));
    }


    /*---------------------------Setters/Getters---------------------------*/

    public int getAttack() {
        return super.getAttack()+this.getAtkGear().getValue();
    }


}
