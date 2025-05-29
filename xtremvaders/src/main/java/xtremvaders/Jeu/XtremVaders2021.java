package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import iut.Game;
import iut.GameItem;
import iut.Vector;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Entites.BalanceConfig;
import xtremvaders.Entites.BalanceConfigFactory;
import xtremvaders.Entites.BalanceConfigFactory.DifficultyLevel;
import xtremvaders.Entites.Joueur;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Input.GameInputHandler;
import xtremvaders.Jeu.Menus.CursorItem;
import xtremvaders.Jeu.Menus.MouseClickManager;
import xtremvaders.Jeu.Menus.MouseMotionManager;
import xtremvaders.Output.StylizedLogger;

/**
 * ReprÃ©sente un petit jeu simple
 * @author aguidet
 */
public class XtremVaders2021 extends Game {
    public static String kBuildVersion = "2.0.0";
    //Debuging logs
    public static boolean kDebugPauseMode = false; //false in release
    public static boolean kDebugGameControls = true; //false in release

    public static boolean kLargeMode = false; // true in release when operationnal
    public static boolean kHitBoxDisplay = false; 
    public static boolean kGameCursor = true; 

    public static boolean kDisableMusic = true; //false in release
    public static boolean kDisableSfx = true; // false

    //Gameplay related
    GameSpeed gameSpeed;
    
    private static Joueur joueur;
    private Partie partie;
    private BalanceConfig difficulty;
   
    //Event related
    MouseMotionManager motionManager;
    MouseClickManager clickManager; 
    CursorItem mousecursor;

    //Menu
    private MainMenuPanel mainMenu;
    private PausePanel pauseMenu;

    private final GameInputHandler gameInputHandler;
    
    
    /**
     * @param aArgs the command line arguments Fonction principal (main)
     * Fonction principale du jeu
     */
    public static void main(String[] aArgs) {

        int width = 1024;
        int height = 800;

        if(kLargeMode == true) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = screenSize.width - 200;
            height = screenSize.height - 100;
        }

        // Tu peux ensuite créer ton jeu avec cette taille, par exemple :
        XtremVaders2021 jeu = new XtremVaders2021(width, height);
        jeu.play();
        jeu.setFocusable(true);
        jeu.requestFocusInWindow();
    }

    /**
     * Initialise le jeu
     * @param width la largeur de l'écran
     * @param height la hauteur de l'écran
     */
    public XtremVaders2021(int width, int height) {
        super(width, height, "XtremeVaders");
        StylizedLogger.printGameLaunch(kBuildVersion, "buildChanges: ");

        //Debug hitboxes
        GameItem.DRAW_HITBOX=kHitBoxDisplay;
         //Keyboard mapping atm
        gameInputHandler = new GameInputHandler(this);
        this.addItem(gameInputHandler); //TODO add remove to clear memory
        GameRuntime.init(new GameSpeed());
        AudioDirector.getInstance().onLaunchGame();

        ///
        difficulty = BalanceConfigFactory.createConfig(BalanceConfigFactory.getCurrentDifficulty());
        joueur = new Joueur(
            this, 
            0.35d, 
            difficulty.getTimeBeforeNextShotMs()
        );
        gameInputHandler.addActionListener(joueur);


    }

    /**
     * Crée les items au début du jeu
     * appelée par game.play()
     */
    @Override
    protected void createItems() { 
        ensureControlsInitialized();
        showMainMenu();
    }

    private void startNewGame() {
        // Give onPressEscape callback to player, 
        // -> he can pause menu
        // TODO on devrait lui passer des controls ou un ensemble d'action  implementer
        // ca ne dvrait pas etre le joueur qui porte de Keyboard listener, 
        // mais plutot un GameMediator ou autre router d'action
        joueur.setOnPressEscape(() -> pauseGame());
        joueur.setEstActionFreeze(true);
        joueur.setPtVie(3);

        gameInputHandler.notifyStof();

        this.partie = new Partie(
            this, 
            joueur
        );
        this.addItem(joueur);
        this.addItem(partie);
        joueur.resetJoueur();
        this.partie.startNewGame(difficulty);
        hideCursor();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * 
     */
    protected void pauseGame(){
        System.out.println("pause game");
        if(kDebugPauseMode == false) {
            showPauseMenu();
        }
        GameRuntime.getGameSpeed().pause(); 
    }

    /**
     * 
     */
    private void resumeGame() {
        joueur.setEstActionFreeze(false);
        GameRuntime.getGameSpeed().resume();
         AudioDirector.getInstance().onResumeGame();
    }


    protected GameSpeed getGameSpeed() {
        return gameSpeed;
    }

    protected void ensureControlsInitialized() {
        //Initializing cursor
        if(kGameCursor==true) {
            initGameCursor();
        }
    }

    protected void hideCursor() {
        System.out.println("MAIN: Removing GameItem Cursor");
        this.remove(motionManager.getCursor());
    }

    public void initGameCursor() {
        mousecursor = new CursorItem(this, "cursor/cursor", 200, 200);
        this.addItem(mousecursor); 

        motionManager = new MouseMotionManager(this, mousecursor);
        clickManager = new MouseClickManager(
            motionManager.getCursor(), 
            (event, cursor) -> {
                this.onMouseClicked(event, cursor);
            }
        );
        this.addMouseMotionListener(motionManager);
        this.addMouseListener(clickManager);
    }

    //  showMainMenu montre le menu d'en
    public void showMainMenu() {
        if (mainMenu == null) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            mainMenu = new MainMenuPanel(
                180, 
                getWidth(), 
                getHeight()
                
            );

            mainMenu.setStartGameCallback(() -> {
                System.out.println("Lancement du jeu !");
                mainMenu.setVisible(false);
                mainMenu.setFocusable(false);
                startNewGame();
                this.setFocusable(true);
                this.requestFocusInWindow();
            });



            if (frame != null) {
                mainMenu.setBounds(0, 0, getWidth(), getHeight());
                mainMenu.setOpaque(false);
                mainMenu.setVisible(true);
                frame.getLayeredPane().add(mainMenu, JLayeredPane.MODAL_LAYER);
                frame.getLayeredPane().revalidate();
                frame.getLayeredPane().repaint();
            }
        } else {
            mainMenu.setVisible(true);
        }
    }

    private void affectDifficulty(DifficultyLevel difficulty) {

    }

    //  showMainMenu montre le menu d'en
    public void showPauseMenu() {
        if (pauseMenu == null) {
            int modaleWidth = getWidth() - 700;
            int modaleHeight = getHeight() - 600;
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            pauseMenu = new PausePanel(0, modaleWidth, modaleHeight); // ← 🎯 Décalage vertical des boutons de menu ici

            // callback de resume game
            pauseMenu.setResumeGameCallback(() -> {
                System.out.println("Reprise de la partie");
                pauseMenu.setVisible(false);
                pauseMenu.setFocusable(false);
                resumeGame();
                this.setFocusable(true);
                this.requestFocusInWindow();
            });


            if (frame != null) {
                int x = (getWidth() - modaleWidth) / 2;
                int y = (getHeight() - modaleHeight) / 2;
                pauseMenu.setBounds(x, y, modaleWidth, modaleHeight);

                pauseMenu.setOpaque(false);
                pauseMenu.setVisible(true);
                frame.getLayeredPane().add(pauseMenu, JLayeredPane.POPUP_LAYER);
                frame.getLayeredPane().revalidate();
                frame.getLayeredPane().repaint();
            }
        } else {
            pauseMenu.setVisible(true);
        }
    }


    public void onMouseClicked(MouseEvent e, CursorItem cursor) {
       //boolean menuItemWasClicked = mainMenu.isCollidingWithCursor(cursor);
       //System.out.print("menuItemWasClicked: " + menuItemWasClicked);
    }

    @Override
    protected void drawBackground(Graphics g) {
        // Dessin du fond du jeu
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }


    /**
     * Appelée lorsque le joueur a perdu la partie
     */
    @Override
    protected void lost() {
        JOptionPane.showMessageDialog(this, "Vous avez perdu! \nVous ne posséder plus de vie");      
    }

    /**
     * Appelée lorsque le joueur a perdu la partie
     */
    @Override
    protected void win() {
        JOptionPane.showMessageDialog(this, "Et c'est gagné!!!");
    }

    @Override
    protected boolean isPlayerWin() {
        // bon courage ...
        return VagueInvaders.getNbVagues() > 100;
    }

    @Override
    protected boolean isPlayerLost() {
        // et oui, le joueur ne perd jamais
        // il est bloqué dans une boucle infinie
        return false;
    }

    /**
     * gets the gravity of the Game
     */
    @Override
    public Vector getGravity() {
        return null;
    }   

    public static Joueur getJoueur() {
        return joueur;
    }

    public static void setJoueur(Joueur joueur) {
        XtremVaders2021.joueur = joueur;
    }
}