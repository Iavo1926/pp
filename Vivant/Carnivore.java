package Vivant;

import Date.Date;
import Point.Point;

public class Carnivore extends Animaux {
    private double distanceFatale;
    private double poidsMax;

    public Carnivore(String nom, char sexe, Date date, double poids, double poidsMax, Point deplacementAnimaux, double distanceFatale) {
        super(nom, sexe, date, poids, poidsMax, deplacementAnimaux);
        this.distanceFatale = distanceFatale;
        this.poidsMax = poidsMax;
    }

    public double calculerDistance(Point positionProie) {
        double dx = this.getDeplacementAnimaux().getx() - positionProie.getx();
        double dy = this.getDeplacementAnimaux().gety() - positionProie.gety();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double manger(Animaux[] proies) {
        for (Animaux proie : proies) {
            if (proie != null && proie != this) {
                double distance = calculerDistance(proie.getDeplacementAnimaux());
                if (distance <= distanceFatale) {
                    double poidsGagne = proie.getPoids();
                    setPoids(getPoids() + poidsGagne);
                    Animaux.decreaseCount();
                    proie.setPoids(0);
                    return getPoids();
                }
            }
        }
        return getPoids();
    }

    public double getDistanceFatale() {
        return distanceFatale;
    }

    @Override
    public double getPoidsMax() {
        return poidsMax;
    }
}