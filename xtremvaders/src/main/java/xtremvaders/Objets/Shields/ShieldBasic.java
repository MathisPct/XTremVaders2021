package xtremvaders.Objets.Shields;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import iut.Game;
import iut.GameItem;
import xtremvaders.Graphics.SpritesAnimes.ImpactShield;
import xtremvaders.Graphics.SpritesAnimes.ShieldSprite;
import xtremvaders.Objets.BonusJoueur.immediate.BonusShield;

/**
 *
 * @author David
 */
public class ShieldBasic extends Shield {

    public ShieldBasic(Game g, double _x, double _y, BonusShield bonusShield) {
        super(g, _x, _y-60, 4, bonusShield);
    }

    @Override
    public void collideEffect(GameItem gi) {       
        if(gi.getItemType().equals("MissileEnnemi")){
            getGame().remove(gi);
            // creation d'une explosion
            ImpactShield impactShield = new ImpactShield(getGame(), "explosion1/explosion1_00001", gi.getMiddleX(), gi.getMiddleY());
            getGame().addItem(impactShield);
            this.setCptImpact(this.getCptImpact()+1);
            if(isBroken() || isLiveTimeOver()){
                getGame().remove(this);
            }
            selectSprite();
        }
    }

    @Override
    public String getItemType() {
        return "BouclierBasique";
    }

    /**
     * Methode qui change le sprite en fonction de la resistance du shield
     */
    private void selectSprite(){ 
        double ratio = ShieldSprite.totalSprite()/getResistance();
        int sprite = (int)Math.round((double)getCptImpact()*ratio);       
        this.changeSprite(ShieldSprite.getSprite(sprite));
    }
    
}
