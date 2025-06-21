package Vivant;

import Date.Date;
import Point.Point;
import Secteur.Secteur;

public class Herbivore extends Animaux {
 double poidsMax;

    public Herbivore(String nom, char sexe, Date date, double poids, double poidsMax, Point deplacementAnimaux) {
        super(nom, sexe, date, poids, poidsMax, deplacementAnimaux);
        this.poidsMax = poidsMax;
    }

    public boolean manger(Secteur secteur) {
        if (secteur.getQuantiteHerbeDisponible() >= 10.0) {
            secteur.reduireHerbe(10.0);
            setPoids(getPoids() + 10.0);
            if (getPoids() > poidsMax) setPoids(poidsMax);
            return true;
        }
        return false;
    }

    
    public double getPoidsMax() {
        return poidsMax;
    }
}