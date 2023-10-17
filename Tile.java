public class Tile {
    int id;
    boolean playerLocation;
    public void Tile(){
        this.id=1;
    }

    public boolean isPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(boolean playerLocation) {
        this.playerLocation = playerLocation;
    }
}
