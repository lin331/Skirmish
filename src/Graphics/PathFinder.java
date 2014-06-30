package Graphics;

import Map.Path;
import Map.Tile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class PathFinder implements MouseListener {

    private ArrayList<Tile> path;
    
    public PathFinder() {
        this.path = new ArrayList<Tile>();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        TileButton b = (TileButton)e.getSource();
        path.add(b.getTile());
        
        ImageIcon unitIcon = new ImageIcon("res/activeUnitTile.png");
        ImageIcon icon = new ImageIcon("res/pathTile.png");
        if (b.getTile().isEmpty()) {
            b.setIcon(icon);        
        }
        else {
            b.setIcon(unitIcon);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        TileButton b = (TileButton)e.getSource();
        ImageIcon unitIcon = new ImageIcon("res/activeUnitTile.png");
        ImageIcon icon = new ImageIcon("res/pathTile.png");
        if (b.getTile().isEmpty()) {
            b.setIcon(icon);        
        }
        else {
            b.setIcon(unitIcon);
        }
        
        printPath();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            TileButton b = (TileButton)e.getSource();
            path.add(b.getTile());
            
            ImageIcon unitIcon = new ImageIcon("res/passedUnitTile.png");
            ImageIcon icon = new ImageIcon("res/pathTile.png");
            if (b.getTile().isEmpty()) {
                b.setIcon(icon);        
            }
            else {
                b.setIcon(unitIcon);
            }
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            TileButton b = (TileButton)e.getSource();
            
            ImageIcon activeUnitIcon = new ImageIcon("res/activeUnitTile.png");
            ImageIcon passedUnitIcon = new ImageIcon("res/passedUnitTile.png");
            ImageIcon icon = new ImageIcon("res/pathTile.png");
            if (b.getTile().isEmpty()) {
                b.setIcon(icon);        
            }
            else if (path.size() == 1) {
                b.setIcon(activeUnitIcon);
            }
            else {
                b.setIcon(passedUnitIcon);
            }
        }           
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
    
    public void printPath() {
        for (Tile t : path) {
            System.out.println(t);
        }
    }
}