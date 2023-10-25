package Personnages;

import Defensif.Bouclier;
import Offensif.Arme;
import Tiles.Tile;
import Exceptions.*;

import java.util.Scanner;
import java.util.Random;

public class Enemy extends Characters implements Tile {


    /*---------------------------Attributs---------------------------*/
    public int id;
    public Scanner truc ;
    public Random destin;
    /*---------------------------Constructeur---------------------------*/
    public Enemy(String name, int pv, int atk,int Id){
        super(name,"ennemi");
        this.setAttack(atk);
        this.setHp(pv);
        this.setAtkGear(new Arme());
        this.setDefGear(new Bouclier());
        this.id=Id;
        truc =new Scanner(System.in);
        destin = new Random();
    }

    /*---------------------------Méthodes---------------------------*/

    @Override
    public void interaction(Characters player)throws ReculDeNcases,FightOverByDeathOf {
        System.out.println("Un ennemi !");
        System.out.println("Le combat contre " + this.getName() + " commence ... préparez vous !");
        try {
            startFight(player);
        }catch(FightOverByDeathOf e1){
            System.out.println("Combat terminé");
        }catch (FightOverByFlee e2){
            int recul = destin.ints(1, 4).findFirst().getAsInt();
            System.out.println("Vous avez fui et reculé de "+recul+" cases");
            throw new ReculDeNcases(recul);
        }
    }
     public void startFight(Characters player)throws FightOverByFlee,FightOverByDeathOf{

        boolean playerTurn =true;
        while (this.getHp()>0&&player.getHp()>0){
            if (playerTurn){
                System.out.println("<-------TOUR DU JOUEUR------->");
                String choix= "";
                while (!choix.equals("a")&&!choix.equals("f")) {
                    System.out.println("Voulez-vous attaquer ou fuir ? [a/f]");
                    choix = truc.next();
                }
                if (choix.equals("a")) {
                    try {
                        player.playFightTurn(this);
                    } catch (FightOverByDeathOf e) {
                        System.out.println("Vous avez vaincu "+this.getName());
                        throw e;
                    }
                } else {
                    throw new FightOverByFlee();
                }
                playerTurn=false;
            }else{
                System.out.println("<-------TOUR DE "+this.getName().toUpperCase()+"------->");
                try {
                    this.playFightTurn(player);
                }catch (FightOverByDeathOf e){
                    System.out.println(this.getName()+" vous a vaincu...");
                    throw e;
                }
                playerTurn=true;
            }
        }
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
