public class EquipementOffensif {
    String gearType;
    public EquipementOffensif(String classe){
        if (classe=="Guerrier"){
            this.gearType="Arme";
        }else {
            this.gearType="Sort";
        }
    }

    public String getGearType() {
        return gearType;
    }
}
