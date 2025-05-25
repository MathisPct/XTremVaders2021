package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iut.Game;
import iut.GameItem;
import iut.Vector;
import xtremvaders.Entites.Joueur;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Jeu.Menus.MenuItemClickable;
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
        MouseClickManager clickManager = new MouseClickManager(this, motionManager.getCursor());
        this.addMouseMotionListener(motionManager);
        this.addMouseListener(clickManager);
    }

     List<MenuItemClickable> menuItems;

    public void initMenu() {
    // Coordonnées de base (centré horizontalement, espacé verticalement)
        int baseX = this.getWidth() / 2 - 344 / 2;
        int baseY = this.getHeight() / 2 + 50;

        int ecartY = 80; // espace vertical entre les boutons

        // 1. Bouton : Commencer Partie
        MenuItemClickable boutonCommencer = new MenuItemClickable(
            this,
            "cursor/select", // Remplace avec sprite bouton réel si différent
            baseX, baseY,
            () -> {
                /*
                System.out.println("Action : Nouvelle partie !");
                setNouvellePartie(true);
                setEnterPressed(true); // Simule le comportement clavier existant
                */
            }
        );

        // 2. Bouton : Contrôles
        MenuItemClickable boutonControles = new MenuItemClickable(
            this,
            "cursor/select",
            baseX, baseY + ecartY,
            () -> {
                /*
                System.out.println("Action : Affichage des contrôles !");
                setSousMenuActif(true);
                setEnterPressed(true);
                */
            }
        );

        // 3. Bouton : Quitter
        MenuItemClickable boutonQuitter = new MenuItemClickable(
            this,
            "cursor/select",
            baseX, baseY + ecartY * 2,
            () -> {
                /*
                System.out.println("Action : Quitter le jeu");
                System.exit(0);
                */
            }
        );

        // Ajout des boutons au jeu
        this.addItem(boutonCommencer);
        this.addItem(boutonControles);
        this.addItem(boutonQuitter);

        menuItems = new ArrayList<>();
        menuItems.add(boutonCommencer);
        menuItems.add(boutonControles);
        menuItems.add(boutonQuitter);
    }

    public List<MenuItemClickable> getAllMenuItems() {
        return menuItems;
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
        
        joueur = new Joueur(this, 0.35d);
        this.partie = new Partie(this, joueur);
        initMenu();
        setupControls();
        setupDifficulty();
        
        
        this.addItem(joueur);
        this.addItem(partie);
    }

    protected void setupDifficulty() {
        joueur.setEstActionFreeze(true);
        joueur.setPtVie(3);
    }


    protected void setupControls() {
        if(kMouseMode==true) {
            initMouseCursor();
        }

        // Give onPress callback to player, 
        // -> he can pause menu
        joueur.setOnPressEscape(() -> partie.lancerMenuPause());
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