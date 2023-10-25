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
    public void getsHit(Characters opponent) {
        this.setHp(this.getHp()-(opponent.getAttack()-this.getDefGear().getValue()));
        System.out.println(opponent.getName()+" vous attaque attaque pour "+opponent.getAttack());
        System.out.println(this.getName()+"'s HP : "+this.getHp());
    }


    /*---------------------------Setters/Getters---------------------------*/

    public int getAttack() {
        return super.getAttack()+this.getAtkGear().getValue();
    }

}