import Tsimbazaza.Tsimbazaza;
import Secteur.Secteur;
import Vivant.Animaux;
import Vivant.Herbivore;
import Vivant.Carnivore;
import Point.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dessin extends JPanel {
    private Tsimbazaza tsimbazaza;
    private Timer timer;
    private int totalAnimaux; // Compteur du nombre total d'animaux
    private Map<Animaux, Integer> carnivoreEatFrames; // Pour l'animation des carnivores qui mangent
    private static Map<Animaux, Integer> staticCarnivoreEatFrames; // Pour Main

    public Dessin(Tsimbazaza tsimbazaza) {
        this.tsimbazaza = tsimbazaza;
        this.totalAnimaux = Animaux.getCount();
        this.carnivoreEatFrames = new HashMap<>();
        staticCarnivoreEatFrames = carnivoreEatFrames;

        // Initialiser le Timer pour animer le déplacement et les interactions
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mettre à jour les positions des animaux
                for (Secteur secteur : tsimbazaza.getSecteur()) {
                    for (Animaux animal : secteur.getAnimaux()) {
                        if (animal != null && animal.getPoids() > 0) {
                            animal.deplacer(secteur.x, secteur.y, secteur.width, secteur.height);
                        }
                    }
                }

                // Actions des carnivores : manger des proies dans le même secteur
                for (Secteur secteur : tsimbazaza.getSecteur()) {
                    ArrayList<Animaux> animaux = secteur.getAnimaux();
                    for (Animaux animal : animaux) {
                        if (animal != null && animal.getPoids() > 0 && animal instanceof Carnivore) {
                            Carnivore carnivore = (Carnivore) animal;
                            double poidsAvant = carnivore.getPoids();
                            double poidsApres = carnivore.manger(animaux.toArray(new Animaux[0]));
                            if (poidsApres > poidsAvant) {
                                carnivoreEatFrames.put(carnivore, 10); // 10 frames d'animation
                                totalAnimaux = Animaux.getCount();
                            }
                        }
                    }
                    animaux.removeIf(animal -> animal == null || animal.getPoids() <= 0);
                }

                // Actions des herbivores : manger de l'herbe
                for (Secteur secteur : tsimbazaza.getSecteur()) {
                    for (Animaux animal : secteur.getAnimaux()) {
                        if (animal != null && animal.getPoids() > 0 && animal instanceof Herbivore) {
                            Herbivore herbivore = (Herbivore) animal;
                            herbivore.manger(secteur);
                        }
                    }
                }

                // Mettre à jour les animations des carnivores
                carnivoreEatFrames.entrySet().removeIf(entry -> entry.getValue() <= 0);
                carnivoreEatFrames.replaceAll((animal, frames) -> frames - 1);

                // Redessiner le composant
                repaint();
            }
        });
        timer.start();
    }

    // Méthode statique pour permettre à Main de déclencher l'animation des carnivores
    public static void triggerCarnivoreEat(Animaux carnivore) {
        if (staticCarnivoreEatFrames != null) {
            staticCarnivoreEatFrames.put(carnivore, 10); // 10 frames d'animation
        }
    }

    // Méthode statique pour mettre à jour le nombre total d'animaux
    public static void updateTotalAnimaux(int newTotal) {
        // Cette méthode sera appelée par Main pour mettre à jour le compteur
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(200, 220, 200));

        // Dessiner le compteur du nombre total d'animaux (en haut à gauche)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Animaux: " + totalAnimaux, 10, 20);
        g.setColor(Color.BLUE);
        g.fillOval(80, 10, 10, 10); // Icône d'animal

        // Dessiner les secteurs
        for (Secteur secteur : tsimbazaza.getSecteur()) {
            int greenValue = (int) (255 * (secteur.getQuantiteHerbeDisponible() / secteur.getQuantiteHerbe()));
            g.setColor(new Color(0, greenValue, 0));
            g.fillRect(secteur.x, secteur.y, secteur.width, secteur.height);
            g.setColor(Color.BLACK);
            g.drawRect(secteur.x, secteur.y, secteur.width, secteur.height);

            // Dessiner une jauge d'herbe au-dessus du secteur
            int herbeWidth = (int) (100 * (secteur.getQuantiteHerbeDisponible() / secteur.getQuantiteHerbe()));
            g.setColor(Color.GREEN);
            g.fillRect(secteur.x, secteur.y - 10, herbeWidth, 5);
            g.setColor(Color.BLACK);
            g.drawRect(secteur.x, secteur.y - 10, 100, 5);
        }

        // Dessiner les animaux
        for (Secteur secteur : tsimbazaza.getSecteur()) {
            for (Animaux animal : secteur.getAnimaux()) {
                if (animal != null && animal.getPoids() > 0) {
                    Point position = animal.getDeplacementAnimaux();
                    int x = position.getx();
                    int y = position.gety();

                    // Dessiner l'animal
                    if (animal instanceof Carnivore) {
                        g.setColor(Color.RED);
                        g.fillOval(x - 5, y - 5, 10, 10);

                        // Animation si le carnivore a mangé
                        if (carnivoreEatFrames.containsKey(animal) && carnivoreEatFrames.get(animal) > 0) {
                            g.setColor(new Color(255, 0, 0, 100)); // Rouge semi-transparent
                            int radius = 15 + (10 - carnivoreEatFrames.get(animal)) * 2;
                            g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                        }
                    } else if (animal instanceof Herbivore) {
                        g.setColor(Color.GREEN);
                        g.fillOval(x - 5, y - 5, 10, 10);
                    }

                    // Dessiner le nom de l'animal
                    g.setColor(Color.BLACK);
                    FontMetrics fm = g.getFontMetrics();
                    int textWidth = fm.stringWidth(animal.getNom());
                    g.drawString(animal.getNom(), x - textWidth / 2, y - 15);

                    // Dessiner une barre de poids à côté de l'animal
                    double poidsMax = (animal instanceof Herbivore) ? ((Herbivore) animal).getPoidsMax() : ((Carnivore) animal).getPoidsMax();
                    int poidsWidth = (int) (30 * (animal.getPoids() / poidsMax));
                    g.setColor(Color.YELLOW);
                    g.fillRect(x + 15, y - 5, poidsWidth, 5);
                    g.setColor(Color.YELLOW);
                    g.drawRect(x + 15, y - 5, 30, 5);
                }
            }
        }
    }

    // Ajouter une méthode pour obtenir le poids maximum (nécessaire pour Herbivore et Carnivore)
    private double getPoidsMax(Animaux animal) {
        if (animal instanceof Herbivore) {
            return ((Herbivore) animal).getPoidsMax();
        } else if (animal instanceof Carnivore) {
            return ((Carnivore) animal).getPoidsMax();
        }
        return 1.0; // Valeur par défaut pour éviter une division par zéro
    }
}