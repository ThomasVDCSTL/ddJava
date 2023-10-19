public class Sort extends EquipementOffensif{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Sort(){
        this.setGearType("Sort");
        this.setValue(0);
        this.setName("Rine");
    }

    public Sort(String nom,int dmg){
        this.setGearType("Sort");
        this.setValue(dmg);
        this.setName(nom);
    }



    /*---------------------------Méthodes---------------------------*/

    /*---------------------------Setters/Getters---------------------------*/

}
