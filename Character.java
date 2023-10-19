public abstract class Character {
    /* Classe de personnage contenant les attributs nécessaires
    ainsi que les méthodes permettant de les utiliser */



    /*---------------------------Attributs---------------------------*/
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int maxAttack;
    private EquipementOffensif atkGear;
    private EquipementDefensif defGear;



    /*---------------------------Constructeur---------------------------*/



    /*---------------------------Méthodes---------------------------*/

    public void getsHit(Character opponent){
        this.hp-=(opponent.getAttack());
    }

    public void heals(int val){
        this.hp+=val;
        if (this.hp>this.maxHp){
            this.hp=this.maxHp;
        }
    }

    public String toString(){
        return "Personnage : "+name+", PV : "+hp+", Attaque : "+attack;
    }
    public abstract void giveWeapon1();
    public abstract void giveWeapon2();



    /*---------------------------Setters/Getters---------------------------*/

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setAtkGear(EquipementOffensif atkGear) {
        this.atkGear = atkGear;
    }

    public void setDefGear(EquipementDefensif defGear) {
        this.defGear = defGear;
    }

    public EquipementOffensif getAtkGear() {
        return atkGear;
    }

    public EquipementDefensif getDefGear() {
        return defGear;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public void setMaxAttack(int maxAttack) {
        this.maxAttack = maxAttack;
    }

}
