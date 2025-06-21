package Vivant;

import Date.Date;
import Point.Point;
import Secteur.Secteur;

public abstract class Animaux {
    private String nom;
    private char sexe;
    private Date dateNaissance;
    private double poids;
    private double poidsMax;
    private Point deplacementAnimaux;
    private static int count = 0;

    public Animaux(String nom, char sexe, Date date, double poids, double poidsMax, Point deplacementAnimaux) {
        this.nom = nom;
        this.sexe = sexe;
        this.dateNaissance = date;
        this.poids = poids;
        this.poidsMax = poidsMax;
        this.deplacementAnimaux = deplacementAnimaux;
        count++;
    }

    public String getNom() {
        return nom;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public Point getDeplacementAnimaux() {
        return deplacementAnimaux;
    }

    public static void decreaseCount() {
        count--;
    }

    public static int getCount() {
        return count;
    }

    // Ajouter une méthode abstraite pour obtenir le poids maximum
    public abstract double getPoidsMax();

    public void deplacer(int secteurX, int secteurY, int secteurWidth, int secteurHeight) {
        int deltaX = (int) (Math.random() * 11) - 5;
        int deltaY = (int) (Math.random() * 11) - 5;

        int newX = deplacementAnimaux.getx() + deltaX;
        int newY = deplacementAnimaux.gety() + deltaY;

        if (newX < secteurX) newX = secteurX;
        if (newX > secteurX + secteurWidth - 10) newX = secteurX + secteurWidth - 10;
        if (newY < secteurY) newY = secteurY;
        if (newY > secteurY + secteurHeight - 10) newY = secteurY + secteurHeight - 10;

        deplacementAnimaux.setx(newX);
        deplacementAnimaux.sety(newY);
    }

    public void deplacer(int newX, int newY, Secteur secteur) {
        int secteurX = secteur.x;
        int secteurY = secteur.y;
        int secteurWidth = secteur.width;
        int secteurHeight = secteur.height;

        if (newX < secteurX) newX = secteurX;
        if (newX > secteurX + secteurWidth - 10) newX = secteurX + secteurWidth - 10;
        if (newY < secteurY) newY = secteurY;
        if (newY > secteurY + secteurHeight - 10) newY = secteurY + secteurHeight - 10;

        deplacementAnimaux.setx(newX);
        deplacementAnimaux.sety(newY);
    }

    @Override
    public String toString() {
        return nom + " (" + sexe + "), né le " + dateNaissance + ", poids: " + poids + " kg";
    }
}