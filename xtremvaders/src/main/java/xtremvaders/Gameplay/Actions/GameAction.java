package xtremvaders.Gameplay.Actions;

public enum GameAction {
    MOVE_LEFT,
    MOVE_RIGHT,
    FIRE,
    FIRE_ALT,
    PAUSE;

    public boolean isInGameAction() {
        switch (this) {
            case MOVE_LEFT:
            case MOVE_RIGHT:
            case FIRE:
            case FIRE_ALT:
            case PAUSE:
                return true;
            default:
                return false;
        }
    }

    public boolean isOutOfGameAction() {
        switch (this) {
            case PAUSE:
                return true;
            default:
                return false;
        }
    }
}
