package Meta;

import Exceptions.LeavingGame;
import Offensif.*;
import Personnages.Character;
import Personnages.Enemy;
import Tiles.Tile;
import Tiles.Potion;
import Tiles.PotionM;
import Tiles.PotionG;
import Tiles.EmptyTile;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game {

    /*---------------------------Attributs---------------------------*/
    private int playerLocation;
    private ArrayList<Tile> plateau = new ArrayList<Tile>();
    Random destin;
    ArrayList<Character> joueurs;
    Menu menu;

    private int maxEnemies ;
    private int maxDragons;
    private int maxSorciers ;
    private int maxGobelins ;
    private int maxChests ;
    private int maxMassues;
    private int maxEpees;
    private int maxEclairs;
    private int maxBouleDF;
    private int maxPotionM;
    private int maxPotionG;


    /*---------------------------Constructeur---------------------------*/
    public Game(ArrayList<Character> players) {
        this.initPlateau();
        this.joueurs = players;
        this.playerLocation = 1;
    }

    /*---------------------------Méthodes---------------------------*/


    public String playGame() throws LeavingGame {
        Scanner jeu = new Scanner(System.in);
        while (this.playerLocation < 64 && this.joueurs.get(0).getHp() > 0) {
            try {
                playerTurn(jeu);
            } catch (LeavingGame e) {

                throw e;
            }
        }
        if (this.playerLocation < 64) {
            System.out.print("T'as perdu gros bouffon !");
        } else if (this.joueurs.get(0).getHp() > 0) {
            System.out.print("GG WP !");
        }
        System.out.println("On recommence ? [y/n]");
        return jeu.next();
    }

    public void initPlateau() {
        for (int i = 1; i < 65; i++) {
            destin = new Random();
            int random = destin.ints(1, 4).findFirst().getAsInt();
            Tile myTile = getTile(random, i);
            this.plateau.add(myTile);
        }
    }

    public void deplacement(int jet) {
        this.playerLocation += jet;
    }

    public void playerTurn(Scanner jeu) throws LeavingGame {        /* Possibilité de rajouter un paramètre joueur pour les faire bouger individuellement */
        Character player = this.joueurs.get(0);
        System.out.println("Tapez 'lancer' pour lancer le dé");
        String choix = jeu.next();
        if (choix.equals("menu")) {
            menu = new Menu(this);
            if (menu.getState().equals("leave")) {
                throw new LeavingGame();
            }
            menu = null;
        }
        boolean alive = true;
        if (choix.equals("lancer")) {
            destin = new Random();
            int dice = destin.ints(1, 7).findFirst().getAsInt();
            this.deplacement(dice);
            System.out.println("Position joueur : " + this.playerLocation);
            if (playerLocation < 64 && playerLocation >= 0) {
                if (this.plateau.get(this.playerLocation) instanceof Enemy adversary) {
                    System.out.println("Un ennemi !");
                    alive = this.combat(player, adversary);
                } else if (this.plateau.get(this.playerLocation) instanceof Potion potion) {
                    player.heals(potion);
                    System.out.println("Vous trouvez une potion, nouveau montant de pv : " + player.getHp());
                } else if (this.plateau.get(this.playerLocation) instanceof EquipementOffensif stuff) {
                    System.out.print("Vous trouvez " + stuff.getName());
                    if (player.canEquip(stuff)) {
                        player.setAtkGear(stuff);
                        System.out.println(" et vous en équippez.");
                    } else {
                        System.out.println(" mais ça ne vous est d'aucune utilité...");
                    }
                } else {
                    System.out.println("Rien dans cette pièce");
                }
                if (alive) {
                    System.out.println("Vous continuez votre aventure");
                }
            }
        }
    }

    public boolean combat(Character player, Enemy adversary) {
        System.out.println("Le combat contre " + adversary.getName() + " commence ... préparez vous !");
        adversary.getsHit(player);
        if (adversary.getHp() <= 0) {
            System.out.println("WAH DANS SA GUEULE !");
        } else {
            System.out.println("Vous attaquez mais ne tuez pas ... préparez vous pour la riposte !");
            player.getsHit(adversary);
            if (player.getHp() <= 0) {
                System.out.println("WAH DANS TA GUEULE LOSER !");
                return false;
            } else {
                System.out.println("L'ennemi vous attaque et s'enfuit, il vous reste " + player.getHp() + " PV..");
            }
        }
        return true;
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

    public ArrayList<Tile> getPlateau() {
        return plateau;
    }

    public Tile getTile(int choix, int id) {
        if (choix == 1 && this.maxEnemies!=24 && id > 10) {
            return initCombat(id);
        } else {
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
    }
    //    public void giveGift(Personnages.Character player){
//        destin = new Random();
//        int giftType=destin.ints(1, 5).findFirst().getAsInt();
//        if (giftType==1){
//            if (player.getAtkGear().getName().equals("Rine")){
//                player.giveWeapon1();
//                System.out.println("Vous avez trouvé une arme sympa, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");
//            } else {
//                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
//            }
//        } else if (giftType==2) {
//            if (player.getAtkGear().getName().equals("Rine")){
//                player.giveWeapon2();
//                System.out.println("Vous avez trouvé une arme de malade, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");;
//            } else {
//                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
//            }
//        }else if (giftType==3) {
//            player.heals(2);
//            System.out.println("Vous avez trouvé une POTION STANDARD, votre nouveau montant de PV est : "+player.getHp());
//        }else if (giftType==4) {
//            player.heals(5);
//            System.out.println("Vous avez trouvé une GRANDE POTION, votre nouveau montant de PV est : "+player.getHp());
//        }
//    }
}
