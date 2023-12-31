package Personnages;

import Defensif.EquipementDefensif;
import Exceptions.FightOverByDeathOf;
import Offensif.EquipementOffensif;
import Tiles.Potion;
import Tiles.Tile;

import java.util.ArrayList;

public abstract class Characters {
    /* Classe de personnage contenant les attributs nécessaires
    ainsi que les méthodes permettant de les utiliser */



    /*---------------------------Attributs---------------------------*/
    private final String name;
    private final String type;
    private int hp;
    private int maxHp;
    private int attack;
    private int maxAttack;
    private EquipementOffensif atkGear;
    private EquipementDefensif defGear;
    private int emplacement;
    private ArrayList<Tile> plateau;
    private int hero_id;
    private int plateau_id;


    /*---------------------------Constructeur---------------------------*/

    public Characters (String nom, String type){
        this.name=nom;
        this.type=type;
    }

    /*---------------------------Méthodes---------------------------*/

    public void getsHit(Characters opponent) {
        this.setHp(this.getHp()-(opponent.getAttack()-this.getDefGear().getValue()));
        System.out.println(opponent.getName()+" vous attaque attaque pour "+opponent.getAttack());
        System.out.println(this.getName()+"'s HP : "+this.getHp());
    }

    public void heals(Potion potion){
        this.hp+=potion.getValue();
        if (this.hp>this.maxHp){
            this.hp=this.maxHp;
        }
    }

    public boolean canEquip(EquipementOffensif stuff){
        EquipementOffensif actualStuff =this.getAtkGear();
        if (actualStuff.getGearType().equals(stuff.getGearType())&&stuff.getValue()>actualStuff.getValue()){
            return true;
        }
        return false;
    }

    public void playFightTurn(Characters truc)throws FightOverByDeathOf {
        truc.getsHit(this);
        if (truc.getHp()<=0){
            throw new FightOverByDeathOf(truc);
        }
    }

    public String toString(){
        return "Personnage : "+name+", PV : "+hp+", Attaque : "+attack;
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

    public String getType() {
        return type;
    }

    public int getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(int emplacement) {
        this.emplacement = emplacement;
    }

    public ArrayList<Tile> getPlateau() {
        return plateau;
    }

    public void setPlateau(ArrayList<Tile> plateau) {
        this.plateau = plateau;
    }

    public int getHero_id() {
        return hero_id;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public int getPlateau_id() {
        return plateau_id;
    }

    public void setPlateau_id(int plateau_id) {
        this.plateau_id = plateau_id;
    }
}
