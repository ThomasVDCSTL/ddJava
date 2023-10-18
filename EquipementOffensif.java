public class EquipementOffensif {
    /* Pas grand chose dans cette classe pour l'instant,
    seulement le type d'équipement dont il s'agit
    en fonction de la classe du personnage qui la possede */


    /*---------------------------Attributs---------------------------*/
    private String gearType;
    private String name;
    private int value;


    /*---------------------------Constructeur---------------------------*/
    public EquipementOffensif(String classe){
        if (classe.equals("Guerrier")){
            this.gearType="Arme";
        }else if (classe.equals("Magicien")){
            this.gearType="Sort";
        }else {
            this.gearType="Rine";
        }
        this.value=0;
        this.name="Rine";
    }

    /*---------------------------Méthodes---------------------------*/

    public void setWeapon1(){
        if (this.gearType.equals("Sort")){
            this.name="Eclair";
            this.value=2;
        } else if (this.gearType.equals("Arme")) {
            this.name="Massue";
            this.value=3;
        }
    }
    public void setWeapon2(){
        if (this.gearType.equals("Arme")){
            this.name="Epée";
            this.value=5;
        } else if (this.gearType.equals("Sort")) {
            this.name="Boule de feu";
            this.value=7;
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
