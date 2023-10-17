import java.util.ArrayList;
public class Game {
    ArrayList<Tile> plateau =new ArrayList<Tile>();

    public void Game(){
        for (int i = 0; i < 64; i++) {
            this.plateau.add(new Tile());
        }
    }

    public ArrayList<Tile> getPlateau() {
        return plateau;
    }
}
