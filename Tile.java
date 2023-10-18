import java.util.Random;
public class Tile {

    /*---------------------------Attributs---------------------------*/
    private int id;
    private boolean playerLocation;
    private int event; /* Numéroté de 1 à 3 */


    /*---------------------------Constructeur---------------------------*/
    public void Tile(){

    }



    /*---------------------------Méthodes---------------------------*/
    public String triggerEvent(){
        String consequence;
        if (this.event==2) {
            consequence="bagarre";
        } else if (this.event==3) {
            consequence="cadeau";
        } else {
            consequence="rine";
        }
        return consequence;
    }


    /*---------------------------Setters/Getters---------------------------*/

    public boolean getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(boolean playerLocation) {
        this.playerLocation = playerLocation;
    }


    public void setId(int newID){
        this.id=newID;
    }

    public int getId() {
        return id;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent() {
        Random destin = new Random();
        this.event = destin.ints(1, 4).findFirst().getAsInt();
    }
}
