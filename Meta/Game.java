
package Meta;

import Exceptions.LeavingGame;
import Exceptions.ReculDeNcases;
import Offensif.*;
import Personnages.Characters;
import Personnages.Enemy;
import Exceptions.FightOverByDeathOf;
import Tiles.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * blabla
 *
 *
 *
 */
public class Game {

    /*---------------------------Attributs---------------------------*/

    Random destin;
    Characters joueur;
    Menu menu;




    /*---------------------------Constructeur---------------------------*/
    public Game(Characters player) {
        this.joueur = player;
    }

    /*---------------------------Méthodes---------------------------*/


    public String playGame() throws LeavingGame {
        Scanner jeu = new Scanner(System.in);
        while (joueur.getEmplacement() < 64 && joueur.getHp() > 0) {
            playerTurn(jeu, this.joueur);
        }
        return getEndingMessage(jeu);
    }




    /**
     * fonction qui sert a jouer un tour
     * @param jeu objet scanner pour les inputs joueur
     * @param player objet de type character représentant le joueur
     * @throws LeavingGame exception venant de l'option quitter la partie du menu ingame
     */
    public void playerTurn(Scanner jeu, Characters player) throws LeavingGame {
        System.out.println("Tapez 'lancer' pour lancer le dé");
        String choix = jeu.next();
        if (choix.equals("menu")) {
            menu = new Menu(this);
            if (menu.getState().equals("leave")) {
                throw new LeavingGame();
            }
            menu = null;
        }
        if (choix.equals("lancer")) {
            this.startTurn();
            System.out.println(this.inspect(player));
        }
    }
    public String inspect(Characters player){
        if (joueur.getEmplacement() < 64 && joueur.getEmplacement() >= 0) {
            try {
                player.getPlateau().get(joueur.getEmplacement()).interaction(player);
            }catch (ReculDeNcases e){
                joueur.setEmplacement(joueur.getEmplacement()-e.getN());
                inspect(player);
            }catch (FightOverByDeathOf e2) {
            }
            if (player.getHp()>0) {
                player.getPlateau().set(joueur.getEmplacement(),new EmptyTile(joueur.getEmplacement()));
                return "Vous continuez votre aventure";
            }
        }
        return "L'aventure s'arrête ici";
    }
    public void startTurn(){
        destin = new Random();
        int dice = destin.ints(1, 7).findFirst().getAsInt();
        this.deplacement(dice);
        System.out.println("Position joueur : " + joueur.getEmplacement());
    }
    public void deplacement(int jet) {
        joueur.setEmplacement(joueur.getEmplacement()+jet);
    }



    public String getEndingMessage(Scanner jeu){
        if (joueur.getEmplacement() < 64) {
            System.out.print("T'as perdu gros bouffon !");
        } else if (this.joueur.getHp() > 0) {
            System.out.print("GG WP !");
        }
        System.out.println("On recommence ? [y/n]");
        return jeu.next();
    }

}
