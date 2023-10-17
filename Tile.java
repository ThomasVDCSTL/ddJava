public class Tile {
    private int id;
    private boolean playerLocation;
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
