package xtremvaders.Jeu;

import java.util.ArrayList;
import java.util.List;

import xtremvaders.Jeu.Menus.CursorItem;
import xtremvaders.Jeu.Menus.MenuAnime;
import xtremvaders.Jeu.Menus.MenuItemClickable;

public class MainMenu {

    List<MenuItemClickable> menuItems;
    
    XtremVaders2021 game;

    MenuAnime menuAnime;

    Runnable onStartNewGame;



    public MainMenu(XtremVaders2021 game, Runnable onStartNewGame) {
        this.game = game;
        this.onStartNewGame = onStartNewGame;
    }

    public boolean isCollidingWithCursor(CursorItem cursor) {
         for (MenuItemClickable item : menuItems) {
            if (item instanceof MenuItemClickable && item.getBoundingBox().intersects(cursor.getBoundingBox())) {
                ((MenuItemClickable) item).onClick(); // Déclenche le comportement du bouton
                System.out.println("Collide with menu item");
                return true;
            }
        }
        return false;
    }

    public List<MenuItemClickable> getAllMenuItems() {
        return menuItems;
    }

     public void spawnMainMenu() {

        menuAnime = new MenuAnime(game, 0, 0);
        game.addItem(menuAnime);

        // Coordonnées de base (centré horizontalement, espacé verticalement)
        int baseX = game.getWidth() / 2 - 344 / 2;
        int baseY = game.getHeight() / 2 + 50;

        int ecartY = 80; // espace vertical entre les boutons

        // 1. Bouton : Commencer Partie
        MenuItemClickable boutonCommencer = new MenuItemClickable(
            game,
            "cursor/select", // Remplace avec sprite bouton réel si différent
            baseX, baseY,
            "start",
            () -> {
                onStartNewGame.run();
                destroyMainMenu();
            }
        );

        // 2. Bouton : Contrôles
        MenuItemClickable boutonControles = new MenuItemClickable(
            game,
            "cursor/select",
            baseX, baseY + ecartY,
            "controls",
            () -> {
                System.out.println("Action : Affichage des contrôles !");
            }
        );

        // 3. Bouton : Quitter
        MenuItemClickable boutonQuitter = new MenuItemClickable(
            game,
            "cursor/select",
            baseX, baseY + ecartY * 2,
            "quit",
            () -> {
                System.out.println("Action : Quitter le jeu");
                System.exit(0);
            }
        );

        // Ajout des boutons au jeu
        game.addItem(boutonCommencer);
        game.addItem(boutonControles);
        game.addItem(boutonQuitter);

        menuItems = new ArrayList<>();
        menuItems.add(boutonCommencer);
        menuItems.add(boutonControles);
        menuItems.add(boutonQuitter);
    }

    public void destroyMainMenu() {
        for (MenuItemClickable item : menuItems) {
            game.remove(item); // Retire chaque bouton du jeu
        }
        menuItems.clear(); // Ensuite, vide la liste
        game.remove(menuAnime); //on retire le menu su jeu
    }

}
