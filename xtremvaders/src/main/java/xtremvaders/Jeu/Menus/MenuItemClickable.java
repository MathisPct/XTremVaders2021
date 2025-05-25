package xtremvaders.Jeu.Menus;

import iut.Game;
import iut.GameItem;

public class MenuItemClickable extends GameItem {

    private String actionName;

    private Runnable action;  // Ce qu'on exécute si cliqué

    public MenuItemClickable(Game g, String name, double x, double y, String actionName, Runnable action) {
        super(g, name, x, y);
        this.action = action;
    }

    @Override
    public void evolve(long dt) {
        // Tu peux ajouter une animation de survol ici plus tard
        
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(
            this.getPosition().getX(), 
            this.getPosition().getY(), 
            this.getWidth(), 
            this.getHeight()
        );
    }

    public void onClick() {
        this.changeSprite("cursor/select_hover");
       action.run();
    }

    @Override
    public void collideEffect(GameItem gi) {
       
    }

    @Override
    public String getItemType() {
        return TypeMenu.DEMARRAGE.name() + "_" + actionName;
    }

    @Override
    public boolean isCollide(GameItem arg0) {
        

        return true;
    }
}
