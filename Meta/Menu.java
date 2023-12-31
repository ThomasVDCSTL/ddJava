package Meta;

import Database.*;
import Exceptions.LeavingGame;
import Offensif.BouleDeFeu;
import Offensif.Eclair;
import Offensif.Epee;
import Offensif.Massue;
import Personnages.Characters;
import Personnages.Enemy;
import Personnages.Guerrier;
import Personnages.Magicien;
import Tiles.EmptyTile;
import Tiles.PotionG;
import Tiles.PotionM;
import Tiles.Tile;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    /* Classe menu servant à initialiser la liste des joueurs et la partie à jouer */



    /*---------------------------Attributs---------------------------*/
    private ArrayList<Characters> players = new ArrayList<Characters>();
    Scanner initGame;
    private String state;
    DatabaseCRUD mydb;
    Characters actualHero = null;
    Random destin;
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
    public Menu(){
        mydb=new DatabaseCRUD();
        destin=new Random();
        initGame = new Scanner(System.in);
        System.out.println("Bienvenu dans cette version de custom de Donjons et Dragons !");
        System.out.println("La partie va bientôt commencer..");
        System.out.println("Mais avant, voici quelques explications pour accéder au menu du jeu :");
        System.out.println("A tout moment de la partie vous pouvez taper 'menu' pour afficher le menu");
        System.out.println("Il vous suffira après de suivre les instructions");
        System.out.println("Bon jeu a vous ! [Dis 'merci' !]");
    }
    public Menu(Game partie){
        initGame = new Scanner(System.in);
        int choice =0;
        while(choice!=2&&choice!=3) {
            System.out.println("-----Menu du jeu-----");
            System.out.println("1_ Options");
            System.out.println("2_ Reprendre");
            System.out.println("3_ Quitter la partie");

            choice = initGame.nextInt();
            if (choice == 1) {
                this.displayOptions();
            } else if (choice==2){
                System.out.println("Reprise de la partie");
            } else if (choice == 3) {
                this.leaveGame(partie);
            }else {
                System.out.println("essaie pas de me faire ...");
            }
        }
    }


    /*---------------------------Méthodes---------------------------*/

    public void getStartMenu()throws SQLException{
        int choix = 0;
        while (choix!=4) {
            choix = 0;
            System.out.println("------Menu de Départ------");
            System.out.println("1_ Création Personnage");
            System.out.println("2_ Séléction Personnage");
            System.out.println("3_ Supprimer Personnage");
            System.out.println("4_ Jouer");
            while (choix < 1 || choix > 4) {
                choix = initGame.nextInt();
            }
            if (choix == 1) {
                createCharacter();
            } else if (choix == 2) {
                selectHero();
            } else if (choix==3) {
                try {
                    deleteHero();
                }catch(SQLException e){
                    System.out.println("Aucun personnage supprimé, vérifiez l'ortographe");
                }
            }else {
                if(actualHero==null){
                    System.out.println("Veuillez séléctionner un personnage avant de commencer..");
                    choix=0;
                }else {
                    Game myGame = new Game(actualHero);
                    try {
                        myGame.playGame();
                    } catch (LeavingGame e) {
                        System.out.println("Vous quittez la partie");
                        HeroCRUD.getInstance().updateHero(actualHero);
                    }
                }
            }
        }
    }
    public void displayOptions(){
        System.out.println("pas d'options pour le moment....[ok]");
        initGame.next();
    }
    public void leaveGame(Game partie){
        state = "leave";
    }
//    public ArrayList<Characters> initPlayers()throws SQLException{
//        String check ="y";
//        int index=0;
//        while (check.equals("y")) {
//            createCharacter();
//            index++;
//        }
//        displayCharacters();
//        return this.players;
//    }
    public void selectHero()throws SQLException{
        ArrayList<Characters> heroes= HeroCRUD.getInstance().getHeroList();
        displayHeros(heroes);
        int choix=0;
        while (choix<1||choix>heroes.size()) {
            System.out.println("Votre choix : ");
            choix = initGame.nextInt();

        }
        actualHero= heroes.get(choix-1);
    }
    public void deleteHero()throws SQLException{
        ArrayList<Characters> heroes= HeroCRUD.getInstance().getHeroList();
        displayHeros(heroes);
        System.out.println("Quel personnage voulez vous supprimer ? [Nom du personnage]");
        String choix = initGame.next();
        HeroCRUD.deleteHero(choix);
    }
    public void createCharacter()throws SQLException{
        String classe ="";
        Characters personnage;
        System.out.println("Nom du personnage :");
        String nom = initGame.next();
        classe=this.classChoice(classe, initGame);
        System.out.println("1_Nom :"+nom);
        System.out.println("2_Classe :"+classe);
        System.out.println("Voulez-vous changer quelque chose ? [y/n]");
        String refonte=initGame.next();
        while (refonte.equals("y")){
            refonte = proposeChanging(nom,classe);
        }
        if (classe.equals("Guerrier")) {
            personnage = new Guerrier(nom);
        } else {
            personnage = new Magicien(nom);
        }
        initPlateau(personnage);
        HeroCRUD.getInstance().createHero(personnage);
        actualHero=personnage;
        System.out.println("Personnage créé et séléctionné");
    }
    public void displayCharacters(){
        int index=0;
        for (Characters player:this.players) {
            index++;
            System.out.println("<--------Personnage n°"+index+"-------->");
            System.out.println("1_Nom :"+player.getName());
            System.out.println("2_Classe :"+player.getClass().getName());
            System.out.println("3_Points de vie :"+player.getHp());
            System.out.println("4_Attaque :"+player.getAttack());
            System.out.println("5_AttaqueGear :"+player.getAtkGear().getName());
        }
    }

    public void displayHeros (ArrayList<Characters> heroList){
        for (int i = 1; i < heroList.size()+1; i++) {
            Characters hero=heroList.get(i-1);
            System.out.println("-----Hero n°"+i+"-----");
            System.out.println("Name : "+hero.getName());
            System.out.println("Classe : "+hero.getType());
            System.out.println("Points de vie : "+hero.getHp());
            System.out.println("Emplacement dans le donjon : "+hero.getEmplacement());
            System.out.println("Equipement offensif : "+hero.getAtkGear().getName());
            System.out.println("Equipement defensif : "+hero.getDefGear().getName());
        }
        System.out.println("-----------------");
    }
    public String proposeChanging(String nom, String classe){
        System.out.println("Quelle stat voulez vous changer ? [1-2]");
        int choix = initGame.nextInt();
        if (choix==1){
            System.out.println("Nouveau Nom :");
            nom=initGame.next();
        } else if (choix==2) {
            System.out.println("Nouvelle classe :");
            classe="";
            classe=this.classChoice(classe, initGame);
        }else {
            System.out.println("il n'y a pas de choix"+choix);
        }
        System.out.println("Vouez-vous changer autre chose ? [y/n]");
        return initGame.next();
    }
    public void startGame()throws SQLException {
        String jouer="y";
        while (jouer.equals("y")) {
            Game partie = new Game(actualHero);
            try {
                jouer = partie.playGame();
            }catch (LeavingGame e){
                System.out.println(e.getMessage());
                partie=null;
                System.gc();
                System.out.println("On recommence ? [y/n]");
                jouer=initGame.next();
            }
        }
    }
    public String classChoice(String classe,Scanner initGame ){
        while (!classe.equals("Guerrier")&&!classe.equals("Magicien")){
            System.out.println("Voulez vous être un guerrier ou un magicien ? [g/m]");
            classe=initGame.next();
            if (classe.equals("g")){
                classe="Guerrier";
            } else if (classe.equals("m")) {
                classe="Magicien";
            }else{
                System.out.println("Je n'ai pas compris..");
            }
        }
        return classe;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public void initPlateau(Characters joueur) {
        ArrayList<Tile> plateau = new ArrayList<Tile>();
        for (int i = 1; i < 65; i++) {
            destin = new Random();
            int random = destin.ints(1, 4).findFirst().getAsInt();
            Tile myTile = getTile(random, i);
            plateau.add(myTile);
        }
        joueur.setPlateau(plateau);
    }
    public Tile getTile(int choix, int id) {
        if (choix == 1 && this.maxEnemies!=24 && id > 10) {
            return initCombat(id);
        } else if (choix!=3 && this.maxChests!=24){
            return initChest(id);
        } else {
            return new EmptyTile(id);
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

}
