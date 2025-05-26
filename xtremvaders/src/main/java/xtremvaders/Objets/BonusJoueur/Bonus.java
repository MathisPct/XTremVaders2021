package xtremvaders.Objets.BonusJoueur;

import java.util.HashMap;
import java.util.Random;

import iut.BoxGameItem;
import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Entites.VagueInvaders;
import xtremvaders.Jeu.XtremVaders2021;
import xtremvaders.Utilities.RangProba;

/**
 * Le bonus appartenant à un vaisseau
 * @author Mathis Poncet
 */
public abstract class Bonus extends BoxGameItem {
    /**
     * Hashmap ayant pour valeur un bonus associé à une probablité
     */
    private static HashMap<TypeBonus, RangProba> probabilitesBonus;

    /**
     * Vitesse de déplacement d'un bonus
     */
    private double vitesse = 0.2;
    
    /**
     * Utile pour jouer l'animation de débloquage du bonus 
     * seulement à la prmière collision
     */
    private int cptCollision;

    /**
     * Constructeur par initialisation
     * @param aG Le jeu auquel le bonus appartient
     * @param aX L'abcisse à laquelle le bonus apparaît
     * @param aY L'ordonnée à laquelle apparaît le bonus
     */
    public Bonus(Game aG, String sprite ,int aX, int aY) {
        super(aG, sprite, aX, aY);
        probabilitesBonus = new HashMap<>();
        this.cptCollision = 0;
    }

    /**
     * Lorsque le bonus rentre en colision avec le joueur, l'effet de celui-ci
     * est lancé. On réinitialise la liste des bonus possibles en enlevant
     * ceux qui sont déjà présents dans l'inventaire du joueur
     * @param gItem l'item avec lequel le bonus rentre en colision
     */
    @Override
    public void collideEffect(GameItem gItem) {
        if(gItem.getItemType().equals("Joueur")){
            this.cptCollision ++;          
            //Animation du bonus débloqué
            if(this.cptCollision == 1) {
                this.lancerEffet();
                AudioDirector.getInstance().playSFX("newSounds/bonusUnlocked");
                BonusUnlocked animation = new BonusUnlocked(getGame(), getMiddleX(), getMiddleY());
                getGame().addItem(animation);
            }

            if(this.canRemoveItem()) {
                getGame().remove(this);
            }
        }
    }

    @Override
    public String getItemType() {
        return "Bonus";
    }
    
    /**
     * Permet de faire bouger le bonus au cours du temps
     * Si le bonus est en cours, on regarde quand est-ce qu'on met fin à l'effet
     * suivant la valeur dt
     * @param dt le cours du temps 
     */
    @Override
    public void evolve(long dt) {
        bouger(dt);
        if(!XtremVaders2021.getJoueur().estVivant()){
            getGame().remove(this);
        }
    }

    protected abstract boolean canRemoveItem();

    /**
     * Permet au bonus de bouger vers le bas
     * @param dt permet de calculer la vitesse
     */
    public abstract void bouger(long dt);

    /**
     * Génération d'un type de bonus, suivant la probabilité de celui-ci et si il 
     * n'est pas déjà présent dans la vague des invaders
     * @return le type de bonus crée
     */
    public static TypeBonus genererBonus() {
        TypeBonus bonus = TypeBonus.AUCUN;
        initBonus();
        Random r = new Random();
        float nb = r.nextFloat(); //nb entre 0.0 et 1.0
        for (RangProba rang : probabilitesBonus.values()) {
            for (TypeBonus tBonus : probabilitesBonus.keySet()) {
                //si le nombre est compris dans le rang d'un des bonus alors on construit ce bonus
                if(nb >= rang.getMin() && nb <= rang.getMax() && probabilitesBonus.get(tBonus) == rang){
                    boolean present = false;
                    for (TypeBonus b : VagueInvaders.getListeBonus()) {
                        //si le bonus est présent dans la vague des invaders
                        if(b!= TypeBonus.AUCUN && b == tBonus) present = true;
                    }
                    if(!present) bonus = tBonus;
                }
            }
        }
        return bonus;
    }

    /**
     * Initialise les bonus possibles lors d'une vague en les associant avec
     * leur probabilité
     */
    private static void initBonus(){
        probabilitesBonus = new HashMap<>();
        for (TypeBonus b : TypeBonus.values()) {
            probabilitesBonus.put(b, FabriqueProbaBonus.fabriqueRangBonus(b));
        }
    }

    /**
     * Le type du bonus
     */
    public abstract TypeBonus getTypeBonus();

    /**
     * Lance l'effet du bonus.
     * 
     * Cette méthode effectue deux actions principales :
     * 1. Active le bonus en appelant {@link BonusManager#activerBonus()}.
     * 2. Délègue à la méthode abstraite {@link #debutEffet()} pour spécifier
     *    l'effet concret du bonus dans le jeu.
     */
    public void lancerEffet() {
        BonusManager.getInstance().activerBonus();
        this.debutEffet();
    }

    /**
     * Spécifie l'effet que va produire le bonus dans le jeu lorsque le joueur
     * le récupère
     */
    public abstract void debutEffet();

    protected double getVitesse(){
        return vitesse;
    }
}