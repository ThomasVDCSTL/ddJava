package Meta;

import Exceptions.LeavingGame;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    /* La classe main ne fait pas grand chose
    si ce n'est lancer le programme
    en commencant par la création d'un objet Meta.Menu */

    /*---------------------------Lancement du programme---------------------------*/
    public static void main(String[] args) {
        try {
            Scanner typing = new Scanner(System.in);
            Menu menu = new Menu();
            menu.getStartMenu();
            System.out.println("Au revoir :)");
        }catch (SQLException e){
            System.out.println("padchance");
        }
    }


}
