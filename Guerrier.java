public class Guerrier extends Character{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Guerrier(String name) {
        super(name, 10, 10);
        super.setAtkGear(new Arme());
        super.setDefGear(new Bouclier());
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