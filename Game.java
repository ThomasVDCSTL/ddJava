import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class Game {

    /*---------------------------Attributs---------------------------*/
    private int playerLocation;
    private ArrayList<Tile> plateau =new ArrayList<Tile>();
    Random destin;



    /*---------------------------Constructeur---------------------------*/
    public Game(ArrayList<Character> players){
        this.initPlateau();
        Scanner jeu= new Scanner(System.in);
        ArrayList<Character> joueurs = players;
        this.playerLocation=1;
        while (this.playerLocation<64) {
            playerTurn(jeu);
        }
        System.out.println("GG WP ! On recommence ? [y/n]");
    }

    /*---------------------------Méthodes---------------------------*/
    public void initPlateau(){
        for (int i = 0; i < 64; i++) {
            this.plateau.add(new Tile());
            this.plateau.get(i).setId(i+1);
            this.plateau.get(i).setEvent();
        }
    }
    public void deplacement (int jet){
        this.playerLocation+=jet;
    }
    public ArrayList<Tile> getPlateau() {
        return plateau;
    }
    public void playerTurn(Scanner jeu){        /* Possibilité de rajouter un paramètre joueur pour les faire bouger individuellement */
        System.out.println("Tapez 'lancer' pour lancer le dé");
        String choix= jeu.next();
        if (choix.equals("lancer")){
            destin = new Random();
            int dice=destin.ints(1, 7).findFirst().getAsInt();
            this.deplacement(dice);
            System.out.println("Position joueur : "+this.playerLocation);
            String event=this.plateau.get(this.playerLocation+1).triggerEvent();
            System.out.println("Evennement déclenché : "+event);
            if (event.equals("bagarre")){
               this.combat();
            } else if (event.equals("cadeau")) {
                this.getGift();
            }
        }
    }
    public void combat(){
        destin = new Random();
        int ennemyType=destin.ints(1, 4).findFirst().getAsInt();
    }
    public void getGift(){
        destin = new Random();
        int giftType=destin.ints(1, 7).findFirst().getAsInt();
        if (giftType==1){

        } else if (giftType==2) {

        }else if (giftType==3) {

        }else if (giftType==4) {

        }else if (giftType==5) {

        }else if (giftType==6) {

        }
    }
}
