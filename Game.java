import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class Game {

    /*---------------------------Attributs---------------------------*/
    private int playerLocation;
    private ArrayList<Tile> plateau =new ArrayList<Tile>();
    Random destin;
    ArrayList<Character> joueurs;



    /*---------------------------Constructeur---------------------------*/
    public Game(ArrayList<Character> players){
        this.initPlateau();
        Scanner jeu= new Scanner(System.in);
        this.joueurs = players;
        this.playerLocation=1;
        while (this.playerLocation<64&&this.joueurs.get(0).getHp()>0) {
            playerTurn(jeu);
        }
        if (this.playerLocation<64) {
            System.out.print("GG WP !");
        } else if (this.joueurs.get(0).getHp()>0) {
            System.out.print("T'as perdu gros bouffon !");
        }
        System.out.println("On recommence ? [y/n]");
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
        Character player=this.joueurs.get(0);
        System.out.println("Tapez 'lancer' pour lancer le dé");
        String choix= jeu.next();
        boolean alive = true;
        if (choix.equals("lancer")){
            destin = new Random();
            int dice=destin.ints(1, 7).findFirst().getAsInt();
            this.deplacement(dice);
            System.out.println("Position joueur : "+this.playerLocation);
            String event=this.plateau.get(this.playerLocation+1).triggerEvent();
            System.out.println("Evennement déclenché : "+event);
            if (event.equals("bagarre")){
               alive=this.combat(player);
            } else if (event.equals("cadeau")) {
                this.getGift(player);
            }
            if (alive){
                System.out.println("Vous continuez votre aventure");
            }
        }
    }
    public boolean combat(Character player){
        Character adversary=this.initCombat();
        System.out.println("Le combat contre "+adversary.getName()+" commence ... préparez vous !");
        adversary.getsHit(player);
        if (adversary.getHp()<=0){
            System.out.println("WAH DANS SA GUEULE !");
        } else {
            System.out.println("Vous attaquez mais ne tuez pas ... préparez vous pour la riposte !");
            player.getsHit(adversary);
            if (player.getHp()<=0){
                System.out.println("WAH DANS TA GUEULE LOSER !");
                return false;
            }else {
                System.out.println("L'ennemi vous attaque et s'enfuit, il vous reste "+player.getHp()+" PV..");
            }
        }
        return true;
    }
    public Character initCombat(){
        Character ennemy;
        destin = new Random();
        int ennemyType=destin.ints(1, 4).findFirst().getAsInt();
        if (ennemyType==1){
            ennemy= new Character("Sorcier","ennemy",9,2);
        } else if (ennemyType==3) {
            ennemy= new Character("Dragon","ennemy",15,4);
        }else {
            ennemy= new Character("Gobelin","ennemy",6,1);
        }
        return ennemy;
    }
    public void getGift(Character player){
        destin = new Random();
        int giftType=destin.ints(1, 5).findFirst().getAsInt();
        if (giftType==1){
            if (player.getAtkGear().getName().equals("Rine")){
                player.getAtkGear().getWeapon1();
                System.out.println("Vous avez trouvé une arme sympa, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");
            } else {
                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
            }
        } else if (giftType==2) {
            if (player.getAtkGear().getName().equals("Rine")){
                player.getAtkGear().getWeapon2();
                System.out.println("Vous avez trouvé une arme de malade, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");;
            } else {
                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
            }
        }else if (giftType==3) {
            System.out.println("Vous avez trouvé une potion standard, vous gagnez 2 PV");
            player.heals(2);
        }else if (giftType==4) {
            System.out.println("Vous avez trouvé une grande potion, vous gagnez 5 PV");
            player.heals(4);
        }
    }
}
