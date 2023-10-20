package Tiles;
import Personnages.Characters;

public class EmptyTile implements Tile{


    /*---------------------------Attributs---------------------------*/
    private int id;


    /*---------------------------Cnstructeur---------------------------*/
    public EmptyTile(int Id){
        this.id=Id;
    }

    /*---------------------------Méthodes---------------------------*/
    @Override
    public void interaction(Characters player) {
        System.out.println("Rien dans cette pièce");
    }
    /*---------------------------Setters/Getters---------------------------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
