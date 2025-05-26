package xtremvaders.Objets.BonusJoueur.temporal;

import xtremvaders.Jeu.XtremVaders2021;
import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Objets.Missiles.TypeMissile;
import iut.Game;
import java.util.Random;

/**
 * Bonus permettant au joueur d'avoir un missile rapide lorsqu'il attrape 
 * ce bonus
 */
public class BonusMissileRapide extends BonusTir {

    public BonusMissileRapide(Game aG, int aX, int aY) {
        super(aG, "bonus/itemsBonus/bonusSpeed", aX, aY);
        Random r = new Random();
        this.dureeEffet = r.nextInt(10000)+ 5000; //entre 5s et 15s d'effet
    }
    
    /**
     * On change le type de missile que possède le joueur
     */
    @Override
    public void debutEffet() {
        XtremVaders2021.getJoueur().setTypeMissile(TypeMissile.RAPIDE);
        this.changeSprite("transparent");
    }
    
    /**
     * On réintialise le type de missile du joueur
     */
    @Override
    public void finEffet() {
        XtremVaders2021.getJoueur().setTypeMissile(TypeMissile.NORMAL);
    }

    @Override
    public TypeMissile getTypeBonusTir() {
        return TypeMissile.RAPIDE;
    }

    @Override
    public TypeBonus getTypeBonus() {
        return TypeBonus.MISSILERAPIDE;
    }    
}