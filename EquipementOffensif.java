public class EquipementOffensif {
    /* Pas grand chose dans cette classe pour l'instant,
    seulement le type d'équipement dont il s'agit
    en fonction de la classe du personnage qui la possede */
    private String gearType;
    public EquipementOffensif(String classe){
        if (classe.equals("Guerrier")){
            this.gearType="Arme";
        }else {
            this.gearType="Sort";
        }
    }

    public String getGearType() {
        return gearType;
    }
}
