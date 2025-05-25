package xtremvaders.Objets.BonusJoueur.immediate;

import xtremvaders.Jeu.XtremVaders2021;
import xtremvaders.Objets.BonusJoueur.BonusManager;
import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Objets.Shields.ShieldBasic;
import iut.Game;
import iut.GameItem;

/**
 * @author David Golay
 */
public class BonusShield extends BonusImmediate {

    public BonusShield(Game aG, int aX, int aY) {
        super(aG, "bonus/itemsBonus/bonusShield", aX, aY);
    }
    
    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public String getItemType() {
        return "bonusShield";
    }

    @Override
    public void bouger(long dt) {
        moveDA(dt * getVitesse(), -90);   
    }

    @Override
    public TypeBonus getTypeBonus() {
        return null;
    }

    @Override
    public void debutEffet() {
        //magnétisme du bonus au joeur
        int cordX = XtremVaders2021.getJoueur().getMiddleX();
        int cordY = XtremVaders2021.getJoueur().getMiddleY();
        //initialisation du shield
        XtremVaders2021.getJoueur().initShield(new ShieldBasic(getGame(), cordX, cordY, this));
    }


    public void finEffet() {
        BonusManager.getInstance().desactiverBonus();
    }
}
