package Meta;

import Exceptions.LeavingGame;
import Personnages.Characters;
import Personnages.Guerrier;
import Personnages.Magicien;
import com.mysql.cj.Query;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    /* Classe menu servant à initialiser la liste des joueurs et la partie à jouer */



    /*---------------------------Attributs---------------------------*/
    private ArrayList<Characters> players = new ArrayList<Characters>();
    Scanner initGame;
    private String state;
    private Connection mydb;


    /*---------------------------Constructeur---------------------------*/
    public Menu(Connection mydb){
        this.mydb=mydb;
        initGame = new Scanner(System.in);
        System.out.println("Bienvenu dans cette version de custom de Donjons et Dragons !");
        System.out.println("La partie va bientôt commencer..");
        System.out.println("Mais avant, voici quelques explications pour accéder au menu du jeu :");
        System.out.println("A tout moment de la partie vous pouvez taper 'menu' pour afficher le menu");
        System.out.println("Il vous suffira après de suivre les instructions");
        System.out.println("Bon jeu a vous ! [Dis 'merci' !]");
        String demarrer = initGame.next();
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
    public void displayOptions(){
        System.out.println("pas d'options pour le moment....[ok]");
        initGame.next();
    }
    public void leaveGame(Game partie){
        state = "leave";
    }
    public ArrayList<Characters> initPlayers(){
        String check ="y";
        int index=0;
        while (check.equals("y")) {
            check=createCharacter();
            index++;
        }
        displayCharacters();
        return this.players;
    }
    public String createCharacter(){
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
        this.players.add(personnage);
        System.out.println("créer un autre personnage ? [n/y]");
        return initGame.next();
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
//    public void setHeroName (String nom){
//        Statement stmt = null;
//        try {
//            stmt = mydb.createStatement( );
//            Query q=INSERT INTO hero (name) VALUES (?);
//            int nbr=stmt.executeUpdate("INSERT INTO hero (name) VALUES (nom)");
//        }
//        catch (SQLException e) {
//
//        }
//    }
    public String getHeroName() throws SQLException {
        Statement stmt = null;
        ResultSet nom = null;
        try {
            stmt = mydb.createStatement( );
            nom=stmt.executeQuery("SELECT * FROM hero WHERE id=1");
        }
        catch (SQLException e) {

        }
        return nom.getString("name");
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
    public void startGame() {
        String jouer="y";
        this.initPlayers();
        while (jouer.equals("y")) {
            Game partie = new Game(this.players);
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
}
