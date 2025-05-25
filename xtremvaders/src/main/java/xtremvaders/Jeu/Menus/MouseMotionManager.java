package xtremvaders.Jeu.Menus;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import iut.Game;

public class MouseMotionManager implements MouseMotionListener {

    private CursorItem cursor;

    public MouseMotionManager(Game g, CursorItem cursor) {
        this.cursor = cursor;
    }

    public CursorItem getCursor() {
        return cursor;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (cursor != null) {
            cursor.udpdateCoords(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    protected void destroy() {

    }
}
