package xtremvaders.Entites;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import iut.Game;
import iut.GameItem;
import xtremvaders.Directors.AudioDirector;
import xtremvaders.Graphics.Animation.AnimatedSprites.BossExplosion;

/**
 * Le générateur de boss ne s'affiche pas. Il sert à créer un boss suivant 
 * le nombre de vague qui se sont déroulées
 * @author Mathis Poncet
 */
public class GenerateurBoss extends GameItem {

    private List<Boss> bosses;   
    
    public GenerateurBoss(Game game) {
        super(game, "", -1, -1);
        this.bosses = new ArrayList<>();
    }

    @Override
    public void draw(Graphics grphcs) throws Exception {
        // Ne rien dessiner, c'est un objet logique
    }

    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void collideEffect(GameItem gi) {
        // Aucun effet de collision
    }

    @Override
    public String getItemType() {
        return "GénérateurBoss";
    }

    @Override
    public void evolve(long dt) {
        // Peut-être gérer ici le timing de spawn
    }
    
    public void spawnBoss() {
        AudioDirector.getInstance().playSFX("newSounds/bossSpawn");
        Boss newBoss = new Boss(
            getGame(), 
            getGame().getWidth()/2,
            5, 
            0.15,
            60
        );
        newBoss.setOnGotKilled(() -> {
            removeBoss(newBoss);
        });
        bosses.add(newBoss); //TODO implement removal corrctely
        getGame().addItem(newBoss);
    }

    public List<Boss> getBosses() {
        return bosses;
    }

    public void removeBoss(Boss boss) {
        System.out.println("Removing boss from generator");
        BossExplosion explosion = new BossExplosion(getGame(), this.getMiddleX(), this.getMiddleY());
        AudioDirector.getInstance().playSFX("newSounds/bossDeath");
        getGame().addItem(explosion);
        boss.tirerMissileMortel();
        getGame().remove(this);
              
        bosses.remove(boss);
        getGame().remove(boss);
    }

    public boolean getIsBossActive() {
        return bosses.isEmpty() == false;
    }


}
