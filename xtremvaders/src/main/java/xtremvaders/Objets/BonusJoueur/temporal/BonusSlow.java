package xtremvaders.Objets.BonusJoueur.temporal;

import xtremvaders.Entites.Invader;
import xtremvaders.Entites.VagueInvaders;
import iut.Game;
import xtremvaders.Objets.BonusJoueur.TypeBonus;

import java.util.Random;

/**
 * Bonus ralentissant toute la vague des invaders
 * @author Mathis Poncet
 */
public class BonusSlow extends BonusTemporel {
    /**
     * Ancienne vitesse que les invaders possédaient
     */
    private double ancienneVitesse;
    
    /**
     * Constructeur par initialisation
     * @param g le jeu auquel appartient le bonus
     * @param x abcisse à laquelle le bonus va apparaître
     * @param y ordonnée à laquelle le bonus va apparaîttre
     */
    public BonusSlow(Game g, int x, int y) {
        super(g, "bonus/itemsBonus/bonusSlow", x, y);
        Random r = new Random();
        this.dureeEffet = r.nextInt(2000)+ 3000; //entre 3s et 5s de ralentissement des ennemis
    }

    @Override
    public String getItemType() {
        return "BonusSlow";
    }

    @Override
    public void bouger(long dt) {
        moveDA(dt * getVitesse(), -90);
    }

    @Override
    public TypeBonus getTypeBonus() {
        return TypeBonus.SLOW;
    }
    
    /**
     * L'effet est lancée et diminue la vitesse des invaders
     */
    @Override
    public void debutEffet() {
        this.changeSprite("transparent");
        this.ancienneVitesse = VagueInvaders.vitesseVague();
        Invader.setVitesseInvaders(0.08);
    }
    
    /**
     * A la fin du bonus, on reset la vitesse
     */
    @Override
    public void finEffet() {
        Invader.setVitesseInvaders(ancienneVitesse);
    }
}
