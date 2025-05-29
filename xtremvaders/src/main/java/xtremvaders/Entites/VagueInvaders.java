package xtremvaders.Entites;


import java.awt.Graphics;
import java.util.ArrayList;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Jeu.GameRuntime;
import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Output.StylizedLogger;
import xtremvaders.Utilities.Direction;
import xtremvaders.Utilities.EtatMouvement;
import xtremvaders.Utilities.InvaderBoundaryListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe générer une vague d'invaders
 * @author Mathis Poncet
 */
public class VagueInvaders extends GameItem implements InvaderBoundaryListener {
    /**
     * Nomre de lignes d'invaders
     */
    private final int nbSurLigne;
    /**
     * Nombre de colonne d'invader
     */
    private final int nbSurCol;
    /**
     * Permet de connaître le nombre de vagues qui se sont déjà déroulés dans le
     * jeu
     */
    private static int nbVagues;
    /**
     * Contient les invaders de la vague
     */
    private static ArrayList<Invader> invaders;
    /**
     * Direction horizontale courante des invaders (GAUCHE ou DROITE)
     */
    private Direction directionHorizontale;
    /**
     * État actuel du mouvement des invaders
     */
    private EtatMouvement etatMouvement;

    /**
     * Liste des bonus présents dans la vague des invaders
     */
    private static ArrayList<TypeBonus> listeBonus;
    
    /**
     * Constructeur par initialisation
     * @param g le jeu 
     * @param nbSurLigne nombre de lignes d'invaders
     * @param nbSurCol nombre de colonnes d'invaders
     */
    public VagueInvaders(Game g, int nbSurLigne, int nbSurCol) {
        super(g, "", -1, -1);
        invaders = new ArrayList<>();
        this.etatMouvement = EtatMouvement.DEPLACEMENT_HORIZONTAL;
        this.nbSurCol = nbSurCol;
        this.nbSurLigne = nbSurLigne;
        this.directionHorizontale = Direction.DROITE;
        nbVagues = 0;
    }

    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }
    
    @Override
    public void draw(Graphics g) throws Exception {
    
    }
    
    @Override
    public void collideEffect(GameItem gi) {
        
    }

    @Override
    public String getItemType() {
        return "VagueInvaders";
    }
    
    @Override
    public void evolve(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);

        //génération de la vague s'il n'y a pas d'invaders sur la carte
        if(invaders.isEmpty()){
            this.genererVague();
        }
        bouger(scaledDt * Invader.getVitesseInvaders());
    }
    
    /**
     * Génére les invaders par ligne et colonne suivant le nombre de ligne voulue
     * et le nombre de colonne voulue. A chaque génération, le nombre de vague est
     * incrémenté. Les invaders générés sont ajoutés à la liste des invaders de
     * la vague
     */
    public void genererVague(){
        nbVagues++;
        StylizedLogger.printWaveAnnouncement(nbVagues);
        AudioDirector.getInstance().playSFX("newSounds/newWave");
        //initialisation de la liste des bonus de la vague
        listeBonus = new ArrayList<>();
        this.directionHorizontale = Direction.DROITE;
        this.etatMouvement = EtatMouvement.DEPLACEMENT_HORIZONTAL;

        // GENERATION DES INVADERS DE LA VAGUE
        for (int l = 0; l < this.nbSurLigne; l++) {
            for (int c = 0; c < this.nbSurCol; c++) {
                Invader invader = FabriqueInvader.fabriquerUnInvader(getGame(),64 + l*64, 0 + c*64, TypeInvader.NORMAL, vitesseVague());//new Invader(getGame(),"cold_un" ,64 + l*64, 0 + c*64);
                invader.setBoundaryListener(this);
                invaders.add(invader);
                super.getGame().addItem(invader);
            }
        }
    }
    
    /**
     * Permet de savoir le nombre de vagues qui se sont passées
     * @return le nombre de vagues qui se sont déroulés
     */
    public static int getNbVagues() {
        return nbVagues;
    }
    
    /**
     * Permet aux invaders de bouger vers le bas
     */
    private void bougerBas(double vitesse){
        for (Invader invader : invaders) {
            invader.bouger(Direction.BAS, vitesse);
        }
    }
    
    /**
     * Permet au groupe d'invader de bouger latéralement dans la direction courante
     */
    private void bougerLateralement(double vitesse){
        for (Invader invader : invaders) {
            invader.bouger(directionHorizontale, vitesse);
        }
    }
    
    /**
     * Permet de faire bouger les invaders selon leur état de mouvement actuel
     * @param vitesse des invaders
     */
    public void bouger(double vitesse){
        switch(etatMouvement) {
            case DESCENTE:
                bougerBas(vitesse);
                etatMouvement = EtatMouvement.DEPLACEMENT_HORIZONTAL;
                bougerLateralement(vitesse);
                break;
            case DEPLACEMENT_HORIZONTAL:
                bougerLateralement(vitesse);
                break;
        }
    }
    
    public static ArrayList<Invader> getInvaders() {
        return invaders;
    }
    
    /**
     * Permet de retirer un invader de la liste
     * @param invader retire l'invader de la liste quand il est mort
     */
    public static void retirerInvader(Invader invader){
        invaders.remove(invader);
    }    

    /**
     * Augmente la vitesse en fonction du nombre de vague
     */
    public static double vitesseVague(){
        return Math.pow(-6.0, -5 * (nbVagues * nbVagues)) + 0.0107 * nbVagues + 0.1251;
    }
    
    public static ArrayList<TypeBonus> getListeBonus() {
        return listeBonus;
    }
    
    public static void ajouterBonus(TypeBonus b){
        listeBonus.add(b);
    }

    /**
     * Méthode appelée lorsqu'un invader touche un bord de l'écran
     * Implémentation de l'interface InvaderBoundaryListener
     *
     * @param invader L'invader qui a touché le bord
     * @param direction La direction du bord touché (GAUCHE ou DROITE)
     */
    @Override
    public void onBoundaryCollision(Invader invader, Direction direction) {
        if (etatMouvement != EtatMouvement.DESCENTE) {
            etatMouvement = EtatMouvement.DESCENTE;
            
            if (direction == Direction.DROITE) {
                this.directionHorizontale = Direction.GAUCHE;
            } else if (direction == Direction.GAUCHE) {
                this.directionHorizontale = Direction.DROITE;
            }
        }
    }
}
