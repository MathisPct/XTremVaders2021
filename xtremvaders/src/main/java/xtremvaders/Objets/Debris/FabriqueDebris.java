package xtremvaders.Objets.Debris;

import iut.Game;
import xtremvaders.Graphics.Animation.ItemAnime;
import xtremvaders.Graphics.Animation.TypeAnimation;

/**
 * Fabrique a Debris
 * @author David Golay
 */
public class FabriqueDebris {
    
    public static Debris fabriquerUnDebris(Game g, int x, int y, TypeDebris typeDebris){
        Debris debris = null;
        switch(typeDebris){
            case ASTEROID:
                debris = new Asteroid(g, x, y);
                debris.setItemAnime(new ItemAnime(g, "asteroid/asteroid_1", x, y, TypeAnimation.ASTEROID_FULL, debris));
                debris.setResistance(30);
                break;
        }
        return debris;
    }
        
    
}
