public class Sort extends EquipementOffensif{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Sort(){
        this.setGearType("Sort");
        this.setValue(0);
        this.setName("Rine");
    }

    public Sort(String type,int dmg){
        this.setGearType("Sort");
        this.setValue(dmg);
        this.setName(type);
    }



    /*---------------------------Méthodes---------------------------*/

    /*---------------------------Setters/Getters---------------------------*/

}
