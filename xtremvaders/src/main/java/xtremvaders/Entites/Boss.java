package xtremvaders.Entites;

import java.util.Random;

import iut.Game;
import iut.GameItem;
import xtremvaders.Graphics.Animation.Animation;
import xtremvaders.Graphics.Animation.ItemAnime;
import xtremvaders.Graphics.Animation.TypeAnimation;
import xtremvaders.Objets.Missiles.FabriqueMissile;
import xtremvaders.Objets.Missiles.Missile;
import xtremvaders.Objets.Missiles.TypeMissile;
import xtremvaders.Runtime.GameRuntime;
import xtremvaders.Utilities.Direction;
import xtremvaders.Utilities.Utilite;

/**
 * Le boss du jeu qui apparaît à partir de la 7ème vague
 * @author Mathis Poncet
 */
public class Boss extends Vaisseau{
    /**
     * Le côté ou aller
     */
    private Direction coteOuAller;
    
    /**
     * Temps avant que le boss tire
     * A son apparition, il attend 3s avant de tirer son premier missile
     */
    private long tempsAvantTirer = 2000; 
    
    /**
     * Utile pour l'animation du boss
     */
    private ItemAnime itemAnime;
    
    /**
     * Utile pour générer le nombre de missile à la mort de boss
     */
    private static int agressivite = 10;

    private Runnable onGotKilled;
    
    
    /**
     * Par défaut à droite
     * @param g le jeu
     * @param x abssice de départ
     * @param y ordonnée de départ
     * @param vitesse vitesse de base
     * @param resistance la résistance du boss
     */
    public Boss(Game g, int x, int y, double vitesse, int resistance) {
        super(g, "alienGreen3", x, y, vitesse);
        this.coteOuAller = Direction.DROITE;
        super.setPtVie(resistance);
        this.itemAnime = new ItemAnime(g, "", x, y, TypeAnimation.ALIEN_FATHER, this);
        itemAnime.setAnimationType(TypeAnimation.ALIEN_FATHER);
        //agressivite = 
    }

    public void setOnGotKilled(Runnable runnable) {
        onGotKilled = runnable;
    }

    @Override
    public void tirer() {
        Missile m = FabriqueMissile.fabriquerUnMissile(getGame(), getMiddleX()-30, getMiddleY()-20, TypeMissile.MISSILE_BOSS, this);
        getGame().addItem(m);
    }

    @Override
    public String getItemType() {
        return "Boss"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void collideEffect(GameItem g) {
        if(g.getItemType().equals("MissileJoueur")){
            Missile m = (Missile) g;
            this.setPtVie(getPtVie() - m.getDegat(this));
            if(this.estVivant() == false) {
                onGotKilled.run();
            }
        }
    }

    /**
     * Permet de faire bouger le boss de droite à gauche 
     * @param dt le cours du temps
     */
    public void bougerBoss(long dt){  
        if(coteOuAller == Direction.DROITE && this.getRight() < getGame().getWidth()) this.moveDA(dt * 0.15, 0);
        else coteOuAller = Direction.GAUCHE;
        if(coteOuAller == Direction.GAUCHE && this.getLeft() > 0) this.moveDA(dt * 0.15, 180);
        else coteOuAller = Direction.DROITE;
    }
    
    /**
     * Le boss se déplace de droite vers la gauche et tire à une cadence aléatoire
     * entre 5s et 10s
     * @param dt le cours du temps
     */
    @Override
    public void evolve(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);
        super.evolve(dt);
        itemAnime.loopAnimation(scaledDt+Utilite.randomBetweenRange(0, 120) * scaledDt,  Animation.getAnimationSpeed());
        bougerBoss(scaledDt);
        tempsAvantTirer -= scaledDt;
        if(tempsAvantTirer <= 0){
            tirer();
            Random r = new Random();
            tempsAvantTirer= r.nextInt(5000) + 5000; //entre 5s et 10s
        }
        //si le boss n'est plus vivant

    } 
    
    /**
     * Methode appelé a la mort du boss pour qu'il balance des missiles magnétisés
     */
    public void tirerMissileMortel(){
        Missile m = null;
        int nbMissile = VagueInvaders.getNbVagues()*2;
        for(int i=0; i<agressivite; i++){
            m = FabriqueMissile.fabriquerUnMissile(getGame(), getMiddleX()-30, getMiddleY()-20, TypeMissile.INVADERDEFAUT, this);
            m.setRandomDirection(40, 140);
            m.setCoefficientMagnetisme(Utilite.randomBetweenRange(0, 50));
            m.setVitesse(0.20);
            getGame().addItem(m);
        }
    }
}
