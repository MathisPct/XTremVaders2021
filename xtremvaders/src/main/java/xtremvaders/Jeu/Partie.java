package xtremvaders.Jeu;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Entites.BalanceConfig;
import xtremvaders.Entites.GenerateurBoss;
import xtremvaders.Entites.Joueur;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Graphics.Background;

/**
 * Cette classe permet d'initialiser une partie et de relancer une partie
 * En invoquant les différents éléments necessaires.
 * @author David
 */
public class Partie extends GameItem {

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
     * Compteur utile pour effectuer certaines instructions seulment une fois
     * dans le evolve. afin d'éviter de tout refaire en boucle et ainsi d'
     * éviter de générer des actions indesirables
     */
    private int cptIteration;

    private InGameGUI gameGUI;

    public Partie(
        Game g, 
        Joueur joueur
        ) {
        super(g, "transparent", 0, 0);
        //Lancement menu démarrage
        this.cptIteration = 0;
        this.gameGUI = new InGameGUI(g);
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
    public void startNewGame(BalanceConfig config){
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
        gameGUI.startNewGame();
        this.background = new Background(getGame()); 
        this.vagueInvaders = new VagueInvaders(getGame(), 5, 3);
        this.generateurBoss = new GenerateurBoss(getGame());
        getGame().addItem(background);
        getGame().addItem(vagueInvaders);
        getGame().addItem(generateurBoss);
        

        AudioDirector director = AudioDirector.getInstance();
        director.playRandomTrackInRange(125, 200);
    }

    private void saveAndQuit() {
        //save(); //TODO to implement
        System.exit(0);
    }
    
    
    /**
     * Méthode qui fabrique un menu de fin
     */
    private void lancerMenuFin(){
        gameGUI.lancerMenuFin();
    }
}
