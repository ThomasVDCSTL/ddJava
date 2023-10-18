public class Arme extends EquipementOffensif{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Arme(){
        super("Guerrier");
    }
    public Arme(String type,int dmg){
        super("Guerrier");
        super.setName(type);
        super.setValue(dmg);
    }


    /*---------------------------Méthodes---------------------------*/



    /*---------------------------Setters/Getters---------------------------*/

}
