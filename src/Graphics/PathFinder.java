package Graphics;

import Map.Path;
import Map.Position;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class PathFinder implements MouseListener {

    private ArrayList<Position> path;
    
    public PathFinder() {
        this.path = new ArrayList<Position>();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        TileButton b = (TileButton)e.getSource();
        ImageIcon activeIcon = new ImageIcon("res/activeTile.png");
        b.setIcon(activeIcon);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        TileButton b = (TileButton)e.getSource();
        ImageIcon activeIcon = new ImageIcon("res/pathTile.png");
        b.setIcon(activeIcon);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            TileButton b = (TileButton)e.getSource();
            ImageIcon activeIcon = new ImageIcon("res/activeTile.png");
            b.setIcon(activeIcon); 
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            TileButton b = (TileButton)e.getSource();
            ImageIcon activeIcon = new ImageIcon("res/pathTile.png");
            b.setIcon(activeIcon);  
        }           
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
    
    public ImageIcon nextIcon(TileButton b) {
        return null;
    }
}