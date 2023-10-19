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
        for (int i = 0; i < 64; i++) {
            this.plateau.add(new Tile(i+1));
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
                if (event.equals("bagarre")) {
                    alive = this.combat(player);
                } else if (event.equals("cadeau")) {
                    this.getGift(player);
                }
                if (alive) {
                    System.out.println("Vous continuez votre aventure");
                }
            } else {
                try {
                    if (this.playerLocation>64||this.playerLocation<0) {            /* refaire tout ça */
                        throw new PersonnageHorsPlateauException();
                    }
                    String event = this.plateau.get(this.playerLocation-1).triggerEvent();
                }catch (PersonnageHorsPlateauException e){ System.out.println("j'ai choppé l'erreur");}

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
            ennemy= new Guerrier("Sorcier");
        } else if (ennemyType==3) {
            ennemy= new Guerrier("Dragon");
        }else {
            ennemy= new Guerrier("Gobelin");
        }
        return ennemy;
    }
    public void getGift(Character player){
        destin = new Random();
        int giftType=destin.ints(1, 5).findFirst().getAsInt();
        if (giftType==1){
            if (player.getAtkGear().getName().equals("Rine")){
                player.setAtkGear(this.giveWeapon1(player));
                System.out.println("Vous avez trouvé une arme sympa, vous gagnez "+player.getAtkGear().getValue()+" points d'attaque !");
            } else {
                System.out.println("Vous avez trouvé une arme mais elle n'est pas mieux que celle que vous possédez déjà... vous la laissez ici!");
            }
        } else if (giftType==2) {
            if (player.getAtkGear().getName().equals("Rine")){
                player.setAtkGear(this.giveWeapon2(player));
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
    public EquipementOffensif giveWeapon1(Character player){
        if (player.getAtkGear().getGearType().equals("arme")){
            return new Arme("Massue",3);
        } else {
            return new Sort("Eclair",2);
        }
    }
    public EquipementOffensif giveWeapon2(Character player){
        if (player.getAtkGear().getGearType().equals("arme")){
            return new Arme("Epée",5);
        } else {
            return new Sort("Boule de feu",7);
        }
    }
    public ArrayList<Tile> getPlateau() {
        return plateau;
    }
}
