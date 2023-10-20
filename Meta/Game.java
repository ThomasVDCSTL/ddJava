package Meta;

import Exceptions.LeavingGame;
import Offensif.*;
import Personnages.Characters;
import Personnages.Enemy;
import Tiles.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game {

    /*---------------------------Attributs---------------------------*/
    private int playerLocation;
    private ArrayList<Tile> plateau = new ArrayList<Tile>();
    Random destin;
    ArrayList<Characters> joueurs;
    Menu menu;

    private int maxEnemies;
    private int maxDragons;
    private int maxSorciers;
    private int maxGobelins;
    private int maxChests;
    private int maxMassues;
    private int maxEpees;
    private int maxEclairs;
    private int maxBouleDF;
    private int maxPotionM;
    private int maxPotionG;


    /*---------------------------Constructeur---------------------------*/
    public Game(ArrayList<Characters> players) {
        this.initPlateau();
        this.joueurs = players;
        this.playerLocation = 1;
    }

    /*---------------------------Méthodes---------------------------*/


    public String playGame() throws LeavingGame {
        Scanner jeu = new Scanner(System.in);
        while (this.playerLocation < 64 && this.joueurs.get(0).getHp() > 0) {
            try {
                playerTurn(jeu, this.joueurs.get(0));
            } catch (LeavingGame e) {

                throw e;
            }
        }
        return getEndingMessage(jeu);
    }


    public void initPlateau() {
        for (int i = 1; i < 65; i++) {
            destin = new Random();
            int random = destin.ints(1, 4).findFirst().getAsInt();
            Tile myTile = getTile(random, i);
            this.plateau.add(myTile);
        }
    }


    public void playerTurn(Scanner jeu, Characters player) throws LeavingGame {        /* Possibilité de rajouter un paramètre joueur pour les faire bouger individuellement */
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
            if (playerLocation < 64 && playerLocation >= 0) {
                this.plateau.get(this.playerLocation).interaction(player);
                if (player.getHp()>0) {
                    System.out.println("Vous continuez votre aventure");
                }
            }
        }
    }
    public void startTurn(){
        destin = new Random();
        int dice = destin.ints(1, 7).findFirst().getAsInt();
        this.deplacement(dice);
        System.out.println("Position joueur : " + this.playerLocation);
    }
    public void deplacement(int jet) {
        this.playerLocation += jet;
    }

    public Tile getTile(int choix, int id) {
        if (choix == 1 && this.maxEnemies!=24 && id > 10) {
            return initCombat(id);
        } else {
            return initChest(id);
        }
    }
    public Tile initCombat(int id) {
        destin = new Random();
        int enemyType = destin.ints(1, 4).findFirst().getAsInt();
        if (enemyType == 1&&this.maxSorciers!=10) {
            this.maxEnemies++;
            this.maxSorciers++;
            return new Enemy("Sorcier", 9, 2, id);
        } else if (enemyType == 3&&this.maxDragons!=4&&id>40) {
            this.maxEnemies++;
            this.maxDragons++;
            return new Enemy("Dragon", 15, 4, id);
        } else {
            this.maxEnemies++;
            this.maxGobelins++;
            return new Enemy("Gobelin", 6, 1, id);
        }
    }
    public Tile initChest(int id){
        destin = new Random();
        int newchoice = destin.ints(1, 7).findFirst().getAsInt();
        if (newchoice==6&&this.maxPotionG!=2) {
            this.maxPotionG++;
            this.maxChests++;
            return new PotionG(id);
        }else if (newchoice == 2 &&this.maxEclairs!=5) {
            this.maxEclairs++;
            this.maxChests++;
            return (Tile) new Eclair(id);
        } else if (newchoice == 3 &&this.maxBouleDF!=2) {
            this.maxBouleDF++;
            this.maxChests++;
            return (Tile) new BouleDeFeu(id);
        } else if (newchoice == 4 &&this.maxMassues!=5) {
            this.maxMassues++;
            this.maxChests++;
            return (Tile) new Massue(id);
        } else if (newchoice == 5 &&this.maxEpees!=4) {
            this.maxEpees++;
            this.maxChests++;
            return (Tile) new Epee(id);
        } else {
            this.maxPotionM++;
            this.maxChests++;
            return new PotionM(id);
        }
    }

    public ArrayList<Tile> getPlateau() {
        return plateau;
    }
    public String getEndingMessage(Scanner jeu){
        if (this.playerLocation < 64) {
            System.out.print("T'as perdu gros bouffon !");
        } else if (this.joueurs.get(0).getHp() > 0) {
            System.out.print("GG WP !");
        }
        System.out.println("On recommence ? [y/n]");
        return jeu.next();
    }

}
