package xtremvaders.Graphics.Animation.AnimatedSprites;


import iut.Game;
import iut.GameItem;
import xtremvaders.Graphics.Animation.ItemAnime;
import xtremvaders.Graphics.Animation.TypeAnimation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class ImpactShield extends GameItem {
    //attribut utile pour l'animation d'imapact sur le bouclier
    private ItemAnime itemAnime;

    public ImpactShield(Game g, String name, int _x, int _y) {
        super(g, name, _x, _y);
        this.itemAnime = new ItemAnime(g, "explosion1/explosion1_00000", _x, _y, TypeAnimation.EXPLOSION1, this);
        moveXY(itemAnime.centerSpriteX(), itemAnime.centerSpriteY());
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
        return "impactShield";
    }

    @Override
    public void evolve(long dt) {
        itemAnime.playAnimation(dt);
    }
}
