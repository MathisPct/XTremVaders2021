package xtremvaders.Entites;

import java.util.Random;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Graphics.SpritesAnimes.EnnemiExplosion;
import xtremvaders.Graphics.VFX.Animation;
import xtremvaders.Graphics.VFX.ItemAnime;
import xtremvaders.Jeu.GameRuntime;
import xtremvaders.Jeu.XtremVaders2021;
import xtremvaders.Objets.BonusJoueur.Bonus;
import xtremvaders.Objets.BonusJoueur.BonusManager;
import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Objets.Missiles.Missile;
import xtremvaders.Utilities.Direction;
import xtremvaders.Utilities.InvaderBoundaryListener;
import xtremvaders.Utilities.Utilite;

/**
 * Classe général des invaders
 * @author Mathis Poncet
 */
public abstract class Invader extends Vaisseau{
   /**
    * Le nombre des invaders en vie
    */
    private static int nombreEnnemi = 0;
    
    /**
     * Missile du vaisseau
     */
    private Missile missile;
    
    /**
     * Le temps maximum avant qu'un tir puisse être produit par le vaisseau
     */
    private long tempAvantTir;
    
    /**
     * L'invader possède un type de bonus définit à sa création 
     * Lorsque le joueur le tue, si le type du bonus est différent de aucun
     * un nouveau bonus est lâché par l'invader
     */
    private final TypeBonus typeBonus;
    
    /**
     * La vitesse des invaders
     */
    private static double vitesseInvaders;
    
    private ItemAnime itemAnime;   
    
    /**
     * Listener pour les collisions avec les bords
     */
    private InvaderBoundaryListener boundaryListener;
    
    /**
     * Constructeur par initialisation
     * @param g le jeu 
     * @param sprite de l'invader
     * @param x l'abcisse de départ
     * @param vitesse de l'invader
     * @param y l'ordonnée de départs
     */
    public Invader(Game g, String sprite,int x, int y, double vitesse) {
        super(g, "alienGreen1", x, y, vitesse);
        Invader.nombreEnnemi ++;
        Random r = new Random();
        vitesseInvaders = vitesse;
        tempAvantTir = r.nextInt(5000)+3000; //entre 3s à 8s
        // création du bonus
        this.typeBonus = Bonus.genererBonus();
        VagueInvaders.ajouterBonus(typeBonus);
    }
    
    /**
     * Définit le listener pour les collisions avec les bords
     * @param listener Le listener à notifier
     */
    public void setBoundaryListener(InvaderBoundaryListener listener) {
        this.boundaryListener = listener;
    }
    
    /**
     * Effets des collisions avec le joueur et le missile
     * @param gi l'item avec qui l'ennemi est en collision
     */
    @Override
    public void collideEffect(GameItem gi) {
        if(gi.getItemType().equals("MissileJoueur")){
            Missile m = (Missile) gi;
            setPtVie(getPtVie() - m.getDegat(this));
        }  
        //effet de la propagation de l'explosion de la bombe nuke
        if(gi.getItemType().equals("explosioNuke")){
            setPtVie(getPtVie() - 100);
        }
        if(!estVivant()){          
            
            //suppression de l'ennemi
            AudioDirector.getInstance().playSFX("newSounds/explosion");
            getGame().remove(this);
            VagueInvaders.retirerInvader(this);     
            
            // le vaisseau meurt
            // creation d'une explosion
            EnnemiExplosion explosionItem = new EnnemiExplosion(getGame(), "explosion1/explosion1_00000", getMiddleX(), getMiddleY());
            getGame().addItem(explosionItem);
            
            nombreEnnemi --;
            XtremVaders2021.getJoueur().addScore();
            
            BonusManager.getInstance().lacherBonus(getGame(), typeBonus, getMiddleX(), getMiddleY());
        }
    }

    @Override
    public String getItemType() {
        return "Invader";
    }

    public static int getNombreEnnemi() {
        return nombreEnnemi;
    }

    @Override
    public void evolve(long dt) {
        super.evolve(dt);
        long scaledDt = GameRuntime.getScaledDt(dt);

        itemAnime.loopAnimation(
            scaledDt + Utilite.randomBetweenRange(0, 120),
            Animation.getAnimationSpeed()
        );

        checkBoundaryCollision();

        tempAvantTir -= scaledDt;

        if (tempAvantTir <= 0) {
            tirer();

            float timeSpeed = GameRuntime.getGameSpeed().getTimeSpeed();
            float minDelay = 3000.0f * timeSpeed;
            float maxDelay = 10000.0f * timeSpeed; // 3000 + 10000 (entre 3 à 10 secondes)

            tempAvantTir = (long) randomBetweenRange((long) minDelay, (long) maxDelay);
        }
    }

    /**
     * Vérifie si l'invader touche un bord de l'écran et notifie le listener
     */
    private void checkBoundaryCollision() {
        Direction direction = coteTouche();
        if (direction != null && boundaryListener != null) {
            boundaryListener.onBoundaryCollision(this, direction);
        }
    }

    // Méthode utilitaire //TODO à externaliser
    public static long randomBetweenRange(long min, long max) {
        if (max <= min) return min; // éviter erreurs
        return min + (long)(Math.random() * (max - min));
    }

    
    /**
     * Permet de savoir quel côté a été touché par un invader
     * @return le côté qui a été touché
     */
    public Direction coteTouche(){
        Direction res = null;
        if(this.getRight() <= this.getWidth()){
            res = Direction.GAUCHE;
        }else if(this.getLeft() >= getGame().getWidth() - this.getWidth()){
            res = Direction.DROITE;
        }
        return res;
    }
    
    /**
     * Permet de bouger un invader selon des composantes x et y de vitesse
     * @param vx la vitesse horizontale
     * @param vy la vitesse verticale
     */
    public void bouger(double vx, double vy) {
        moveXY(vx, vy);
    }
    
    /**
     * Permet à l'invader de tirer un missile
     */
    @Override
    public abstract void tirer();
      
    public abstract TypeInvader getTypeInvader();
    
    public static double getVitesseInvaders() {
        return vitesseInvaders;
    }

    public static void setVitesseInvaders(double vitesseInvaders) {
        Invader.vitesseInvaders = vitesseInvaders;
    }

    public void setItemAnime(ItemAnime itemAnime) {
        this.itemAnime = itemAnime;
        moveXY(itemAnime.centerSpriteX(), itemAnime.centerSpriteY());
    }

    public void setMissile(Missile missile) {
        this.missile = missile;
    }

}
