package xtremvaders.Entites;

import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import iut.Game;
import iut.GameItem;
import xtremvaders.Directors.AudioDirector;

/**
 * Le générateur de boss ne s'affiche pas. Il sert à créer un boss suivant 
 * le nombre de vague qui se sont déroulées
 * @author Mathis Poncet
 */
public class GenerateurBoss extends GameItem{

    private  ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // frequence apparition boss
    private int frequence;
    
    private Boss boss;   
    
    public GenerateurBoss(Game game) {
        super(game, "", -1, -1);
        this.frequence = 4;
    }

    @Override
    public void draw(Graphics grphcs) throws Exception {

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
        return "GénérateurBoss";
    }

    @Override
    public void evolve(long dt) {

    }

    ///TODO faire comme dans la classe background
    public void bossSpawnRoutine() {
        frequence = calculerFrequence();

        scheduler.schedule(() -> {
            System.out.println("bossSpawnRoutine() à " + java.time.LocalTime.now());
            spawnBoss();
            // Relancer avec la fréquence ajustée
            bossSpawnRoutine();
        }, frequence, TimeUnit.SECONDS);
    }

    
    private void spawnBoss() {
        AudioDirector.getInstance().playSFX("newSounds/bossSpawn");
        this.boss = new Boss(getGame(), getGame().getWidth()/2, 5, 0.15, 60);
        getGame().addItem(boss);
    }


    private int calculerFrequence() {
        return 30; //seconds
    }

    
    /**
     * Méthode qui génère le boss et l'ajoute au jeu
     */
    private void genererBoss(){
        AudioDirector.getInstance().playSFX("newSounds/bossSpawn");
        this.boss = new Boss(getGame(), getGame().getWidth()/2, 5, 0.15, 60);
        getGame().addItem(boss);
    }
}
