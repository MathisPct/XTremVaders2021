package xtremvaders.Jeu.Menus;

import iut.Game;
import iut.GameItem;

public class CursorItem extends GameItem {

    public CursorItem(Game g, String name, double _x, double _y) {
        super(g, name, _x, _y);
        //TODO Auto-generated constructor stub
    }

    public void udpdateCoords(int x, int y) {
        //this.get
        System.out.println("Souris à : " + x + ", " + y);

        this.getPosition().setX(x);
        this.getPosition().setY(y);
    }

    @Override
    public void evolve(long arg0) {
    }

    @Override
    public void collideEffect(GameItem arg0) {
    }

    @Override
    public String getItemType() {
        return("cursor");
    }

    @Override
    public boolean isCollide(GameItem arg0) {
        return true;
    }
    
}
