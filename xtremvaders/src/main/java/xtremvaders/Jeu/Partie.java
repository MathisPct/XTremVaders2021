package xtremvaders.Jeu;

import iut.Game;
import iut.GameItem;
import xtremvaders.Directors.AIDirector;
import xtremvaders.Directors.DirectorCommands;
import xtremvaders.Directors.EmotionnalState;
import xtremvaders.Directors.GameState;
import xtremvaders.Entites.Joueur;
import xtremvaders.Gameplay.Balance.BalanceConfig;
import xtremvaders.Graphics.HUD.InGameGUI;

/**
 * Cette classe permet d'initialiser une partie et de relancer une partie
 * En invoquant les différents éléments necessaires.
 * @author David
 */
public class Partie extends GameItem {

    private final AIDirector director;
    private final DirectorCommands directorCommands;
    private final GameState gameState;
    private final EmotionnalState emotionnalState;
    private final InGameGUI gameGUI;

    public Partie(
        Game g, 
        Joueur joueur
        ) {
        super(g, "transparent", 0, 0);
        this.gameGUI = new InGameGUI(g);
        emotionnalState = new EmotionnalState(1);
        gameState = new GameState();
        directorCommands = new DirectorCommands(g);
        director = new AIDirector(gameState, emotionnalState, directorCommands);
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
        director.update(l);
    }
    
    /**
    * Cette méthode est appelée pour générer les items du jeu 
    * (sauf le joueur déja créé dans la classe du jeu)
    */
    public void startNewGame(BalanceConfig config){
        gameGUI.onGameStarted();
        directorCommands.onGameStarted();
    }
}
