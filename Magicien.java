public class Magicien extends Character{

    /*---------------------------Attributs---------------------------*/


    /*---------------------------Constructeur---------------------------*/
    public Magicien(String name){
        this.setName(name);
        this.setAttack(8);
        this.setMaxAttack(15);
        this.setHp(3);
        this.setMaxHp(6);
        this.setAtkGear(new Sort());
        this.setDefGear(new Philtre());
    }


    /*---------------------------Méthodes---------------------------*/


    public void getsHit(Character opponent) {
        this.setHp(this.getHp()-(opponent.getAttack()-this.getDefGear().getValue()));
    }

    /*---------------------------Setters/Getters---------------------------*/

    public int getAttack() {
        return super.getAttack()+this.getAtkGear().getValue();
    }
    public void giveWeapon1(){
        this.setAtkGear(new Sort("Eclair",2));
    }
    public void giveWeapon2(){
        this.setAtkGear(new Sort("Boule de feu",7));
    }




}
