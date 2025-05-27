package xtremvaders.Utilities;

import xtremvaders.Entites.Invader;

/**
 * Interface pour observer les collisions des invaders avec les bords de l'écran
 * @author Mathis Poncet
 */
public interface InvaderBoundaryListener {
    /**
     * Méthode appelée lorsqu'un invader touche un bord de l'écran
     * 
     * @param invader L'invader qui a touché le bord
     * @param direction La direction du bord touché (GAUCHE ou DROITE)
     */
    void onBoundaryCollision(Invader invader, Direction direction);
}
