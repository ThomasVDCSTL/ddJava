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
            System.out.println("Nom du personnage :");
            String nom = initGame.next();
            System.out.println("Classe : [Guerrier/Magicien]");
            String classe=initGame.next();
            System.out.println("Points de vie :");
            int pv = initGame.nextInt();
            System.out.println("Attaque :");
            int att = initGame.nextInt();
            Character personnage = new Character(nom,classe,pv,att);
            System.out.println("1_Nom :"+personnage.getName());
            System.out.println("2_Classe :"+personnage.getClasse());
            System.out.println("3_Points de vie :"+personnage.getHp());
            System.out.println("4_Attaque :"+personnage.getAttack());
            System.out.println("Voulez-vous changer quelque chose ? [y/n]");
            String refonte=initGame.next();
            while (refonte.equals("y")){
                System.out.println("Quelle stat voulez vous changer ? [1-4]");
                int choix = initGame.nextInt();
                if (choix==1){
                    System.out.println("Nouveau Nom :");
                    personnage.setName(initGame.next());
                } else if (choix==2) {
                    System.out.println("Nouvelle classe :");
                    personnage.setClasse(initGame.next());
                } else if (choix==3) {
                    System.out.println("Nouveaux Points de vie :");
                    personnage.setHp(initGame.nextInt());
                } else if (choix==4) {
                    System.out.println("Nouvell attaque :");
                    personnage.setAttack(initGame.nextInt());
                }else {
                    System.out.println("il n'y a pas de choix"+choix);
                }
                System.out.println("Vouez-vous changer autre chose ? [y/n]");
                refonte=initGame.next();
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
            System.out.println("2_Classe :"+player.getClasse());
            System.out.println("3_Points de vie :"+player.getHp());
            System.out.println("4_Attaque :"+player.getAttack());
        }
        return this.players;
    }
    public void startGame(){
        this.partie = new Game(this.initPlayers());
    }
    public static void leaveGame(){

    }
}
