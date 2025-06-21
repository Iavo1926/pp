package Tsimbazaza;

import Secteur.Secteur;
import Date.Date;
import Point.Point;
import Vivant.Animaux;
import Vivant.Herbivore;
import Vivant.Carnivore;

public class Tsimbazaza {
    private Secteur[] secteur;

    public Tsimbazaza() {
        secteur = new Secteur[2];
        secteur[0] = new Secteur(50, 50, 200, 200);
        secteur[1] = new Secteur(300, 50, 200, 200);

       
        Point point1 = new Point(60, 60);
        Animaux herbivore = new Herbivore("Herbivore1", 'M', new Date(1, 1, 2020), 50.0, 70.0, point1);
        secteur[0].ajouterAnimal(herbivore);

        
        Point point2 = new Point(310, 60);
        Animaux carnivore = new Carnivore("Carnivore1", 'F', new Date(1, 1, 2020), 70.0, 90.0, point2, 50.0);
        secteur[1].ajouterAnimal(carnivore);
    }

    public Secteur[] getSecteur() {
        return secteur;
    }
}