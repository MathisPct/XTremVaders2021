package xtremvaders.Jeu.Menus;

import iut.Game;
import iut.GameItem;

public class CursorItem extends GameItem {

    public CursorItem(Game g, String name, double _x, double _y) {
        super(g, name, _x, _y);
        
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(
            this.getPosition().getX(), 
            this.getPosition().getY(), 
            this.getWidth(), 
            this.getHeight()
        );
    }

    public void udpdateCoords(int x, int y) {
        //this.get
        //System.out.println("Souris à : " + x + ", " + y);

        this.getPosition().setX(x);
        this.getPosition().setY(y);
    }

    @Override
    public void evolve(long arg0) {
    }

    @Override
    public void collideEffect(GameItem gi) {
        
    }

    @Override
    public String getItemType() {
        return("cursor");
    }


    @Override
    public boolean isCollide(GameItem gi) {
        
        return false;
    }
    
}
