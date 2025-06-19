package xtremvaders.Graphics;


import iut.Game;
import iut.GameItem;
import xtremvaders.Objets.Debris.Debris;
import xtremvaders.Objets.Debris.FabriqueDebris;
import xtremvaders.Objets.Debris.TypeDebris;
import xtremvaders.Runtime.GameRuntime;
import xtremvaders.XtremVaders2021;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lr468444
 */
public class Background extends GameItem {
    private long lifeSpend;
    private long vitesseCiel;
    private int frequenceAsteroid;
    private long timeSinceLastAsteroid; // en ms
    private int frequenceCourante; // en secondes

    public Background(Game g) {
        super(g, "background/background_1", 0, 0);
        this.lifeSpend = 0;
        this.vitesseCiel = 15;
        this.frequenceAsteroid = 6;
        this.timeSinceLastAsteroid = 0;
        this.frequenceCourante = calculerFrequence();
        moveXY(0, -getGame().getHeight());
    }

    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void collideEffect(GameItem gi) {}

    @Override
    public String getItemType() {
        return "background";
    }

    private int calculerFrequence() {
        int vie = XtremVaders2021.getJoueur().getPtVie();
        if (vie <= 30) return 8;
        else if (vie <= 60) return 5;
        else return 3;
    }

    private void spawnAsteroid() {
        Debris debris = FabriqueDebris.fabriquerUnDebris(getGame(), 0, 0, TypeDebris.ASTEROID);
        getGame().addItem(debris);
    }

    @Override
    public void evolve(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);

        // Déplacement du fond
        this.moveXY(0, scaledDt * vitesseCiel / 100);
        if (this.getTop() > 0) {
            moveXY(0, -this.getHeight() / 2);
        }

        // Gestion du spawn d'astéroïdes
        timeSinceLastAsteroid += scaledDt;
        long frequenceMs = frequenceCourante * 1000L;
        if (timeSinceLastAsteroid >= frequenceMs) {
            spawnAsteroid();
            timeSinceLastAsteroid = 0;
            frequenceCourante = calculerFrequence(); // recalcule dynamique
        }
    }
}
