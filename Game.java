import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class Game {
    private int playerLocation;
    private ArrayList<Tile> plateau =new ArrayList<Tile>();

    public Game(ArrayList<Character> players){
        for (int i = 0; i < 64; i++) {
            this.plateau.add(new Tile());
        }
        Scanner jeu= new Scanner(System.in);
        ArrayList<Character> joueurs = players;
        this.playerLocation=1;
        while (this.playerLocation<64) {
            System.out.println("Tapez 'lancer' pour lancer le dé");
            String choix= jeu.next();
            if (choix.equals("lancer")){
                Random destin = new Random();
                int dice=destin.ints(1, 7).findFirst().getAsInt();
                this.deplacement(dice);
                System.out.println("Position joueur : "+this.playerLocation);
            }
        }
        System.out.println("GG WP ! On recommence ? [y/n]");
    }

    public void deplacement (int jet){
        this.playerLocation+=jet;
    }
    public ArrayList<Tile> getPlateau() {
        return plateau;
    }
}
