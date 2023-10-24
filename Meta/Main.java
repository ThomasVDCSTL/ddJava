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
        Connection mydb = null;
        try {
            Driver myDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(myDriver);
            String URL = "jdbc:mysql://localhost:3306/mydb_java";
            mydb = DriverManager.getConnection(URL,"root2","");
            System.out.println("l'url est bonne");
            Scanner typing = new Scanner(System.in);
            Menu menu = new Menu(mydb);
//            menu.setHeroName("Spozer");
            System.out.println(menu.getHeroName());
            menu.startGame();
            System.out.println("Au revoir :)");
        } catch (SQLException e) {
            System.out.println("Error: unable to load driver class! dès le début");
            System.exit(1);
        }finally {
            mydb=null;

        }
    }


}
