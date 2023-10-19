import java.util.Scanner;
public class Main {
    /* La classe main ne fait pas grand chose
    si ce n'est lancer le programme
    en commencant par la création d'un objet Menu */

    /*---------------------------Lancement du programme---------------------------*/
    public static void main(String[] args){
        Scanner typing= new Scanner(System.in);
        Menu menu =new Menu();
        boolean jouer = true;
        while(jouer) {
            try {
                menu.startGame();
            } catch (LeavingGame e) {
                System.out.println("Voulez vous relancer une partie avec un nouveau personnage ? [y/n]");
                if (typing.next().equals("y")){
                    jouer=true;
                }else {
                    jouer =false;
                }
            }
        }
        System.out.println("Au revoir :)");
    }
}
