package xtremvaders.Objets.BonusJoueur.temporal;

import xtremvaders.XtremVaders2021;
import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Objets.Missiles.TypeMissile;
import iut.Game;
import java.util.Random;

/**
 * Bonus permettant au joueur d'avoir un bonus nuke quand il l'attrappe
 * @author Mathis Poncet
 */
public class BonusNuke extends BonusTir {

    public BonusNuke(Game aG, int x, int y) {
        super(aG, "bonus/itemsBonus/bonusNuke", x, y);
        Random r = new Random();
        this.dureeEffet = r.nextInt((10000)+ 5000); //entre 10s et 15s d'effet
    }
    
    @Override
    public void debutEffet() {
        XtremVaders2021.getJoueur().setTypeMissile(TypeMissile.NUKE);
        this.changeSprite("transparent");
    }

    @Override
    public void finEffet() {
        XtremVaders2021.getJoueur().setTypeMissile(TypeMissile.NORMAL);
    }

    @Override
    public TypeBonus getTypeBonus() {
        return TypeBonus.NUKE;
    }

    @Override
    public TypeMissile getTypeBonusTir() {
        return TypeMissile.NUKE;
    }
    
}
