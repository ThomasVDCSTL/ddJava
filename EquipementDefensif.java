public class EquipementDefensif {
    /* Pas grand chose dans cette classe pour l'instant,
    seulement le type d'équipement dont il s'agit
    en fonction de la classe du personnage qui la possede */


    /*---------------------------Attributs---------------------------*/
    private String gearType;
    private String name;
    private int value;



    /*---------------------------Constructeur---------------------------*/
    public EquipementDefensif(String classe){
        if (classe.equals("Guerrier")){
            this.gearType="Bouclier";
        }else {
            this.gearType="Philtre";
        }
    }


    /*---------------------------Setters/Getters---------------------------*/

    public String getGearType() {
        return gearType;
    }


    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

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
