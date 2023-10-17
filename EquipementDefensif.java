public class EquipementDefensif {
    /* Pas grand chose dans cette classe pour l'instant,
    seulement le type d'équipement dont il s'agit
    en fonction de la classe du personnage qui la possede */
    private String gearType;
    public EquipementDefensif(String classe){
        if (classe.equals("Guerrier")){
            this.gearType="Bouclier";
        }else {
            this.gearType="Philtre";
        }
    }

    public String getGearType() {
        return gearType;
    }
}
