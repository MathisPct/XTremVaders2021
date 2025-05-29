package xtremvaders.Graphics.Animation.AnimatedSprites;

/**
 * @author David Golay
 * @author Mathis Poncet
 */
public class ExplosionSprite {
    
    public static int totalSprite(){
        return 12;
    }
    
    /**
     * Methode permettant de récupérer le bon filepath pour l'explosion
     * @deprecated classe ralisée avant le systeme d'animation. donc méthode dépréciée
     * @param sprite numero du sprite que l'on désire récupérer
     * @return 
     */
    public static String getSprite(int sprite) {
        int maxIndex = totalSprite();
        int clampedIndex = Math.min(sprite, maxIndex);
        return String.format("explosion1/explosion1_%05d", clampedIndex);
    }

}
