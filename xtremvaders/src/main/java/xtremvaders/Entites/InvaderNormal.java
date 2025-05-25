package xtremvaders.Entites;

import xtremvaders.Objets.Missiles.FabriqueMissile;
import xtremvaders.Objets.Missiles.Missile;
import xtremvaders.Objets.Missiles.TypeMissile;
import iut.Game;

/**
 * Invader normal qui se déplacent déplacent de droite à gauche et vers le bas
 * @author Mathis Poncet
 */
public class InvaderNormal extends Invader{

    public InvaderNormal(Game g, String sprite,int x, int y, double vitesse) {
        super(g, sprite ,x, y, vitesse);
    }

    @Override
    public TypeInvader getTypeInvader() {
        return TypeInvader.NORMAL;
    }  

    @Override
    public void tirer() {
        Missile m = FabriqueMissile.fabriquerUnMissile(getGame(), getMiddleX()-30, getMiddleY()-20, TypeMissile.INVADERDEFAUT, this);
        setMissile(m);
        getGame().addItem(m);
    }
}
