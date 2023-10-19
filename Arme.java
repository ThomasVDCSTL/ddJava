public class Arme extends EquipementOffensif{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Arme(){
        this.setGearType("Arme");
        this.setValue(0);
        this.setName("Rine");
    }
    public Arme(String type,int dmg){
        this.setGearType("Arme");
        this.setValue(dmg);
        this.setName(type);
    }


    /*---------------------------Méthodes---------------------------*/



    /*---------------------------Setters/Getters---------------------------*/

}
