package xtremvaders.Graphics.HUD;

import iut.Game;

public class InGameGUI {
        /**
     * Attribut permettant d'afficher la barre de score en haut à gauche
     * pendant la partie
     */
    private ScoreBar scoreBar;
    
    /**
     * Attribut permettant d'afficher la barre de vie en cours de partie
     */
    private HealthBar healthBar;

    /**
     * Attribut permettant d'afficher le score au centre de l'ecran 
     * en fin de partie
     */
    private ScoreBar scoreFin;

    private Game game;
     
    public InGameGUI(Game g) {
        this.game = g;
    }

    public  void onGameStarted() {
        if(scoreFin != null){
            scoreFin.removeItems();
            game.remove(scoreFin);
        }

        //INTERFACE JOUEUR 
        healthBar = new HealthBar(game, 0, 0);
        scoreBar = new ScoreBar(game, 0, 30);
        // getGame().addItem(healthBar); TODO: rajouter images dashboard manquante
        game.addItem(scoreBar);
        scoreBar.initItems(true);
    }


     /**
     * Méthode qui fabrique un menu de fin
     */
    protected  void lancerMenuFin(){
        scoreFin = new ScoreBar(game, 385, 180);
        game.addItem(scoreFin);
        game.remove(scoreBar);
        scoreBar.removeItems();
        game.remove(healthBar);
        scoreFin.initItems(false);
    }
}
