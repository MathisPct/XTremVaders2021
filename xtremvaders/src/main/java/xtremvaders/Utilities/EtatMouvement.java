package xtremvaders.Utilities;

/**
 * Représente les différents états de mouvement possibles pour un groupe d'invaders
 * @author Mathis Poncet
 */
public enum EtatMouvement {
    /**
     * Les invaders se déplacent horizontalement (gauche ou droite)
     */
    DEPLACEMENT_HORIZONTAL,
    
    /**
     * Les invaders commencent à descendre suite à une collision avec un bord
     */
    DESCENTE
}
