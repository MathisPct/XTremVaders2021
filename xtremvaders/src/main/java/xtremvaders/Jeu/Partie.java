package xtremvaders.Jeu;

import iut.Game;
import iut.GameItem;
import xtremvaders.Entites.GenerateurBoss;
import xtremvaders.Entites.Joueur;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Gameplay.Balance.BalanceConfig;
import xtremvaders.Graphics.Background;
import xtremvaders.Graphics.HUD.InGameGUI;

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


    private final InGameGUI gameGUI;

    public Partie(
        Game g, 
        Joueur joueur
        ) {
        super(g, "transparent", 0, 0);
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
        gameGUI.onGameStarted();
        this.background = new Background(getGame());
        this.vagueInvaders = new VagueInvaders(getGame(), 5, 3);
        this.generateurBoss = new GenerateurBoss(getGame());
        this.generateurBoss.bossSpawnRoutine();
        getGame().addItem(background);
        getGame().addItem(vagueInvaders);
        getGame().addItem(generateurBoss);
    }
}
