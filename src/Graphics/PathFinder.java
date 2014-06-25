package Graphics;

import Player.Path;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PathFinder implements MouseListener {

    private Path path;
    
    @Override
    public void mousePressed(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        ImageIcon activeIcon = new ImageIcon("res/activeTile.png");
        b.setIcon(activeIcon);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
}