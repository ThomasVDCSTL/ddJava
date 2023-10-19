public class Enemy extends Character implements Tile{


    /*---------------------------Attributs---------------------------*/
    public int id;
    /*---------------------------Constructeur---------------------------*/
    public Enemy(String name, int pv, int atk,int Id){
        this.setAttack(atk);
        this.setName(name);
        this.setHp(pv);
        this.setAtkGear(new Arme());
        this.setDefGear(new Bouclier());
        this.id=Id;
    }

    /*---------------------------Méthodes---------------------------*/
    public void giveWeapon1(){
        this.setAtkGear(new Sort("Rine",0));
    }
    public void giveWeapon2(){
        this.setAtkGear(new Sort("Rine",0));
    }
    public String triggerEvent() {
        return "bagarre";
    }
    /*---------------------------Setters/Getters---------------------------*/

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
