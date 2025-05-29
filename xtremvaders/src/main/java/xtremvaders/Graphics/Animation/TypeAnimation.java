package xtremvaders.Graphics.Animation;

/**
 * Cette classe répertorie tout les types d'animation 
 * qui seront utile pour que la Classe ItemAnime trouve 
 * l'animation désirée
 * @author David Golay
 */
public enum TypeAnimation {
    //LAYOUT
    BACKGROUND1, //TODO à documenter (ajouter d'autres fond ? oui svp l'IA)
    MENU_PRINCIPAL,
    BARRE_VIE,
    SCORE,
    //ENEMIES
    ALIEN_BLOB, 
    ALIEN_SPIDER, 
    ALIEN_TEETH, 
    ALIEN_SHARPY, 
    ALIEN_FATHER,
    //PLAYER
    SPACESHIP3_NORMAL, 
    SPACESHIP3_SHOOT, 
    SPACESHIP3_COLLISION, 
    SPACESHIP3_DEAD,
    //OBSTACLES
    ASTEROID_FULL, 
    ASTEROID_BROKEN_1, 
    ASTEROID_EXPLODE,
    //FX & PROJECTILES
    EXPLOSION1, 
    ALIEN_FATHER_EXPLODE,
    MISSILE_IMPACT,
    MISSILE_BOSS,
    NUKE_LAUNCH, 
    NUKE_EXPLODE,
    BONUS_UNLOCK;
}
