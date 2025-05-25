package xtremvaders.Jeu.Menus;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import xtremvaders.Jeu.XtremVaders2021;

public class MouseClickManager implements MouseListener {

    private final XtremVaders2021 game;
    private final CursorItem cursor;

    public MouseClickManager(XtremVaders2021 g, CursorItem cursor) {
        this.game = g;
        this.cursor = cursor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Met à jour la position du curseur
        cursor.udpdateCoords(e.getX(), e.getY());

         System.out.println("CLICK Souris à : " + e.getX() + ", " + e.getY());

        // Parcourt tous les GameItems et détecte collision avec cursor
        List<MenuItemClickable> items = game.getAllMenuItems();

        for (MenuItemClickable item : items) {
            if (item instanceof MenuItemClickable && item.getBoundingBox().intersects(cursor.getBoundingBox())) {
                ((MenuItemClickable) item).collideEffect(cursor); // Déclenche le comportement du bouton
                System.out.println("Collide with menu item");
                break; // Une seule action par clic
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
