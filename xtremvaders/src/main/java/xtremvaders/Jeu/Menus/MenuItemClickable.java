package xtremvaders.Jeu.Menus;

import iut.Game;
import iut.GameItem;

public class MenuItemClickable extends GameItem {

    private Runnable action;  // Ce qu'on exécute si cliqué

    public MenuItemClickable(Game g, String name, double x, double y, Runnable action) {
        super(g, name, x, y);
        this.action = action;
    }

    @Override
    public void evolve(long dt) {
        // Tu peux ajouter une animation de survol ici plus tard
        //this.changeSprite("cursor/select");
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(
            this.getPosition().getX(), 
            this.getPosition().getY(), 
            this.getWidth(), 
            this.getHeight()
        );
    }

    @Override
    public void collideEffect(GameItem gi) {
       
    }

    @Override
    public String getItemType() {
        return "menuItem";
    }

    @Override
    public boolean isCollide(GameItem arg0) {
        

        return true;
    }
}
