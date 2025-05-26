package xtremvaders.Objets.BonusJoueur.temporal;

import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Objets.Missiles.TypeMissile;
import iut.Game;

/**
 * Bonus de tir que le joueur peut avoir. En fonction du type du bonus tir, 
 * le joueur pourra tirer avec différents types de missile
 */
public abstract class BonusTir extends BonusTemporel {
    /**
     * Constructeur par initialisation
     * @param aX Abcisse où apparaît le bonus
     * @param aY Ordonnée où apparaît le bonus
     */
    public BonusTir(Game aG, String sprite ,int aX, int aY) {
        super(aG, sprite, aX, aY);
    }

    public String getItemType() {
        return "BonusTir";
    }

    /**
     * Permet au bonus de bouger vers le bas
     */
    @Override
    public void bouger(long dt) {
        this.moveDA(dt * getVitesse(), -90);
    }

    /**
     * Lance l'effet que va produire le bonus dans le jeu lorsque le joueur le récupère
     */
    public abstract void debutEffet();

    /**
     * Le type du bonus
     */
    public abstract TypeBonus getTypeBonus();

    /**
     * Le type de missile que l'on va pouvoir avoir grâce à ce bonus
     */
    public abstract TypeMissile getTypeBonusTir();
}