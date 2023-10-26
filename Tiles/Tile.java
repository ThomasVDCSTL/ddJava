package Tiles;

import Exceptions.ReculDeNcases;
import Personnages.Characters;
import Exceptions.FightOverByDeathOf;

public interface Tile {

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/


    /*---------------------------Méthodes---------------------------*/

     public void interaction(Characters player) throws ReculDeNcases, FightOverByDeathOf;

    /*---------------------------Setters/Getters---------------------------*/

    public void setId(int newID);
    public int getId();


}

