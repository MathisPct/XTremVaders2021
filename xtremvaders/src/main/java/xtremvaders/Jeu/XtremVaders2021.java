package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import iut.Game;
import iut.GameItem;
import iut.Vector;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Entites.Joueur;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Jeu.Menus.CursorItem;
import xtremvaders.Jeu.Menus.MouseClickManager;
import xtremvaders.Jeu.Menus.MouseMotionManager;

/**
 * ReprÃ©sente un petit jeu simple
 * @author aguidet
 */
public class XtremVaders2021 extends Game {

    private boolean kDebugMode = true;
    private boolean kMouseMode = true;

    /**
     * Joueur qui est initialisé au départ
     */
    private static Joueur joueur;
    
    private Partie partie;
   
    MainMenu mainMenu;

    
    
    /**
     * @param aArgs the command line arguments Fonction principal (main)
     * Fonction principale du jeu
     */
    public static void main(String[] aArgs) {
        XtremVaders2021 jeu = new XtremVaders2021(1024, 800);
        jeu.play();
    }

    public void initMouseCursor() {
        // Curseur par défaut du système
        MouseMotionManager motionManager = new MouseMotionManager(this);
        MouseClickManager clickManager = new MouseClickManager(
            motionManager.getCursor(), 
            (event, cursor) -> {
                this.onMouseClicked(event, cursor);
            }
        );
        this.addMouseMotionListener(motionManager);
        this.addMouseListener(clickManager);
    }

    public void onMouseClicked(MouseEvent e, CursorItem cursor) {
       boolean menuItemWasClicked = mainMenu.isCollidingWithCursor(cursor);
       System.out.print("menuItemWasClicked: " + menuItemWasClicked);
    }

    public void launchMainMenu() {
        mainMenu = new MainMenu(
            this,
            () -> {
                spawnPlayer();
                this.partie.nouvellePartie();

                AudioDirector director = AudioDirector.getInstance();
                director.playRandomTrackInRange(125, 200);
                
            }
        );

        mainMenu.spawnMainMenu();
    }

   
    public void spawnPlayer() {
        joueur = new Joueur(this, 0.35d);
        this.partie = new Partie(this, joueur);
        setupDifficulty();
        this.addItem(joueur);
        this.addItem(partie);
        joueur.resetJoueur();

        // Give onPress callback to player, 
        // -> he can pause menu
        joueur.setOnPressEscape(() -> partie.lancerMenuPause());
    }


    /**
     * Initialise le jeu
     * @param width la largeur de l'écran
     * @param height la hauteur de l'écran
     */
    public XtremVaders2021(int width, int height) {
        super(width, height, "XtremeVaders");
        //Debug hitboxes
        GameItem.DRAW_HITBOX=kDebugMode;


    }
    /**
     * Crée les items au début du jeu
     */
    @Override
    protected void createItems() { 
        launchMainMenu();
        ensureControlsInitialized();
    }

    protected void setupDifficulty() {
        joueur.setEstActionFreeze(true);
        joueur.setPtVie(3);
    }


    protected void ensureControlsInitialized() {
        //Initializing cursor
        if(kMouseMode==true) {
            initMouseCursor();
        }
    }

    protected void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(00, 0, getWidth(), getHeight());
    }

    /**
     * Appelée lorsque le joueur a perdu la partie
     */
    protected void lost() {
        JOptionPane.showMessageDialog(this, "Vous avez perdu! \nVous ne posséder plus de vie");      
    }

    /**
     * Appelée lorsque le joueur a perdu la partie
     */
    protected void win() {
        JOptionPane.showMessageDialog(this, "Et c'est gagné!!!");
    }

    protected boolean isPlayerWin() {
        // bon courage ...
        return VagueInvaders.getNbVagues() > 100;
    }

    protected boolean isPlayerLost() {
        // et oui, le joueur ne perd jamais
        // il est bloqué dans une boucle infinie
        return false;
    }

    /**
     * gets the gravity of the Game
     */
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