package xtremvaders.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iut.Game;
import iut.GameItem;
import xtremvaders.Jeu.XtremVaders2021;


/**
 * Cette classe doit boire les inputs, c'est l'extraction de la classe joueur
 */

public class GameInputHandler extends GameItem implements KeyListener {

    /**
     * Map liant les codes des touches clavier (KeyEvent.VK_*) à des actions du jeu (GameAction).
     * Exemple : KeyEvent.VK_LEFT -> GameAction.MOVE_LEFT
     */
    private final Map<Integer, GameAction> keyBindings = new HashMap<>();

    /**
     * Map état actuel de chaque action du jeu (pressée ou non).
     * Si une action est en cours d'exécution (true) ou relâchée (false).
     */
    private final Map<GameAction, Boolean> actionStates = new HashMap<>();

    /**
     * Objets notifiés lorsqu'une action est triggered
     * Chaque listener est une implémentation de GameActionListener (ex. : Player).
     */
    private final List<GameActionListener> listeners = new ArrayList<>();


    public GameInputHandler(Game g) {
        super(g, "utils/empty", 0, 0);
        // Bindings clavier par défaut
        keyBindings.put(KeyEvent.VK_LEFT, GameAction.MOVE_LEFT);
        keyBindings.put(KeyEvent.VK_Q, GameAction.MOVE_LEFT);
        keyBindings.put(KeyEvent.VK_RIGHT, GameAction.MOVE_RIGHT);
        keyBindings.put(KeyEvent.VK_D, GameAction.MOVE_RIGHT);
        keyBindings.put(KeyEvent.VK_SPACE, GameAction.FIRE);
        keyBindings.put(KeyEvent.VK_A, GameAction.FIRE_ALT);
        keyBindings.put(KeyEvent.VK_ESCAPE, GameAction.PAUSE);

        // Initialiser tous les états à false
        for (GameAction action : GameAction.values()) {
            actionStates.put(action, false);
        }
    }

    public void addActionListener(GameActionListener listener) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: Add GameControlsListener: " + listener.getClass().toString());
        }
        listeners.add(listener);
    }

    private void notifyActionPressed(GameAction action) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: notifyActionPressed " + this.getClass().getName());
        }
        for (GameActionListener l : listeners) {
            l.onActionPressed(action);
        }
    }

    private void notifyActionReleased(GameAction action) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: notifyActionReleased " + this.getClass().getName());
        }
        for (GameActionListener l : listeners) {
            l.onActionReleased(action);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: Key pressed " + e.toString());
        }
        
       // Récupère l'action reliée
        GameAction action = keyBindings.get(e.getKeyCode()); 

        // Si trouvée ET pas déjà active
        if (action != null && !actionStates.get(action)) {
            
            // Marque l'action comme étant active (touche enfoncée)
            actionStates.put(action, true);
            
            // Notifie tous les objets abonnés (ex: Player) que cette action vient d'être pressée
            notifyActionPressed(action);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: keyReleased Event KeyListener" + e.getKeyCode());
        }
        GameAction action = keyBindings.get(e.getKeyCode());
        if (action != null) {
            actionStates.put(action, false);
            notifyActionReleased(action);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: keyTyped Event KeyListener" + e.getKeyCode());
        }
    }

    @Override
    public void evolve(long arg0) {
    }

    @Override
    public void collideEffect(GameItem arg0) {

    }

    @Override
    public String getItemType() {
        return "GameInputListener";
    }

    @Override
    public boolean isCollide(GameItem arg0) {
        return false;
    }
}