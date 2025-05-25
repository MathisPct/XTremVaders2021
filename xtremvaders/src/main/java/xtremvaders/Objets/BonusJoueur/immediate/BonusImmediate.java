package xtremvaders.Objets.BonusJoueur.immediate;

import iut.Game;
import xtremvaders.Objets.BonusJoueur.Bonus;
import xtremvaders.Objets.BonusJoueur.BonusManager;

public abstract class BonusImmediate extends Bonus {
    /**
     * Constructeur par initialisation
     *
     * @param aG     Le jeu auquel le bonus appartient
     * @param sprite
     * @param aX     L'abcisse à laquelle le bonus apparaît
     * @param aY     L'ordonnée à laquelle apparaît le bonus
     */
    public BonusImmediate(Game aG, String sprite, int aX, int aY) {
        super(aG, sprite, aX, aY);
    }

    @Override
    public boolean canRemoveItem() {
        BonusManager.getInstance().desactiverBonus();
        return true;
    }
}
