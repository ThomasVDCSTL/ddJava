package Personnages;

import Defensif.Bouclier;
import Offensif.Arme;
import Tiles.Tile;

public class Enemy extends Characters implements Tile {


    /*---------------------------Attributs---------------------------*/
    public int id;
    /*---------------------------Constructeur---------------------------*/
    public Enemy(String name, int pv, int atk,int Id){
        super(name);
        this.setAttack(atk);
        this.setHp(pv);
        this.setAtkGear(new Arme());
        this.setDefGear(new Bouclier());
        this.id=Id;
    }

    /*---------------------------Méthodes---------------------------*/

    @Override
    public void interaction(Characters player) {
        System.out.println("Un ennemi !");
        System.out.println("Le combat contre " + this.getName() + " commence ... préparez vous !");
        this.getsHit(player);
        if (this.getHp() <= 0) {
            System.out.println("WAH DANS SA GUEULE !");
        } else {
            System.out.println("Vous attaquez mais ne tuez pas ... préparez vous pour la riposte !");
            player.getsHit(this);
            if (player.getHp() <= 0) {
                System.out.println("WAH DANS TA GUEULE LOSER !");
            } else {
                System.out.println("L'ennemi vous attaque et s'enfuit, il vous reste " + player.getHp() + " PV..");
            }
        }
    }
     public void startFight(Characters player){

        boolean playerTurn =true;
        while (this.getHp()>0&&player.getHp()>0){
            if (playerTurn){
                player.playFightTurn(this);
                playerTurn=false;
            }else{
                this.playFightTurn(player);
                playerTurn=true;
            }
        }

     }
     public void playFightTurn(Characters player){

     }


    /*---------------------------Setters/Getters---------------------------*/

    public int getId() {
        return id;
    }


    @Override
    public void setId(int id) {
        this.id = id;
    }
}
