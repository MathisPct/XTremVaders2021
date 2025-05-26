package xtremvaders.Objets.BonusJoueur.temporal;

import iut.Game;
import xtremvaders.Objets.BonusJoueur.Bonus;
import xtremvaders.Objets.BonusJoueur.BonusManager;

public abstract class BonusTemporel extends Bonus {
    /**
     * Durée pendant laquelle le joueur aura le bonus
     */
    protected long dureeEffet;

    private boolean isEffetTermine = false;

    /**
     * Constructeur par initialisation
     *
     * @param aG     Le jeu auquel le bonus appartient
     * @param sprite
     * @param aX     L'abcisse à laquelle le bonus apparaît
     * @param aY     L'ordonnée à laquelle apparaît le bonus
     */
    public BonusTemporel(Game aG, String sprite, int aX, int aY) {
        super(aG, sprite, aX, aY);
    }

    @Override
    public void evolve(long dt) {
        super.evolve(dt);

        this.dureeEffet -= dt;
        if(this.dureeEffet <= 0) {
            this.isEffetTermine = true;
        }

        if(this.isEffetTermine()) {
            this.finEffet();
            BonusManager.getInstance().desactiverBonus();
            getGame().remove(this);
        }
    }

    /**
     * Spécifie ce qu'il se passe à la fin de l'effet
     */
    public abstract void finEffet();

    @Override
    public boolean canRemoveItem() {
        return this.isEffetTermine();
    }

    protected boolean isEffetTermine() {
        return this.isEffetTermine;
    }
}
