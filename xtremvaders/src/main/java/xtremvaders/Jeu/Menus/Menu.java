/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtremvaders.Jeu.Menus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;

/**
 *
 * @author David
 */
public abstract class Menu extends GameItem implements KeyListener{
    
    //Type du menu
    private TypeMenu type;
    
    //représente le choix fait par l'utilisateur
    private int choice;
    //représente le nombre d'item de menu du menu principal
    private int nbItemMenu;
    private boolean enterPressed;
    
    // utile pour ne lancer qu'une fois les actions dans le evolve
    private int cptIteration;
    
    // booleans utiles pour savoir quel menu est actif
    private boolean menuPrincipalActif;
    private boolean sousMenuActif;
    
    private boolean nouvellePartie;
    
    public Menu(Game g, double _x, double _y) {
        super(g, "taito", _x, _y);
        this.choice             = 0;
        this.nbItemMenu         = 3;
        this.cptIteration       = 0;  
        this.enterPressed       = false;      
        this.menuPrincipalActif = true;
        this.sousMenuActif      = false;
        this.nouvellePartie     = false;
    }
    
    public abstract TypeMenu getTypeMenu();
    
    public abstract void choiceMade();
    
    public abstract void scenePrincipale();
       
    public abstract void sceneSecondaire();
    
    /**
     * cette méthode normalise la variable qui determine 
     * le choix de l'utilisateur en fonction du nombre d'item du menu
     */
    public void normaliseChoice(){
        if(this.choice < 0) {
            this.choice = nbItemMenu - 1;
        }
        else if(this.choice> nbItemMenu-1){
            this.choice = 0;
        }
    }
    
    /**
     * Cette méthode est appelé systematiquement dans evolve, 
     * afin d'afficher le bon sprite de menu à l'écran
     */
    public void updateSpriteMenu(){       
        if(menuPrincipalActif){
            scenePrincipale();
        } else if (sousMenuActif) {
            sceneSecondaire();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) choice--;
        else if (key == KeyEvent.VK_DOWN) choice++;
        else if (key == KeyEvent.VK_ENTER) enterPressed = true;
        else if (key == KeyEvent.VK_ESCAPE && sousMenuActif) {
            sousMenuActif = false;
            menuPrincipalActif = true;
            scenePrincipale();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getNbItemMenu() {
        return nbItemMenu;
    }

    public void setNbItemMenu(int nbItemMenu) {
        this.nbItemMenu = nbItemMenu;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }

    public int getCptIteration() {
        return cptIteration;
    }

    public void setCptIteration(int cptIteration) {
        this.cptIteration = cptIteration;
    }

    public boolean isMenuPrincipalActif() {
        return menuPrincipalActif;
    }

    public void setMenuPrincipalActif(boolean menuPrincipalActif) {
        this.menuPrincipalActif = menuPrincipalActif;
    }

    public boolean isSousMenuActif() {
        return sousMenuActif;
    }

    public void setSousMenuActif(boolean sousMenuActif) {
        this.sousMenuActif = sousMenuActif;
    }

    public boolean isNouvellePartie() {
        return nouvellePartie;
    }

    public void setNouvellePartie(boolean nouvellePartie) {
        this.nouvellePartie = nouvellePartie;

        if(this.nouvellePartie == false) return;

        AudioDirector director = AudioDirector.getInstance();

        // Récupère les pistes correspondant à la plage de BPM
        director.playRandomTrackInRange(125, 200);
    }
}
