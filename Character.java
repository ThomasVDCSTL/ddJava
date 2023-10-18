public class Character {
    /* Classe de personnage contenant les attributs nécessaires
    ainsi que les méthodes permettant de les utiliser */



    /*---------------------------Attributs---------------------------*/
    private String name;
    private int hp;
    private int attack;
    private EquipementOffensif atkGear;
    private EquipementDefensif defGear;



    /*---------------------------Constructeur---------------------------*/

    public Character(String name,int hp, int atk){
        this.name=name;
        this.hp=hp;
        this.attack=atk;
    }

    /*---------------------------Méthodes---------------------------*/

    public void getsHit(Character opponent){
        this.hp-=(opponent.getAttack());
    }

    public void heals(int val){
        this.hp+=val;
    }



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
}
