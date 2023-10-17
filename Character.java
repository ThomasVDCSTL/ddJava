public class Character {
    String name;
    int hp;
    int attack;
    EquipementOffensif atkGear;
    EquipementDefensif defGear;

    public Character(String name,int hp, int atk, String classe){
        this.name=name;
        this.hp=hp;
        this.attack=atk;
        this.atkGear=new EquipementOffensif(classe);
        this.defGear=new EquipementDefensif(classe);
    }
}
