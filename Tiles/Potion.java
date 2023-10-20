package Tiles;
import Personnages.Characters;

public class Potion implements Tile{


    /*---------------------------Attributs---------------------------*/
    private int id;
    private int value;

    /*---------------------------Constructeur---------------------------*/
    public Potion(int Id){
        this.id=Id;
    }


    /*---------------------------Méthodes---------------------------*/
    @Override
    public void interaction(Characters player) {
        player.heals(this);
        System.out.println("Vous trouvez une potion, nouveau montant de pv : " + player.getHp());
    }

    /*---------------------------Setters/Getters---------------------------*/

    public void setId(int newID) {
        this.id=newID;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
