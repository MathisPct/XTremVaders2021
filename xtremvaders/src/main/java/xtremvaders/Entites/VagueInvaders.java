package xtremvaders.Entites;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import iut.Game;
import iut.GameItem;
import xtremvaders.Audio.AudioDirector;
import xtremvaders.Jeu.GameRuntime;
import xtremvaders.Objets.BonusJoueur.TypeBonus;
import xtremvaders.Utilities.InvaderBoundaryListener;
import xtremvaders.Utilities.Direction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe générer une vague d'invaders
 *
 * @author Mathis Poncet
 */
public class VagueInvaders extends GameItem implements InvaderBoundaryListener {
    /**
     * Nomre de lignes d'invaders
     */
    private final int nbSurLigne;
    /**
     * Nombre de colonne d'invader
     */
    private final int nbSurCol;
    /**
     * Permet de connaître le nombre de vagues qui se sont déjà déroulés dans le
     * jeu
     */
    private static int nbVagues;
    /**
     * Contient les invaders de la vague
     */
    private static ArrayList<Invader> invaders;

    /**
     * Liste des bonus présents dans la vague des invaders
     */
    private static ArrayList<TypeBonus> listeBonus;

    /**
     * Constructeur par initialisation
     *
     * @param g          le jeu
     * @param nbSurLigne nombre de lignes d'invaders
     * @param nbSurCol   nombre de colonnes d'invaders
     */
    public VagueInvaders(Game g, int nbSurLigne, int nbSurCol) {
        super(g, "", -1, -1);
        invaders = new ArrayList<>();
        this.nbSurCol = nbSurCol;
        this.nbSurLigne = nbSurLigne;
        nbVagues = 0;
    }

    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void draw(Graphics g) throws Exception {

    }

    @Override
    public void collideEffect(GameItem gi) {

    }

    @Override
    public String getItemType() {
        return "VagueInvaders";
    }

    @Override
    public void evolve(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);

        //génération de la vague s'il n'y a pas d'invaders sur la carte
        if (invaders.isEmpty()) {
            this.genererVague();
        }
        bouger();
    }

    /**
     * Génére les invaders par ligne et colonne suivant le nombre de ligne voulue
     * et le nombre de colonne voulue. A chaque génération, le nombre de vague est
     * incrémenté. Les invaders générés sont ajoutés à la liste des invaders de
     * la vague
     */
    public void genererVague() {
        nbVagues++;
        AudioDirector.getInstance().playSFX("newSounds/newWave");
        System.out.println("Nb vague = " + nbVagues);
        listeBonus = new ArrayList<>();

        // Augmenter l'espacement pour éviter le regroupement rapide
        int spacingX = (int) (64 * 1.5);
        int spacingY = (int) (64 * 1.5);

        int startX = (getGame().getWidth() - (this.nbSurLigne * spacingX)) / 2;

        Random random = new Random();
        for (int l = 0; l < this.nbSurLigne; l++) {
            for (int c = 0; c < this.nbSurCol; c++) {
                int jitterX = random.nextInt(30) + 30;
                int jitterY = random.nextInt(30) + 30;

                Invader invader = FabriqueInvader.fabriquerUnInvader(
                        getGame(),
                        startX + l * spacingX + jitterX,
                        15 + c * spacingY + jitterY,
                        TypeInvader.NORMAL,
                        vitesseVague()
                );
                invader.setBoundaryListener(this);
                invaders.add(invader);
                super.getGame().addItem(invader);
            }
        }
    }
    
    /**
     * Permet de savoir le nombre de vagues qui se sont passées
     * @return le nombre de vagues qui se sont déroulés
     */
    public static int getNbVagues() {
        return nbVagues;
    }

    /**
     * Distance à laquelle les invaders peuvent voir les autres
     */
    private final double visualRange = 64 * 2;
    /**
     * Distance minimale entre invaders (augmentée pour éviter regroupement)
     */
    private final double protectedRange = 64 + 64 * 0.1;
    /**
     *  Facteur de centrage vers le groupe (réduit pour moins d'agrégation)
     */
    private final double centering_factor = 0.0005;
    /**
     * Facteur d'alignement de la vitesse
     */
    private final double matching_factor = 0.08;
    /**
     * Facteur d'évitement (augmenté significativement pour éviter regroupement)
     */
    private final double avoidfactor = 0.5;
    /**
     * Facteur de virage aux bords
     */
    private final double turnfactor = 0.4;
    /**
     * Vitesse minimale
     */
    private final double minspeed = 1.2;
    /**
     * Vitesse max
     */
    private final double maxspeed = 3.0;
    /**
     * Biais vers le bas pour descente progressive
     */
    private final double downwardBias = 0.001;
    /**
     * Biais horizontal pour encourager un mouvement groupé de gauche à droite
     */
    private final double horizontalBias = 0.1;
    /**
     * Facteur de divergence pour empêcher le regroupement excessif
     */
    private final double divergence_factor = 0.02;

    /**
     * Déplace tous les invaders selon l'algorithme des boids
     * Les invaders se déplacent en groupe, évitent les collisions entre eux,
     * et s'alignent avec le mouvement global du groupe
     */
    public void bouger() {
        if (invaders.isEmpty()) {
            return;
        }

        double visual_range_squared = visualRange * visualRange;
        double protected_range_squared = protectedRange * protectedRange;

        for (Invader boid : VagueInvaders.invaders) {
            double xpos_avg = 0, ypos_avg = 0, xvel_avg = 0, yvel_avg = 0;
            double close_dx = 0, close_dy = 0;
            int neighboring_boids = 0;

            double boid_x = boid.getLeft();
            double boid_y = boid.getTop();
            double boid_vx = 0;
            double boid_vy = 0;
            int boidIndex = invaders.indexOf(boid);
            double uniqueAngle = (boidIndex * 0.3) % (2 * Math.PI);
            double divergeX = Math.cos(uniqueAngle) * divergence_factor;
            double divergeY = Math.sin(uniqueAngle) * divergence_factor;

            for (Invader otherboid : VagueInvaders.invaders) {
                if (boid == otherboid) {
                    continue;
                }

                double dx = boid_x - otherboid.getLeft();
                double dy = boid_y - otherboid.getTop();

                // Sont-ils dans le champ visuel?
                if (Math.abs(dx) < visualRange && Math.abs(dy) < visualRange) {
                    double squared_distance = dx * dx + dy * dy;

                    // Si distance inférieure à la zone protégée, éviter
                    if (squared_distance < protected_range_squared) {
                        close_dx += dx;
                        close_dy += dy;
                    }
                    // Sinon, si dans le champ visuel, aligner et centrer
                    else if (squared_distance < visual_range_squared) {
                        xpos_avg += otherboid.getLeft();
                        ypos_avg += otherboid.getTop();
                        // Nous n'avons pas accès aux vitesses directement, on les approxime
                        // par la position relative entre les frames
                        xvel_avg += 0; // A ajuster si on dispose de la vitesse
                        yvel_avg += 0;
                        neighboring_boids++;
                    }
                }
            }

            if (neighboring_boids > 0) {
                xpos_avg = xpos_avg / neighboring_boids;
                ypos_avg = ypos_avg / neighboring_boids;
                xvel_avg = xvel_avg / neighboring_boids;
                yvel_avg = yvel_avg / neighboring_boids;

                boid_vx += (xpos_avg - boid_x) * centering_factor + (xvel_avg - boid_vx) * matching_factor;
                boid_vy += (ypos_avg - boid_y) * centering_factor + (yvel_avg - boid_vy) * matching_factor;
            }

            boid_vx += close_dx * avoidfactor;
            boid_vy += close_dy * avoidfactor;

            boid_vx += divergeX;
            boid_vy += divergeY;

            // Ajout d'une force anti-centralisation : empêche les invaders de converger vers le centre de l'écran
            double screenCenterX = getGame().getWidth() / 2.0;
            double screenCenterY = getGame().getHeight() / 3.0;
            double distToCenter = Math.sqrt(Math.pow(boid_x - screenCenterX, 2) + Math.pow(boid_y - screenCenterY, 2));

            // Si trop près du centre, appliquer une force répulsive
            if (distToCenter < 100) {
                double repulsiveFactor = 0.2;
                double repulseX = (boid_x - screenCenterX) / distToCenter * repulsiveFactor;
                double repulseY = (boid_y - screenCenterY) / distToCenter * repulsiveFactor;
                boid_vx += repulseX;
                boid_vy += repulseY;
            }

            // Gestion des bords de l'écran
            if (boid_y < 10) { // Top margin
                boid_vy += turnfactor;
            }
            if (boid_x > getGame().getWidth() - 10) { // Right margin
                boid_vx -= turnfactor;
            }
            if (boid_x < 10) { // Left margin
                boid_vx += turnfactor;
            }
            if (boid_y > getGame().getHeight() - 3 * 64) { // Bottom margin
                boid_vy -= turnfactor;
            }

            // Ajout d'un biais vers le bas pour faire descendre progressivement les invaders
            boid_vy += downwardBias;
            double oscillation = Math.sin(System.currentTimeMillis() / 1000.0) * horizontalBias;
            boid_vx += oscillation;

            // Calcul de la vitesse
            double speed = Math.sqrt(boid_vx * boid_vx + boid_vy * boid_vy);

            if (speed > 0) {
                if (speed < minspeed) {
                    boid_vx = (boid_vx / speed) * minspeed;
                    boid_vy = (boid_vy / speed) * minspeed;
                }
                if (speed > maxspeed) {
                    boid_vx = (boid_vx / speed) * maxspeed;
                    boid_vy = (boid_vy / speed) * maxspeed;
                }
            } else {
                boid_vx = (Math.random() - 0.5) * minspeed;
                boid_vy = (Math.random() * 0.5) * minspeed;
            }

            boid.bouger(boid_vx, boid_vy);
        }
    }
    
    public static ArrayList<Invader> getInvaders() {
        return invaders;
    }
    
    /**
     * Permet de retirer un invader de la liste
     * @param invader retire l'invader de la liste quand il est mort
     */
    public static void retirerInvader(Invader invader){
        invaders.remove(invader);
    }    

    /**
     * Augmente la vitesse en fonction du nombre de vague
     */
    public static double vitesseVague(){
        return Math.pow(-6.0, -5 * (nbVagues * nbVagues)) + 0.0107 * nbVagues + 0.1251;
    }
    
    public static ArrayList<TypeBonus> getListeBonus() {
        return listeBonus;
    }
    
    public static void ajouterBonus(TypeBonus b){
        listeBonus.add(b);
    }

    /**
     * Méthode appelée lorsqu'un invader touche un bord de l'écran
     * Implémentation de l'interface InvaderBoundaryListener
     *
     * @param invader L'invader qui a touché le bord
     * @param direction La direction du bord touché (GAUCHE ou DROITE)
     */
    @Override
    public void onBoundaryCollision(Invader invader, Direction direction) {
        double rebond = (direction == Direction.GAUCHE) ? 5.0 : -5.0;
        Random random = new Random();

        // Calculer le centre de la vague pour le dispersement
        double centreX = 0, centreY = 0;
        for (Invader inv : invaders) {
            centreX += inv.getLeft();
            centreY += inv.getTop();
        }
        centreX /= invaders.size();
        centreY /= invaders.size();

        for (int i = 0; i < invaders.size(); i++) {
            Invader inv = invaders.get(i);

            // Calculer un vecteur de dispersion depuis le centre
            double dx = inv.getLeft() - centreX;
            double dy = inv.getTop() - centreY;

            // Normaliser le vecteur et appliquer une force de dispersion
            double length = Math.sqrt(dx * dx + dy * dy);
            if (length > 0) {
                dx = dx / length * random.nextDouble() * 2.0;
                dy = dy / length * random.nextDouble() * 2.0;
            }

            // Descendre, rebondir horizontalement et disperser légèrement
            inv.bouger(rebond + dx, 15 + dy);

            // On augmente légèrement la vitesse à chaque rebond pour accroître la difficulté
            double vitesseActuelle = Invader.getVitesseInvaders();
            Invader.setVitesseInvaders(vitesseActuelle * 1.05);
        }
    }
}
