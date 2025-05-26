package xtremvaders.Jeu;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Entites.GenerateurBoss;
import xtremvaders.Entites.Joueur;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Graphics.Background;
import xtremvaders.Graphics.Dashboard.HealthBar;
import xtremvaders.Graphics.Dashboard.ScoreBar;
import xtremvaders.Jeu.Menus.FabriqueMenu;
import xtremvaders.Jeu.Menus.Menu;
import xtremvaders.Jeu.Menus.TypeMenu;

/**
 * Cette classe permet d'initialiser une partie et de relancer une partie
 * En invoquant les différents éléments necessaires.
 * @author David
 */
public class Partie extends GameItem {

    /**
     * Menu de démarrage et menu de fin de partie
     */
    private Menu menu;
    
    /**
     * Fond étoilé du jeu
     * Ce fond génére aussi les débris
     */
    private Background background;
    
    /**
     * Attibut permettant de générer les vagues d'invaders
     */
    private VagueInvaders vagueInvaders;   
    
    /**
     * Attribut permettant de générer le boss
     */
    private GenerateurBoss generateurBoss;
    
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
    
    /**
     * Compteur utile pour effectuer certaines instructions seulment une fois
     * dans le evolve. afin d'éviter de tout refaire en boucle et ainsi d'
     * éviter de générer des actions indesirables
     */
    private int cptIteration;

    public boolean paused;
    
    public Partie(Game g, Joueur joueur) {
        super(g, "transparent", 0, 0);
        //Lancement menu démarrage
        this.cptIteration = 0;
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
        return "partie";       
    }

    @Override
    public void evolve(long l) {       
        
    }
    
    /**
    * Cette méthode est appelée pour générer les items du jeu 
    * (sauf le joueur déja créé dans la classe du jeu)
    */
    public void startNewGame(){
        //ITEM DE JEU
        if(vagueInvaders != null){
            getGame().remove(vagueInvaders);
        }
        if(generateurBoss != null){
            getGame().remove(generateurBoss);
        }
        if(background != null){
            getGame().remove(background);
        }
        if(scoreFin != null){
            scoreFin.removeItems();
            getGame().remove(scoreFin);
        }
        this.background = new Background(getGame()); 
        this.vagueInvaders = new VagueInvaders(getGame(), 5, 3);
        this.generateurBoss = new GenerateurBoss(getGame());
        getGame().addItem(background);
        getGame().addItem(vagueInvaders);
        getGame().addItem(generateurBoss);
        
        //INTERFACE JOUEUR 
        healthBar = new HealthBar(getGame(), 0, 0);
        scoreBar = new ScoreBar(getGame(), 0, 30);
        // getGame().addItem(healthBar); TODO: rajouter images dashboard manquante
        getGame().addItem(scoreBar);
        scoreBar.initItems(true);     
    }

    private void endGame() {

    }

    private void saveAndQuit() {
        //save(); //TODO to implement
        System.exit(0);
    }

     /**
     * Methode qui fabrique un menu de pause in game
     */
    protected void pauseGame(){
        if(GameRuntime.getGameSpeed().isPaused()) { // déjà en pause ? on resume
            GameRuntime.getGameSpeed().resume();
            return;
        }
        GameRuntime.getGameSpeed().pause();
        paused = true; //TODO to remove
        //this.menu = FabriqueMenu.FabriquerUnMenu(getGame(), TypeMenu.PAUSE);
        //getGame().addItem(menu);
        getGame().remove(background);    
        AudioDirector.getInstance().onPauseMenuOpened();
 
    }

    
    
    /**
     * Méthode qui fabrique un menu de fin
     */
    private void lancerMenuFin(){
        scoreFin = new ScoreBar(getGame(), 385, 180);
        getGame().addItem(scoreFin);
        getGame().remove(scoreBar);
        scoreBar.removeItems();
        getGame().remove(healthBar);
        scoreFin.initItems(false);
        this.menu = FabriqueMenu.FabriquerUnMenu(getGame(), TypeMenu.FIN);
        getGame().addItem(menu);
    }

    /*
    private void oldEvolve() {
        //LANCEMENT DU JEU
        //si le menu est de type démarrage et peut lancer une nouvelle partie
        if(menu != null && menu.isNouvellePartie()){
            System.out.println("LANCEMENT D'UNE PREMIERE PARTIE");
            menu.setNouvellePartie(false);
            getGame().remove(menu);
            startNewGame();
            XtremVaders2021.getJoueur().resetJoueur();
        }
        
        //FIN DE PARTIE
        //Si le joueur est mort
        if(!XtremVaders2021.getJoueur().estVivant()) {
            this.cptIteration ++;
            //LANCEMENT MENU FIN DE PARTIE [1 seule fois]
            if(cptIteration == 1){
                System.out.println("JOUEUR MORT");
                XtremVaders2021.getJoueur().setEstActionFreeze(true);
                lancerMenuFin();              
            }
            if(menu.getTypeMenu()==TypeMenu.FIN){
                if(menu.isNouvellePartie()){
                    System.out.println("LANCEMENT D'UNE NOUVELLE PARTIE");
                    getGame().remove(background);
                    menu.setNouvellePartie(false);
                    XtremVaders2021.getJoueur().resetJoueur();
                    startNewGame();
                    cptIteration = 0;
                }
            }
        }
    }
     */
}
