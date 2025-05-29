package xtremvaders.Graphics.Animation.AnimatedSprites;

import iut.Game;
import iut.GameItem;
import xtremvaders.Graphics.Animation.ItemAnime;
import xtremvaders.Graphics.Animation.TypeAnimation;
import xtremvaders.Utilities.Utilite;

/**
 * Cette classe permet de générer une explosion quand un invader décède de ses 
 * blessures de guerres intergallactiques
 * @author dg447135
 */
public class EnnemiExplosion extends GameItem {  
    //attribut utile pour l'animation d'explosion des suites de la mort d'un invader
    private ItemAnime itemAnime;
    
    public EnnemiExplosion(Game g, String name, int _x, int _y) {
        super(g, name, _x, _y);
        this.itemAnime = new ItemAnime(g, name, _x, _y, TypeAnimation.EXPLOSION1, this);
        moveXY(itemAnime.centerSpriteX(), itemAnime.centerSpriteY());
        setAngle(Utilite.randomBetweenRange(0, 259));
    }


    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void collideEffect(GameItem gi) {
    }

    @Override
    public String getItemType() {
        return "explosion";
    }

    @Override
    public void evolve(long dt) {
        itemAnime.playAnimation(dt);
    }    
}
