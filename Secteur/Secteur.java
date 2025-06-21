package Secteur;

import Vivant.Animaux;
import java.util.ArrayList;

public class Secteur {
    public int x, y, width, height;
    private double quantiteHerbe;
    private double quantiteHerbeDisponible;
    private ArrayList<Animaux> animaux;

    public Secteur(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.quantiteHerbe = 100.0;
        this.quantiteHerbeDisponible = quantiteHerbe;
        this.animaux = new ArrayList<>();
    }

    public void ajouterAnimal(Animaux animal) {
        animaux.add(animal);
    }

    public ArrayList<Animaux> getAnimaux() {
        return animaux;
    }

    public double getQuantiteHerbe() {
        return quantiteHerbe;
    }

    public double getQuantiteHerbeDisponible() {
        return quantiteHerbeDisponible;
    }

    public void reduireHerbe(double quantite) {
        quantiteHerbeDisponible -= quantite;
        if (quantiteHerbeDisponible < 0) quantiteHerbeDisponible = 0;
    }
}