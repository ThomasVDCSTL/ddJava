package Offensif;
import Tiles.Tile;
import Personnages.Characters;

public class Arme extends EquipementOffensif implements Tile {

    /*---------------------------Attributs---------------------------*/

    public int id;
    /*---------------------------Constructeur---------------------------*/
    public Arme(){
        this.setValue(0);
        this.setName("Rine");
    }
    public Arme(String type,int dmg,int Id){
        this.setValue(dmg);
        this.setName(type);
        this.id=Id;
    }


    /*---------------------------Méthodes---------------------------*/

    @Override
    public void interaction(Characters player) {
        System.out.print("Vous trouvez " + this.getName());
        if (player.canEquip(this)) {
            player.setAtkGear(this);
            System.out.println(" et vous en équippez.");
        } else {
            System.out.println(" mais ça ne vous est d'aucune utilité...");
        }
    }

    /*---------------------------Setters/Getters---------------------------*/
    public String getGearType(){
        return "arme";
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
