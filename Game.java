import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class Game {

    /*---------------------------Attributs---------------------------*/
    private int playerLocation;
    private ArrayList<Tile> plateau =new ArrayList<Tile>();
    Random destin;
    ArrayList<Character> joueurs;
    Menu menu;



    /*---------------------------Constructeur---------------------------*/
    public Game(ArrayList<Character> players){
        this.initPlateau();
        this.joueurs = players;
        this.playerLocation=1;
    }

    /*---------------------------Méthodes---------------------------*/




    public String playGame()throws LeavingGame{
        Scanner jeu= new Scanner(System.in);
        while (this.playerLocation<64&&this.joueurs.get(0).getHp()>0) {
            try{
                playerTurn(jeu);
            }catch (LeavingGame e){

                throw e;
            }
        }
        if (this.playerLocation<64) {
            System.out.print("T'as perdu gros bouffon !");
        } else if (this.joueurs.get(0).getHp()>0) {
            System.out.print("GG WP !");
        }
        System.out.println("On recommence ? [y/n]");
        return jeu.next();
    }
    public void initPlateau(){
        for (int i = 1; i < 65; i++) {
            destin = new Random();
            int random=destin.ints(1, 4).findFirst().getAsInt();
            Tile myTile=getTile(random,i);
            this.plateau.add(myTile);
        }
    }
    public void deplacement (int jet){
        this.playerLocation+=jet;
    }
    public void playerTurn(Scanner jeu)throws LeavingGame{        /* Possibilité de rajouter un paramètre joueur pour les faire bouger individuellement */
        Character player=this.joueurs.get(0);
        System.out.println("Tapez 'lancer' pour lancer le dé");
        String choix= jeu.next();
        if (choix.equals("menu")){
            menu =new Menu(this);
            if (menu.getState().equals("leave")){
                throw new LeavingGame();
            }
            menu=null;
        }
        boolean alive = true;
        if (choix.equals("lancer")){
            destin = new Random();
            int dice=destin.ints(1, 7).findFirst().getAsInt();
            this.deplacement(dice);
            System.out.println("Position joueur : " + this.playerLocation);
            if (playerLocation<=64&&playerLocation>=0) {
                String event = this.plateau.get(this.playerLocation-1).triggerEvent();
                System.out.println("Evennement déclenché : " + event);
                if (this.plateau.get(this.playerLocation) instanceof Enemy adversary) {
                    alive = this.combat(player,adversary);
                } else if (event.equals("cadeau")) {
                    this.giveGift(player);
                }
                if (alive) {
                    System.out.println("Vous continuez votre aventure");
                }
            }
        }
    }
    public boolean combat(Character player,Enemy adversary){
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
    public Enemy initCombat(int id){
        Enemy enemy;
        destin = new Random();
        int enemyType=destin.ints(1, 4).findFirst().getAsInt();
        if (enemyType==1){
            enemy= new Enemy("Sorcier",9,2,id);
        } else if (enemyType==3) {
            enemy= new Enemy("Dragon",15,4,id);
        }else {
            enemy= new Enemy("Gobelin",6,1,id);
        }
        return enemy;
    }
    public void giveGift(Character player){
        destin = new Random();
        int giftType=destin.ints(1, 5).findFirst().getAsInt();
        if (giftType==1){
            if (player.getAtkGear().getName().equals("Rine")){
                player.giveWeapon1();
                System.out.println("Vous avez trouvé une arme sympa, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");
            } else {
                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
            }
        } else if (giftType==2) {
            if (player.getAtkGear().getName().equals("Rine")){
                player.giveWeapon2();
                System.out.println("Vous avez trouvé une arme de malade, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");;
            } else {
                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
            }
        }else if (giftType==3) {
            player.heals(2);
            System.out.println("Vous avez trouvé une POTION STANDARD, votre nouveau montant de PV est : "+player.getHp());
        }else if (giftType==4) {
            player.heals(5);
            System.out.println("Vous avez trouvé une GRANDE POTION, votre nouveau montant de PV est : "+player.getHp());
        }
    }
    public ArrayList<Tile> getPlateau() {
        return plateau;
    }
    public Tile getTile(int choix,int id){
        if (choix==1){
            return initCombat(id);
        } else if (choix==2) {
            return new Potion(id);
        }else {
            return new EmptyTile(id);
        }
    }
}
