package xtremvaders.Entites;

import java.awt.event.KeyEvent;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Graphics.VFX.Animation;
import xtremvaders.Graphics.VFX.ItemAnime;
import xtremvaders.Graphics.VFX.TypeAnimation;
import xtremvaders.Input.GameAction;
import xtremvaders.Input.GameActionListener;
import xtremvaders.Jeu.GameRuntime;
import xtremvaders.Jeu.XtremVaders2021;
import xtremvaders.Objets.Canon;
import xtremvaders.Objets.Missiles.FabriqueMissile;
import xtremvaders.Objets.Missiles.Missile;
import xtremvaders.Objets.Missiles.TypeMissile;
import xtremvaders.Objets.Shields.Shield;

/**
 * Joueur du petitjeu
 * @author aguidet
 */
public class Joueur extends Vaisseau implements GameActionListener {
    /**
     * Le score du joueur
     */
    private int _score = 0;
    
    /**
     * Permet au joueur de lancer des missiles
     */
    private Missile missile;
    
    /**
     * utile pour attandre le temps avant de pouvoir tirer à nouveau
     */
    private double lastShotElaspedTime;
    private double timeBeforeNextShotMs; //CONSTANT TO SET FROM DIFFICULTY
    
    /**
     * boolean qui décrit/défini si le joueur peut tirer ou non
     */
    private boolean canShoot;
    
    /**
     * Le type de missile que possède le joueur dans son inventaire
     */
    private TypeMissile typeMissile;
    
    /**
     * Détermine si le joueur peut aller à gauche
     * (quand il appuie sur la touche gauche du clavier)
     */
    private boolean left;
    /**
     * Détermine si le joueur peut aller à droite
     * (quand il appuie sur la touche droite du clavier)
     */
    private boolean right;
    
    /** 
     * utile pour l'animation du vaisseau
     */
    private ItemAnime itemAnime;
    
    /**
     * canon du joueur
     */
    private Canon canon;
    
    /**
     *  bouclier du joueur
     */
    private Shield shield;
    
    /*
    *utile pour bloquer les actions du vaisseau et sur le vaisseau
    */
    private boolean estActionFreeze;
    
    /**
     * limite de vie
     */
    private int maxPtVie;

    
    /**
     * Initialise le joueur
     * @param g le jeu
     * @param vitesse du joueur
     */
    public Joueur(Game g, double vitesse, int timeBeforeNextShotMs) {
        super(g, "joueur/playerShip3", 485, 690, vitesse);
        int PV = 100;
        setPtVie(PV);
        this.maxPtVie  = PV;
        this._score          = 0;
        this.estActionFreeze = false;
        this.canShoot        = true;
        this._score          = 0;
        this.lastShotElaspedTime = 0;
        this.timeBeforeNextShotMs = timeBeforeNextShotMs;
        typeMissile          = TypeMissile.NORMAL;
        right                = false;
        left                 = false;
        //définition de la première animation du vaisseau Joueur qui sera jouée
        this.itemAnime = new ItemAnime(getGame(), "transparent", 485, 690, TypeAnimation.SPACESHIP3_NORMAL, this);

        
        //resetJoueur();
    }

    private Runnable onPressEscape;
    public void setOnPressEscape(Runnable callback) {
        this.onPressEscape = callback;
    }

    @Override
    public void tirer() {
        //si les actions du vaisseaux ne sont pas freezées
        if(!estActionFreeze){
            //si le joueur peut tirer
            if(this.canShoot){
                AudioDirector director = AudioDirector.getInstance();
                director.playSFX("newSounds/spaceShoot");
                this.canShoot = false;
                this.missile = FabriqueMissile.fabriquerUnMissile(getGame(), getMiddleX()-5, getMiddleY()-50, typeMissile, this);
                getGame().addItem(missile);
                itemAnime.setLifeSpend(0);
                itemAnime.setAnimationType(TypeAnimation.SPACESHIP3_SHOOT);
            }
        }
    }
    
    /**
     * Quand le joueur entre en colision avec un missile ennemi il perd une vie
     * S'il rentre en colision avec un invader il perd toutes ses vies
     * @param item avec lequel le joueur est entré en colision 
     */
    @Override
    public void collideEffect(GameItem item) {
        //si les actions du vaisseaux ne sont pas freezées
        if(estVivant() && !estActionFreeze){
            if(item.getItemType().equals("MissileEnnemi")){
                Missile m = (Missile) (item);
                System.out.println(getPtVie());
                setPtVie(getPtVie() - m.getDegat(this) );
                AudioDirector.getInstance().playSFX("invaderKilled");
                System.out.println(getPtVie());
                itemAnime.setAnimationType(TypeAnimation.SPACESHIP3_COLLISION);         
            }
            else if(item.getItemType().equals("Invader")){
                setPtVie(-getPtVie());
                AudioDirector.getInstance().playSFX("newSounds/playerTouched");
            }
            else if(item.getItemType().equals("Debrits")){
                setPtVie(getPtVie()-50);
                AudioDirector.getInstance().playSFX("newSounds/asteroidCollision");
            }
        }
    }

    @Override
    public void evolve(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);

        //Animation de mort
        if(!estVivant()){
            itemAnime.setAnimationType(TypeAnimation.SPACESHIP3_DEAD);
        }
        // selection des animations a lancer selon les cas
        if(itemAnime.getAnimationType() == TypeAnimation.SPACESHIP3_COLLISION && itemAnime.animationFinie()){
            itemAnime.setAnimationType(TypeAnimation.SPACESHIP3_NORMAL);
        } else if(itemAnime.getAnimationType() == TypeAnimation.SPACESHIP3_SHOOT && itemAnime.animationFinie()){
            itemAnime.setAnimationType(TypeAnimation.SPACESHIP3_NORMAL);
        }       
        
        itemAnime.loopAnimation(scaledDt, Animation.getAnimationSpeed());
        this.lastShotElaspedTime -= scaledDt;
        
        if(this.lastShotElaspedTime <= 0){
            this.canShoot = true;
            this.lastShotElaspedTime = timeBeforeNextShotMs; //reset du shooting
        }
        //mouvement du joueur
        appliquerMouvement(scaledDt);
        //applyPhysics(scaledDt);
    }

    @Override
    public String getItemType() {
        return "Joueur";
    }

    
    /**
     * Evènement appelé lorsqu'une touche est pressée. Gère les mouvements 
     * du 
     * @param e la touche qui est pressée
     */
    //@Override
    public void keyPressed(KeyEvent e) {
        try{
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    left = true;
                    setVitesse(getVitesse() + 0.005); //accélération + on appuie
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    setVitesse(getVitesse() + 0.005); //accélération + on appuie
                    break;
                case KeyEvent.VK_Q:
                    left = true;
                    setVitesse(getVitesse() + 0.005); //accélération + on appuie
                    break;
                case KeyEvent.VK_D:
                    right = true;
                    setVitesse(getVitesse() + 0.005); //accélération + on appuie
                    break;

                case KeyEvent.VK_SPACE:
                   tirer();
                   break;
                case KeyEvent.VK_A:
                   this.canon.tirer();
                   break;
                case KeyEvent.VK_ESCAPE: // cas ECHAP
                    onPressEscape.run();
                    
            }
        }catch(Exception x){}
    }
    
   // @Override
    public void keyTyped(KeyEvent e) {
    }
    
    //@Override
    public void keyReleased(KeyEvent e) {
        try{
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    left = false;
                    setVitesse(0.25); //réinitialisation de la vitesse
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    setVitesse(0.25); //réinitialisation de la vitesse
                    break;
                case KeyEvent.VK_Q:
                    left = false;
                    setVitesse(0.25); //réinitialisation de la vitesse
                    break;
                case KeyEvent.VK_D:
                    right = false;
                    setVitesse(0.25); //réinitialisation de la vitesse
                    break;
            }
        }catch(Exception x){}
    }

    public int getScore() {
        return _score;
    }
    
    public void addScore(){
        _score = _score + 1;
    }  
    
    public void setTypeMissile(TypeMissile typeMissile) {
        this.typeMissile = typeMissile;
    }
    
    /**
     * Changement de direction et calcul de la vitesse suivant si gauche ou
     * droite est appuyé
     * @dt l'évolution du temps
     */
    private void appliquerMouvement(long dt){
        if(this.getRight() < getGame().getWidth() && right) {
            canon.deplacerCanon(dt * getVitesse(), 0);
            moveDA(dt * getVitesse(), 0);        
        }
        else if(this.getLeft() > 0 && left) {
            moveDA(dt * getVitesse(), 180);
            canon.deplacerCanon(dt * getVitesse(), 180);
        }
    }

    // Paramètres physiques
    private double masse = 1.0;                // masse de l'objet
    private double vitesseX = 0;               // vitesse horizontale actuelle
    private double force = 500;                // force de poussée (plus elle est grande, plus l'accélération est rapide)
    private double inertie = 0.95;             // facteur de ralentissement (0.95 = 5% de perte de vitesse par frame)
    private double vitesseMax = 400.0;         // vitesse maximale autorisée


    /**
     * Appliquer le mouvement horizontal avec masse et inertie
     * @param dt Temps écoulé en millisecondes
     */
    private void applyPhysics(long dt) {
        double dtSecondes = dt / 1000.0; // conversion en secondes

        // Calcul de la force appliquée selon les touches
        double accelerationX = 0;
        if (right && this.getRight() < getGame().getWidth()) {
            accelerationX = force / masse;
        } else if (left && this.getLeft() > 0) {
            accelerationX = -force / masse;
        }

        // Appliquer l'accélération à la vitesse
        vitesseX += accelerationX * dtSecondes;

        // Appliquer inertie si aucune touche n’est pressée
        if (!right && !left) {
            vitesseX *= inertie; // ralentit progressivement
            if (Math.abs(vitesseX) < 1) vitesseX = 0; // éviter la glisse infinie
        }

        // Limiter la vitesse
        if (vitesseX > vitesseMax) vitesseX = vitesseMax;
        if (vitesseX < -vitesseMax) vitesseX = -vitesseMax;

        // Appliquer le déplacement réel
        double dx = vitesseX * dtSecondes;
        moveDA(Math.abs(dx), dx >= 0 ? 0 : 180); // 0 = droite, 180 = gauche
        canon.deplacerCanon(Math.abs(dx), dx >= 0 ? 0 : 180);
    }


    /**
     * Cette méthode permet de setter le Shield du joueur et de l'ajouter au jeu
     * @param shield qui ecrasera/settera l'attribut shield du joueur
     */
    public void initShield(Shield shield) {
        this.shield = shield;
        getGame().addItem(shield);
    }
    
    /**
     * Cette méthode créer le double Canon du Joueur et l'ajoute au jeu
     */
    public void creerUnCanon(Canon canon){
        this.canon = canon;
        getGame().addItem(canon);
    }

    public Canon getCanon() {
        return canon;
    }
    
    /**
     * Cette méthode permet d'activer le boolean qui servira à savoir 
     * si on doit bloquer les actions du vaisseau ou non
     */
    public void setEstActionFreeze(boolean estActionFreeze) {
        this.estActionFreeze = estActionFreeze;
    }

    public boolean isEstActionFreeze() {
        return estActionFreeze;
    }
    
    public void resetJoueur(){
        int PV = 100;
        setPtVie(PV);
        this.maxPtVie  = PV;
        this._score          = 0;
        this.estActionFreeze = false;
        this.canShoot        = true;
        this._score          = 0;
        this.lastShotElaspedTime = timeBeforeNextShotMs;
        typeMissile          = TypeMissile.NORMAL;
        right                = false;
        left                 = false;
        //définition de la première animation du vaisseau Joueur qui sera jouée
        this.itemAnime = new ItemAnime(getGame(), "transparent", 485, 690, TypeAnimation.SPACESHIP3_NORMAL, this);
        initCanon();
    }

    public int getMaxPtVie() {
        return maxPtVie;
    }

    public void setMaxPtVie(int maxPtVie) {
        this.maxPtVie = maxPtVie;
    }    
    
    /**
     * Cette méthode initialise le canon
     */
    public void initCanon(){
        this.canon     = new Canon(getGame(), this);
        getGame().addItem(canon);
    }

    @Override
    public void onActionPressed(GameAction action) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: " + this.getClass().getName() + " onActionPressed() ");
        }
        switch (action) {
            case MOVE_LEFT:
                left = true;
                setVitesse(getVitesse() + 0.005); // accélération
                break;
            case MOVE_RIGHT:
                right = true;
                setVitesse(getVitesse() + 0.005); // accélération
                break;
            case FIRE:
                tirer();
                break;
            case FIRE_ALT:
                this.canon.tirer();
                break;
            case PAUSE:
                onPressEscape.run();
                break;
        }
    }

    @Override
    public void onActionReleased(GameAction action) {
        if(XtremVaders2021.kDebugGameControls == true) {
            System.out.println("kDebugGameControls: " + this.getClass().getName() + " onActionReleased() ");
        }
        switch (action) {
            case MOVE_LEFT:
                left = false;
                setVitesse(0.25); // réinitialisation
                break;
            case MOVE_RIGHT:
                right = false;
                setVitesse(0.25); // réinitialisation
                break;
            default:
                break; // Rien à faire pour FIRE, FIRE_ALT, PAUSE
        }
    }

}