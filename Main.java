// model/ChessModel.java
package model;

public class ChessModel {
    String[][] echiquier;
    String BLANC = "Blanc";
    String NOIR = "Noir";

    public ChessModel() {
        echiquier = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                echiquier[i][j] = "Vide";
            }
        }
    }

    public boolean placerPiece(int x, int y, String typePiece, String couleur) {
        if (!positionValide(x, y)) {
            return false; 
        }
        
        if (!echiquier[y][x].equals("Vide")) {
            return false; 
        }
        echiquier[y][x] = typePiece + "-" + couleur;
        return true;
    }

    //  public boolean deplacerPiece(int debutX, int debutY, int finX, int finY, String couleur) {
    //      if (!positionValide(debutX, debutY) || !positionValide(finX, finY)) {
    //          return false; 
    //      }
        
    //      String piece = echiquier[debutY][debutX];
    //      if (piece.equals("Vide") || !piece.contains(couleur)) {
    //          return false; 
    //      }

        
    //      if (!echiquier[finY][finX].equals("Vide")) {
    //          return false; 
    //      }

    //      echiquier[finY][finX] = piece;
    //     echiquier[debutY][debutX] = "Vide";
    //      return true;
    //  }
    public boolean deplacerPiece(int debutX, int debutY, int finX, int finY, String couleur) {
        if (!positionValide(debutX, debutY) || !positionValide(finX, finY)) {
            return false; 
        }
    
        String piece = echiquier[debutY][debutX];
        if (piece.equals("Vide") || !piece.contains(couleur)) {
            return false; 
        }
    
        
        String destination = echiquier[finY][finX];
        if (!destination.equals("Vide") && destination.contains(couleur)) {
            return false; 
        }
    
       
        String typePiece = piece.split("-")[0];
    
 
        if (!estMouvementValide(typePiece, debutX, debutY, finX, finY)) {
            return false; // Mouvement non autorisé
        }
    
        // Effectuer le déplacement ou la capture
        echiquier[finY][finX] = piece;
        echiquier[debutY][debutX] = "Vide";
        return true;
    }
    
    public boolean estMouvementValide(String typePiece, int debutX, int debutY, int finX, int finY) {
        int dx = Math.abs(finX - debutX);
        int dy = Math.abs(finY - debutY);
    
        switch (typePiece) {
            case "Pion":
                // Mouvement du pion (simplifié pour les Blancs)
                if (echiquier[debutY][debutX].contains("Blanc")) {
                    // Avance d'une case
                    if (debutX == finX && finY == debutY + 1 && echiquier[finY][finX].equals("Vide")) {
                        return true;
                    }
                    // Avance de deux cases depuis la position initiale
                    if (debutY == 1 && debutX == finX && finY == debutY + 2 && echiquier[finY][finX].equals("Vide")
                            && echiquier[debutY + 1][debutX].equals("Vide")) {
                        return true;
                    }
                    // Capture en diagonale
                    if (dy == 1 && dx == 1 && !echiquier[finY][finX].equals("Vide")) {
                        return true;
                    }
                }
            
                return false;
    
            case "Cavalier":
                return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    
            case "Fou":
                if (dx == dy) {
                    return cheminLibreDiagonale(debutX, debutY, finX, finY);
                }
                return false;
    
            case "Tour":
                if (dx == 0 || dy == 0) {
                    return cheminLibreLigne(debutX, debutY, finX, finY);
                }
                return false;
    
            case "Dame":
                if (dx == dy || dx == 0 || dy == 0) {
                    return cheminLibreLigne(debutX, debutY, finX, finY) || cheminLibreDiagonale(debutX, debutY, finX, finY);
                }
                return false;
    
            case "Roi":
                return dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0);
                
    
            default:
                return false;
        }
    }
    
    public boolean cheminLibreLigne(int debutX, int debutY, int finX, int finY) {
        int stepX = Integer.compare(finX, debutX);
        int stepY = Integer.compare(finY, debutY);
        int x = debutX + stepX;
        int y = debutY + stepY;
        while (x != finX || y != finY) {
            if (!echiquier[y][x].equals("Vide")) {
                return false;
            }
            x += stepX;
            y += stepY;
        }
        return true;
    }
    
    public boolean cheminLibreDiagonale(int debutX, int debutY, int finX, int finY) {
        int stepX = Integer.compare(finX, debutX);
        int stepY = Integer.compare(finY, debutY);
        int x = debutX + stepX;
        int y = debutY + stepY;
        while (x != finX && y != finY) {
            if (!echiquier[y][x].equals("Vide")) {
                return false;
            }
            x += stepX;
            y += stepY;
        }
        return true;
    }

    public boolean positionValide(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public String[][] getEchiquier() {
        return echiquier;
    }
}