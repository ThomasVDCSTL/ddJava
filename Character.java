public class Character {
    /* Classe de personnage contenant les attributs nécessaires
    ainsi que les méthodes permettant de les utiliser */



    /*---------------------------Attributs---------------------------*/
    private String name;
    private int hp;
    private int attack;
    private String classe;
    private EquipementOffensif atkGear;
    private EquipementDefensif defGear;

    /*---------------------------Constructeur---------------------------*/

    public Character(String name, String classe,int hp, int atk){
        this.name=name;
        this.hp=hp;
        this.classe =classe;
        this.attack=atk;
        this.atkGear=new EquipementOffensif(classe);
        this.defGear=new EquipementDefensif(classe);
    }

    /*---------------------------Méthodes---------------------------*/

    public void getsHit(Character opponent){
        this.hp-=(opponent.getAttack()-this.defGear.getValue());
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
        return attack+this.atkGear.getValue();
    }

    public EquipementOffensif getAtkGear() {
        return atkGear;
    }

    public EquipementDefensif getDefGear() {
        return defGear;
    }

    public String getClasse() {
        return classe;
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

    public void setClasse(String classe) {
        this.classe = classe;
        this.atkGear=new EquipementOffensif(classe);
        this.defGear= new EquipementDefensif(classe);
    }

    public void setAtkGear(EquipementOffensif atkGear) {
        this.atkGear = atkGear;
    }

    public void setDefGear(EquipementDefensif defGear) {
        this.defGear = defGear;
    }
}
