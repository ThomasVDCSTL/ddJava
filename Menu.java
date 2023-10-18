import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    /* Classe menu servant à initialiser la liste des joueurs et la partie à jouer */



    /*---------------------------Attributs---------------------------*/
    private ArrayList<Character> players = new ArrayList<Character>();
    private Game partie;



    /*---------------------------Méthodes---------------------------*/
    public ArrayList<Character> initPlayers(){
        Scanner initGame = new Scanner(System.in);
        String check ="y";
        int index=0;
        while (check.equals("y")) {
            Character personnage;
            System.out.println("Nom du personnage :");
            String nom = initGame.next();
            String classe ="";
            classe=this.classChoice(classe, initGame);
            System.out.println("1_Nom :"+nom);
            System.out.println("2_Classe :"+classe);
            System.out.println("Voulez-vous changer quelque chose ? [y/n]");
            String refonte=initGame.next();
            while (refonte.equals("y")){
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
                refonte=initGame.next();
            }
            if (classe.equals("Guerrier")) {
                personnage = new Guerrier(nom);
            } else {
                personnage = new Magicien(nom);
            }
            this.players.add(personnage);
            System.out.println("créer un autre personnage ? [n/y]");
            check =initGame.next();
            index++;
        }
        index=0;
        for (Character player:this.players) {
            index++;
            System.out.println("<--------Personnage n°"+index+"-------->");
            System.out.println("1_Nom :"+player.getName());
            System.out.println("2_Classe :"+player.getClass());
            System.out.println("3_Points de vie :"+player.getHp());
            System.out.println("4_Attaque :"+player.getAttack());
        }
        return this.players;
    }
    public void startGame(){
        String jouer="y";
        this.initPlayers();
        while (jouer.equals("y")) {
            this.partie = new Game(this.players);
            jouer=this.partie.playGame();
        }
    }
    public static void leaveGame(){

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
}
