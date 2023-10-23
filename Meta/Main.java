package Meta;

import Exceptions.LeavingGame;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    /* La classe main ne fait pas grand chose
    si ce n'est lancer le programme
    en commencant par la création d'un objet Meta.Menu */

    /*---------------------------Lancement du programme---------------------------*/
    public static void main(String[] args) {
        Fenetre f = new Fenetre();
        Scanner typing = new Scanner(System.in);
        Menu menu = new Menu();
        menu.startGame();
        System.out.println("Au revoir :)");
    }
}

