package xtremvaders.Jeu.Menus;

public class BoundingBox {
    private double x, y, width, height;

    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getLeft() {
        return x;
    }

    public double getRight() {
        return x + width;
    }

    public double getTop() {
        return y;
    }

    public double getBottom() {
        return y + height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getPosX() {
        return x;
    }

    public double getPosY() {
        return y;
    }

    public boolean intersects(BoundingBox other) {
        return !(this.getRight() < other.getLeft() ||
                 this.getLeft() > other.getRight() ||
                 this.getBottom() < other.getTop() ||
                 this.getTop() > other.getBottom());
    }
}
