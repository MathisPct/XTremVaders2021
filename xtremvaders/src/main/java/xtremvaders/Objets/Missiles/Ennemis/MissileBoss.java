package xtremvaders.Objets.Missiles.Ennemis;

import iut.Game;
import iut.GameItem;
import xtremvaders.Entites.Vaisseau;
import xtremvaders.Graphics.SpritesAnimes.EnnemiExplosion;
import xtremvaders.Graphics.VFX.ItemAnime;
import xtremvaders.Graphics.VFX.TypeAnimation;
import xtremvaders.Jeu.GameRuntime;
import xtremvaders.Objets.Missiles.TypeMissile;

/**
 *
 * @author David
 */
public class MissileBoss extends MissileEnnemi {
    //missile du boss animé
    private ItemAnime itemAnime;

    public MissileBoss(Game aG, int aX, int aY, Vaisseau vaisseau) {
        super(aG, "cold1", aX, aY, vaisseau);
        this.itemAnime = new ItemAnime(aG, "missile/missile_boss/missile_boss_00001", (int)aX, (int)aY, TypeAnimation.MISSILE_BOSS, this); 
    }

    @Override
    public void collideEffect(GameItem gameItem) {
        super.collideEffect(gameItem);      
    }

    @Override
    public TypeMissile getMissileType() {
        return TypeMissile.MISSILE_BOSS;
    }

    @Override
    public void effetExplosion() {
        // creation d'une explosion
        EnnemiExplosion explosionItem = new EnnemiExplosion(getGame(), "explosion1/explosion1_00000", getMiddleX(), getMiddleY());
        getGame().addItem(explosionItem);
    }

    @Override
    public void deplacement(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);

        itemAnime.loopAnimation(dt, 30);
        this.moveDA(scaledDt * getVitesse(), -getRandomDirection() );
        this.trackPlayerPosition(scaledDt);
    }

    @Override
    public int getDegat(iut.GameItem itemType) {
        return 50;
    }
    
}
