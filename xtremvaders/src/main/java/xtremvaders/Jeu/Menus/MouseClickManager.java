package xtremvaders.Jeu.Menus;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

public class MouseClickManager implements MouseListener {

    private final CursorItem cursor;

    private final BiConsumer<MouseEvent, CursorItem> onClickAction;


    public MouseClickManager( CursorItem cursor, BiConsumer<MouseEvent, CursorItem> onClickAction) {
        this.cursor = cursor;
        this.onClickAction = onClickAction;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (onClickAction != null) {
            onClickAction.accept(e, cursor);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
