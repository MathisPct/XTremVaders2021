package xtremvaders.Objets.BonusJoueur;

import iut.Game;

/**
 * Gestionnaire des bonus actifs dans le jeu
 * Pattern Singleton pour accéder à l'instance depuis n'importe où
 * @author Mathis Poncet
 */
public class BonusManager {
    
    private static BonusManager instance;
    
    /**
     * Indique si un bonus est actuellement actif dans le jeu
     */
    private boolean bonusActif;
    
    /**
     * Constructeur privé pour le pattern Singleton
     */
    private BonusManager() {
        bonusActif = false;
    }
    
    /**
     * Accéder à l'instance unique du manager
     * @return l'instance du BonusManager
     */
    public static BonusManager getInstance() {
        if (instance == null) {
            instance = new BonusManager();
        }
        return instance;
    }
    
    /**
     * Vérifie si un bonus est actif
     * @return true si un bonus est actuellement actif
     */
    public boolean estBonusActif() {
        return bonusActif;
    }
    
    /**
     * Active un bonus
     */
    public void activerBonus() {
        bonusActif = true;
    }
    
    /**
     * Désactive le bonus actif
     */
    public void desactiverBonus() {
        bonusActif = false;
    }

    /**
     * Lâche un bonus à une position donnée si aucun autre bonus n'est actif
     * @param game le jeu dans lequel ajouter le bonus
     * @param typeBonus le type de bonus à lâcher
     * @param x la position x du bonus
     * @param y la position y du bonus
     */
    public void lacherBonus(Game game, TypeBonus typeBonus, int x, int y) {
        if(!this.estBonusActif() && typeBonus != TypeBonus.AUCUN) {
            Bonus bonus = FabriqueBonus.fabriquerUnBonus(game, x, y, typeBonus);
            game.addItem(bonus);
        }
    }
}