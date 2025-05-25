package xtremvaders.Jeu.Menus;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import iut.Game;

public class MouseMotionManager implements MouseMotionListener {

    private CursorItem cursorInGame;

    // ✅ Constructeur : il n'a pas de type de retour !
    public MouseMotionManager(Game g) {
        // On suppose que CursorItem prend un nom de sprite ou type, + coordonnées
        this.cursorInGame = new CursorItem(g, "cursor/cursor", 200, 200);
        g.addItem(cursorInGame); // Ajout dans le jeu pour qu'il soit affiché
    }

    public CursorItem getCursor() {
        return cursorInGame;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (cursorInGame != null) {
            cursorInGame.udpdateCoords(e.getX(), e.getY()); // Corrige typo udpdate -> update si besoin
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
}
