public class EmptyTile implements Tile{


    /*---------------------------Attributs---------------------------*/
    private int id;


    /*---------------------------Cnstructeur---------------------------*/
    public EmptyTile(int Id){
        this.id=Id;
    }

    /*---------------------------Méthodes---------------------------*/
    public String triggerEvent() {
        return "Rine";
    }
    /*---------------------------Setters/Getters---------------------------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
