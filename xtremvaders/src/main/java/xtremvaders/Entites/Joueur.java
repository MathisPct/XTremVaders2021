package xtremvaders.Entites;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Controls.GameActionListener;
import xtremvaders.Gameplay.Actions.GameAction;
import xtremvaders.Graphics.Animation.Animation;
import xtremvaders.Graphics.Animation.ItemAnime;
import xtremvaders.Graphics.Animation.TypeAnimation;
import xtremvaders.Objets.Canon;
import xtremvaders.Objets.Missiles.FabriqueMissile;
import xtremvaders.Objets.Missiles.Missile;
import xtremvaders.Objets.Missiles.TypeMissile;
import xtremvaders.Objets.Shields.Shield;
import xtremvaders.Runtime.GameConfig;
import xtremvaders.Runtime.GameRuntime;

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

    /*
    * CONSTANT TO SET FROM DIFFICULTY
    */
    private double timeBeforeNextShotMs;
    
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
    
    /**
     * Quand le joueur entre en colision avec un missile ennemi il perd une vie
     * S'il rentre en colision avec un invader il perd toutes ses vies
     * @param item avec lequel le joueur est entré en colision 
     */
    @Override
    public void collideEffect(GameItem item) {
        //si les actions du vaisseaux ne sont pas freezées
        if(estVivant()){
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
        //appliquerMouvement(scaledDt);
        applyPhysics(scaledDt);
    }

    @Override
    public String getItemType() {
        return "Joueur";
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


    private void applyPhysics(long dt) {
        double dtSecondes = dt / 1000.0;

        // États des touches
        boolean allerADroite = right && this.getRight() < getGame().getWidth();
        boolean allerAGauche = left && this.getLeft() > 0;

        double accelerationX = 0;
        double boost = 200; // Valeur du boost (tu peux ajuster)

        if (allerADroite) {
            if (vitesseX < 0) {
                // Changement de direction : petit boost
                vitesseX += boost;
            }
            accelerationX = force / masse;

        } else if (allerAGauche) {
            if (vitesseX > 0) {
                vitesseX -= boost;
            }
            accelerationX = -force / masse;
        }

        // Appliquer l'accélération à la vitesse
        vitesseX += accelerationX * dtSecondes;

        // Appliquer l'inertie si aucune touche n’est pressée
        if (!allerADroite && !allerAGauche) {
            vitesseX *= inertie;
            if (Math.abs(vitesseX) < 1) vitesseX = 0;
        }

        // Limiter la vitesse
        if (vitesseX > vitesseMax) vitesseX = vitesseMax;
        if (vitesseX < -vitesseMax) vitesseX = -vitesseMax;

        // Appliquer le déplacement
        double dx = vitesseX * dtSecondes;
        moveDA(Math.abs(dx), dx >= 0 ? 0 : 180);
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
    

    public void resetJoueur(){
        int PV = 100;
        setPtVie(PV);
        this.maxPtVie  = PV;
        this._score          = 0;
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
        if(GameConfig.kDebugGameControls == true) {
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
        if(GameConfig.kDebugGameControls == true) {
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