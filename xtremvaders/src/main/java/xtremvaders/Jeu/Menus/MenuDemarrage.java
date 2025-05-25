package xtremvaders.Jeu.Menus;

import iut.Game;
import iut.GameItem;

/**
 *
 * @author David
 */
public class MenuDemarrage extends Menu {   
    
    private MenuAnime menuAnime;

    public MenuDemarrage(Game g, double _x, double _y) {
        super(g, _x, _y);
        this.menuAnime = new MenuAnime(getGame(), 0, 0);
        getGame().addItem(menuAnime);
    }

    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void collideEffect(GameItem gi) {}

    @Override
    public String getItemType() {
        return "menu_principal";
    }

    @Override
    public void evolve(long l) {
        incrementIteration();
        normaliseChoice();
        updateSpriteMenu();

        if (isEnterPressed()) {
            handleEnterPress();
        }
    }

    private void incrementIteration() {
        setCptIteration(getCptIteration() + 1);
    }

    private void handleEnterPress() {
        choiceMade();

        // On ne veut exécuter ces actions qu'une seule fois, d'où la vérification cptIteration == 0
        if (isNouvellePartie() && getCptIteration() == 0) {
            resetEnterPressed();
            cleanUpMenu();
        } else if (isSousMenuActif() && getCptIteration() == 0) {
            resetEnterPressed();
            sceneSecondaire();
        }
    }

    private void resetEnterPressed() {
        setEnterPressed(false);
    }

    private void cleanUpMenu() {
        getGame().remove(menuAnime);
        getGame().remove(this);
    }

    @Override
    public void choiceMade() {
        setCptIteration(0);  // On reset l’itération à chaque nouveau choix

        switch (getChoice()) {
            case 0:  // Nouvelle partie
                setSousMenuActif(false);
                setNouvellePartie(true);
                break;
            case 1:  // Menu commandes (sous-menu)
                setSousMenuActif(true);
                setMenuPrincipalActif(false);
                break;
            case 2:  // Quitter
                System.exit(0);
                break;
            default:
                // Cas par défaut (optionnel)
                break;
        }      
    }

    @Override
    public void sceneSecondaire() {
        this.changeSprite("menus/menu_commandes/menu_commandes");
    }

    @Override
    public void scenePrincipale() {
        String basePath = "menus/menu_principal/menu_principal_";
        String spriteIndex;

        switch (getChoice()) {
            case 0: spriteIndex = "00000"; break;
            case 1: spriteIndex = "00001"; break;
            case 2: spriteIndex = "00002"; break;
            default: spriteIndex = "00000"; break;
        }

        this.changeSprite(basePath + spriteIndex);
    }

    @Override
    public TypeMenu getTypeMenu() {
        return TypeMenu.DEMARRAGE;
    }
}
