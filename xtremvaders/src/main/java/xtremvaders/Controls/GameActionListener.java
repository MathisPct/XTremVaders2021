package xtremvaders.Controls;

import xtremvaders.Gameplay.Actions.GameAction;

public interface GameActionListener {
    void onActionPressed(GameAction action);
    void onActionReleased(GameAction action);
}
