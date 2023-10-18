public class Magicien extends Character{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Magicien(String name){
        super(name,6,15);
        super.setAtkGear(new Sort());
        super.setDefGear(new Philtre());
    }


    /*---------------------------Méthodes---------------------------*/
    public void getsHit(Character opponent){
        super.setHp(super.getHp()-(opponent.getAttack()-super.getDefGear().getValue()));
    }


    /*---------------------------Setters/Getters---------------------------*/

    public int getAttack() {
        return super.getAttack()+super.getAtkGear().getValue();
    }




}
