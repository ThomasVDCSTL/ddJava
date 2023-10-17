public class EquipementDefensif {
    String gearType;
    public EquipementDefensif(String classe){
        if (classe=="Guerrier"){
            this.gearType="Bouclier";
        }else {
            this.gearType="Philtre";
        }
    }
}
