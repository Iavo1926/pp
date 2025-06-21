import javax.swing.*;
import java.awt.event.*;

public class Ecoute implements MouseListener {
    public Ecoute() {
        // Constructor can be used to set up listeners if needed
    }

 
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click events
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
    }

 
    public void mousePressed(MouseEvent e) {}

   
    public void mouseReleased(MouseEvent e) {}

   
    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}
