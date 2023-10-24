package Meta;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.image.ImageObserver;
 import java.awt.image.ImageProducer;

public class Fenetre extends JFrame{
    public Fenetre(){



        this.setTitle("ma Fenêtre !");
//        this.setIconImage();
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenu =new JPanel();
        contenu.setBackground(Color.blue);

        JMenuBar navbar = new JMenuBar();
        navbar.setBackground(Color.orange);
        navbar.setSize(600,50);
        navbar.setVisible(true);

        this.setJMenuBar(navbar);
        this.setContentPane(contenu);
        this.setVisible(true);
    }
}
