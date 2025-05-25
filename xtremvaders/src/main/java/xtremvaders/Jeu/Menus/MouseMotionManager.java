package xtremvaders.Jeu.Menus;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionManager implements MouseMotionListener {
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Souris à : " + e.getX() + ", " + e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
}
