// package xtremvaders.Jeu.Menus;

// import java.awt.Graphics;

// import iut.Game;
// import iut.GameItem;

// public class MenuVolumeSlider extends GameItem {

//     private double volume; // entre 0.0 et 1.0
//     private double width, height;

//     public MenuVolumeSlider(Game game, double x, double y, double width, double height) {
//         super(game, "menus/slider/slider_bg", x, y);
//         this.volume = 0.5; // valeur par défaut
//         this.width = width;
//         this.height = height;
//     }

//     @Override
//     public void evolve(long dt) {
//         // Rien à faire automatiquement ici
//     }

//     @Override
//     public void draw(Graphics g) throws Exception {
//         // Dessine la barre de fond (déjà via sprite), puis le curseur (manuellement)
//         super.draw(g);

//         int sliderX = (int) (getLeft() + volume * width);
//         int sliderY = (int) getTop();
//         int sliderWidth = 10;
//         int sliderHeight = (int) height;

//         g.setColor(java.awt.Color.WHITE);
//         g.fillRect(sliderX - sliderWidth / 2, sliderY, sliderWidth, sliderHeight);
//     }

//     public BoundingBox getBoundingBox() {
//         return new BoundingBox(getLeft(), getTop(), width, height);
//     }

//     public void setVolumeFromMouse(double mouseX) {
//         double relativeX = Math.max(0, Math.min(mouseX - getLeft(), width));
//         this.volume = relativeX / width;

//         // Applique le volume au système audio
//         //AudioDirector.getInstance().music.setVolume(volume);

//         System.out.println("Volume ajusté à : " + volume);
//     }

//     @Override
//     public void collideEffect(GameItem gi) {}

//     @Override
//     public String getItemType() {
//         return "volume_slider";
//     }

//     @Override
//     public boolean isCollide(GameItem gi) {
//         return true;
//     }
// }
