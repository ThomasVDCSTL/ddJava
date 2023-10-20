package Offensif;

public abstract class EquipementOffensif {
    /* Pas grand chose dans cette classe pour l'instant,
    seulement le type d'équipement dont il s'agit
    en fonction de la classe du personnage qui la possede */


    /*---------------------------Attributs---------------------------*/
    private String name;
    private int value;


    /*---------------------------Constructeur---------------------------*/


    /*---------------------------Méthodes---------------------------*/



    /*---------------------------Setters/Getters---------------------------*/

    public abstract String getGearType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
