package xtremvaders.Directors;

import iut.Game;
import xtremvaders.Entites.GenerateurBoss;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Graphics.Background;

public class DirectorCommands implements  IDirectorCommands {

    private Game game;
    
    /**
     * Attibut permettant de générer les vagues d'invaders
     */
    private VagueInvaders vagueInvaders;   
    
    /**
     * Attribut permettant de générer le boss
     */
    private GenerateurBoss generateurBoss;

    /**
     * Fond étoilé du jeu
     * Ce fond génére aussi les débris
     */
    private Background background;

    public DirectorCommands(Game g) {
        this.game = g;
    }

    public void onGameStarted() {
        //ITEM DE JEU
        if(vagueInvaders != null){
            game.remove(vagueInvaders);
        }
        if(generateurBoss != null){
            game.remove(generateurBoss);
        }
        if(background != null){
            game.remove(background);
        }

        this.vagueInvaders = new VagueInvaders(game, 5, 3);
        this.generateurBoss = new GenerateurBoss(game);
        //this.generateurBoss.bossSpawnRoutine();
        this.background = new Background(game);

        game.addItem(background);
        game.addItem(vagueInvaders);
        game.addItem(generateurBoss);

        this.spawnWave();
    }


    @Override
    public void spawnWave() {
        vagueInvaders.spawnWave();
    }

    @Override
    public void spawnBoss() {
        generateurBoss.spawnBoss();
    }

    @Override
    public boolean getIsBossActive() {
        return generateurBoss.getIsBossActive();
    }

    @Override
    public int getEnemiesOnScreen() {
       return vagueInvaders.getEnemiesOnScreen();
    }

}