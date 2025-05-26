package xtremvaders.Objets.Missiles.Ennemis;

import xtremvaders.Entites.Vaisseau;
import xtremvaders.Graphics.SpritesAnimes.ImpactMissile;
import xtremvaders.Objets.Missiles.Rarity;
import xtremvaders.Objets.Missiles.TypeMissile;
import iut.Game;
import iut.GameItem;

/**
 * Missile par défaut des invader
 * @author Mathis Poncet
 */
public class MissileInvader extends MissileEnnemi{

    public MissileInvader(Game g, int x, int y, Vaisseau v) {
        super(g, "missile/missileAlien1", x, y, v);      
    }

    @Override
    public void collideEffect(GameItem gameItem) {
        super.collideEffect(gameItem); //To change body of generated methods, choose Tools | Templates.
        if(super.getVaisseau().getItemType().contains("Invader"));
    }  
    
    @Override
    public TypeMissile getMissileType() {
        return TypeMissile.INVADERDEFAUT;
    }

    @Override
    public void effetExplosion() {
        /*
        * on crée une instance de impact missile (qui contient un itemAnime) qui permet
        * de générer une animation d'impact
        */
        ImpactMissile impact = new ImpactMissile(getGame(), "missile/missile_impact/missile_impact_", getMiddleX() , getMiddleY() );
        getGame().addItem(impact);
    }

    @Override
    public void deplacement(long dt) {
        //sens de déplacement des missiles
        this.moveDA(dt * getVitesse(), -getRandomDirection() );
        //magnetisme des missiles
        trackPlayerPosition();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public int getDegat(iut.GameItem itemType) {
        return 20;
    }


    
    
}
