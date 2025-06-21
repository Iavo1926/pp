package main;

import gui.PanneauEchiquier;
import model.ChessModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class JeuEchec {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            ChessModel model = new ChessModel();
            JFrame frame = new JFrame("ECHEC DE LA MORT");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            PanneauEchiquier panneauEchiquier = new PanneauEchiquier(model.getEchiquier());
            frame.add(panneauEchiquier, BorderLayout.CENTER);

            JPanel panneauControle = new JPanel();
            panneauControle.setLayout(new GridLayout(4, 1));

            
            JTextField champX = new JTextField(2);
            JTextField champY = new JTextField(2);
            JTextField champFinX = new JTextField(2);
            JTextField champFinY = new JTextField(2);
            String[] pieceSelectionnee = {"Pion"}; 
            String[] couleurSelectionnee = {"Blanc"};
            boolean[] modePlacement = {true};

       
            JPanel panneauPiece = new JPanel();
            String[] pieces = {"Pion", "Tour", "Cavalier", "Fou", "Reine", "Roi"};
            JComboBox<String> selecteurPiece = new JComboBox<>(pieces);
            selecteurPiece.addActionListener(e -> pieceSelectionnee[0] = (String) selecteurPiece.getSelectedItem());

            JRadioButton boutonBlanc = new JRadioButton("Blanc", true);
            JRadioButton boutonNoir = new JRadioButton("Noir");
            ButtonGroup groupeCouleur = new ButtonGroup();
            groupeCouleur.add(boutonBlanc);
            groupeCouleur.add(boutonNoir);

            ActionListener ecouteurCouleur = e -> {
                couleurSelectionnee[0] = boutonBlanc.isSelected() ? "Blanc" : "Noir";
            };
            boutonBlanc.addActionListener(ecouteurCouleur);
            boutonNoir.addActionListener(ecouteurCouleur);

            panneauPiece.add(new JLabel("Piece: "));
            panneauPiece.add(selecteurPiece);
            panneauPiece.add(boutonBlanc);
            panneauPiece.add(boutonNoir);

            
            JPanel panneauCoordonnees = new JPanel();
            panneauCoordonnees.add(new JLabel("X:"));
            panneauCoordonnees.add(champX);
            panneauCoordonnees.add(new JLabel("Y:"));
            panneauCoordonnees.add(champY);
            panneauCoordonnees.add(new JLabel("Vers X:"));
            panneauCoordonnees.add(champFinX);
            panneauCoordonnees.add(new JLabel("Vers Y:"));
            panneauCoordonnees.add(champFinY);

            
            JPanel panneauBoutons = new JPanel();
            JButton boutonAction = new JButton("Placer");
            JButton boutonMode = new JButton("Passer au mode deplacement");

            boutonAction.addActionListener(e -> {
                try {
                    int x = Integer.parseInt(champX.getText());
                    int y = Integer.parseInt(champY.getText());
                    
                    if (modePlacement[0]) {
                        if (!model.placerPiece(x, y, pieceSelectionnee[0], couleurSelectionnee[0])) {
                          
                            System.out.println("Case deja occupeeX3");
                        }
                    } else {
                        int finX = Integer.parseInt(champFinX.getText());
                        int finY = Integer.parseInt(champFinY.getText());
                        if (!model.deplacerPiece(x, y, finX, finY, couleurSelectionnee[0])) {
                       
                                System.out.println(" \"Aucune piece de cette couleur a cet emplacement!\" : \r\n" + //
                                                                        "                                \"Position invalide (0-7)!\");");
                        }
                    }

                    
                    panneauEchiquier.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer des coordonnes valides (0-7)");
                }
            });


            boutonMode.addActionListener(e -> {
                modePlacement[0] = !modePlacement[0];
                boutonAction.setText(modePlacement[0] ? "Placer" : "Deplacer");
                boutonMode.setText(modePlacement[0] ? "Passer au mode deplacement" : "Passer au mode placement");
                champFinX.setEnabled(!modePlacement[0]);
                champFinY.setEnabled(!modePlacement[0]);
            });

            panneauBoutons.add(boutonAction);
            panneauBoutons.add(boutonMode);

            
            JPanel panneauSauvegarde = new JPanel();
            JButton boutonSauvegarder = new JButton("Sauvegarder");
            JButton boutonCharger = new JButton("Charger");

            boutonSauvegarder.addActionListener(e -> {
                try (PrintWriter writer = new PrintWriter("sauvegarde_echecs.txt")) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            writer.print(model.getEchiquier()[i][j]);
                            if (j < 7) writer.print(",");
                        }
                        writer.println();
                    }
                   
                     System.out.println("Partie sauvegarde");
                } catch (IOException ex) {
             
                  System.out.println("Erreur lors de la sauvegarde");
                }
            });

            boutonCharger.addActionListener(e -> {
                try (BufferedReader lecteur = new BufferedReader(new FileReader("sauvegarde_echecs.txt"))) {
                    String ligne;
                    int rangee = 0;
                    while ((ligne = lecteur.readLine()) != null && rangee < 8) {
                        String[] piecesArray = ligne.split(",");
                        for (int colonne = 0; colonne < 8 && colonne < piecesArray.length; colonne++) {
                            model.getEchiquier()[rangee][colonne] = piecesArray[colonne];
                        }
                        rangee++;
                    }
                    panneauEchiquier.repaint();
               
                     System.out.println("Partie sauvegarde");
                } catch (IOException ex) {
                   
                       System.out.println("Erreur chargement");
                }
            });

            panneauSauvegarde.add(boutonSauvegarder);
            panneauSauvegarde.add(boutonCharger);

          
            panneauControle.add(panneauPiece);
            panneauControle.add(panneauCoordonnees);
            panneauControle.add(panneauBoutons);
            panneauControle.add(panneauSauvegarde);
            frame.add(panneauControle, BorderLayout.NORTH);

           
            champFinX.setEnabled(false);
            champFinY.setEnabled(false);

        
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}